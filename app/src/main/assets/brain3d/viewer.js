import * as THREE from 'three';
import { OrbitControls } from 'three/addons/controls/OrbitControls.js';
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js';
import { DRACOLoader } from 'three/addons/loaders/DRACOLoader.js';

// Fully offline viewer: real sourced anatomy (Brain Project, CC BY-SA 4.0).
// Runs from Android assets via file:// URLs — no network needed.
const MODEL_URL = 'models/brain.glb';
const MANIFEST_URL = 'models/manifest.json';
const FUNCTIONS_URL = 'functions.json';
const DRACO_PATH = 'vendor/draco/';
const IS_ANDROID = typeof window.Android !== 'undefined';

const TA2_JUNK = new Set([
  'Bonus collection', 'Scene Collection', 'Head',
  'Regions of human body', 'Main divisions',
]);

const CATEGORY_STYLE = {
  cortex: { color: 0xc79a90, label: 'Cortex' },
  cerebellum: { color: 0xbd9489, label: 'Cerebellum' },
  brainstem: { color: 0xb3937a, label: 'Brainstem' },
  deep_grey: { color: 0x9c6a5c, label: 'Deep grey' },
  diencephalon: { color: 0xa67e6b, label: 'Diencephalon' },
  white_matter: { color: 0xe2d6c2, label: 'White matter' },
  tracts: { color: 0xd6c5a2, label: 'Tracts' },
  ventricles: { color: 0xafcadd, opacity: 0.55, label: 'Ventricles' },
  arteries: { color: 0xa83a32, label: 'Arteries' },
  veins_sinuses: { color: 0x416b99, label: 'Veins & sinuses' },
  cranial_nerves: { color: 0xd3b268, label: 'Cranial nerves' },
  meninges_dura: { color: 0xc9ced1, opacity: 0.35, label: 'Meninges' },
};

const $ = (id) => document.getElementById(id);

// Brainstem floor: the cord-bound tails below are truncated at the
// inferior tip of the medulla (computed at runtime from TRIM_REF_IDS),
// so the model ends at the brainstem instead of trailing into the neck.
// IDs are Brain-Project manifest ids (verified against manifest.json).
const TRIM_IDS = [30, 31, 85, 86, 294, 295]; // ant. spinal aa., corticospinal + reticulospinal tracts
const TRIM_REF_IDS = [196, 327, 328, 235, 236]; // medulla, pyramids, olives (left+right)

function fail(step, msg) {
  $('loadmsg').textContent = `Stuck at "${step}": ${msg}`;
  $('loadmsg').style.color = '#f87171';
  $('retryBtn').hidden = false;
  try {
    if (window.Android && typeof window.Android.onLoadFailed === 'function') {
      window.Android.onLoadFailed(step, msg);
    }
  } catch (ignored) {}
}
$('retryBtn').addEventListener('click', () => window.location.reload());
window.addEventListener('error', (e) => {
  console.error(e);
  if (!$('loader').classList.contains('done')) fail('Error: ' + (e.message || 'failed to start'));
});
window.addEventListener('unhandledrejection', (e) => {
  console.error('Unhandled rejection:', e.reason);
  if (!$('loader').classList.contains('done')) {
    fail('Async load', e.reason && e.reason.message ? e.reason.message : String(e.reason));
  }
});

const container = $('scene');
let renderer = null;
try {
  renderer = new THREE.WebGLRenderer({
    antialias: false,
    alpha: true,
    powerPreference: 'default',
    precision: 'mediump',
    depth: true,
    stencil: false,
    failIfMajorPerformanceCaveat: false
  });
} catch (e1) {
  try {
    renderer = new THREE.WebGLRenderer({ alpha: true, precision: 'mediump' });
  } catch (e2) {
    fail('WebGL', 'WebGL not supported: ' + (e2.message || e1.message));
  }
}

if (renderer) {
  renderer.setPixelRatio(1.0);
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.localClippingEnabled = true;
  if (renderer.domElement) {
    renderer.domElement.addEventListener('webglcontextlost', (e) => {
      e.preventDefault();
      console.warn('WebGL context lost, pausing animation loop');
      renderer.setAnimationLoop(null);
    }, false);
    renderer.domElement.addEventListener('webglcontextrestored', () => {
      console.info('WebGL context restored, resuming animation loop');
      renderer.setAnimationLoop(() => {
        if (controls) controls.update();
        renderer.render(scene, camera);
      });
    }, false);
  }
  container.appendChild(renderer.domElement);
}

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(40, window.innerWidth / window.innerHeight, 0.01, 100);
camera.position.set(0.35, 0.22, 0.62);

scene.add(new THREE.HemisphereLight(0xfff1e6, 0x232b34, 0.8));
const key = new THREE.DirectionalLight(0xfff4ea, 1.4);
key.position.set(0.6, 0.9, 0.7);
scene.add(key);
const fill = new THREE.DirectionalLight(0xdfe8ff, 0.6);
fill.position.set(-0.7, 0.1, 0.5);
scene.add(fill);
const rim = new THREE.DirectionalLight(0xffe2d2, 0.7);
rim.position.set(-0.2, 0.4, -0.8);
scene.add(rim);

let idleTimer = null;
let spinWanted = true;

const controls = (renderer && renderer.domElement) ? new OrbitControls(camera, renderer.domElement) : null;
if (controls) {
  controls.enableDamping = true;
  controls.dampingFactor = 0.08;
  controls.autoRotate = true;
  controls.autoRotateSpeed = 0.7;
  controls.minDistance = 0.05;
  controls.maxDistance = 3;

  controls.addEventListener('start', () => {
    controls.autoRotate = false;
    if (idleTimer) clearTimeout(idleTimer);
  });
  controls.addEventListener('end', () => {
    if (idleTimer) clearTimeout(idleTimer);
    idleTimer = setTimeout(() => { if (spinWanted) controls.autoRotate = true; }, 5000);
  });
}

function tissueMaterial(cat) {
  const style = CATEGORY_STYLE[cat] || { color: 0xb9a89c };
  const color = new THREE.Color(style.color);
  const mat = new THREE.MeshLambertMaterial({ color });
  if (style.opacity !== undefined && style.opacity < 1) {
    mat.transparent = true;
    mat.opacity = style.opacity;
    mat.depthWrite = false;
  }
  return mat;
}

const baseMats = {};
const hoverMats = {};
const selectMats = {};

function ensureVariants(cat) {
  if (!baseMats[cat]) return;
  if (!hoverMats[cat]) {
    const m = baseMats[cat].clone();
    m.emissive = new THREE.Color(baseMats[cat].color).multiplyScalar(0.28);
    hoverMats[cat] = m;
  }
  if (!selectMats[cat]) {
    const m = baseMats[cat].clone();
    m.emissive = new THREE.Color(baseMats[cat].color).multiplyScalar(0.5);
    selectMats[cat] = m;
  }
}

const manifestById = new Map();
let functions = {};
const anatomyMeshes = [];
const meshesByCat = new Map();
const searchIndex = [];
const catState = {};
const labelMats = new Set();
let matchSet = null;
let hemi = 'both'; // 'both' | 'left' | 'right'; median structures always stay visible
let labelsOn = false;
let labelWorld = 0.01;
let selected = null;
let hovered = null;
let homePos = camera.position.clone();
let homeTarget = new THREE.Vector3(0, 0, 0);
const slicePlane = new THREE.Plane(new THREE.Vector3(1, 0, 0), 0);
const trimPlane = new THREE.Plane(new THREE.Vector3(0, -1, 0), -Infinity); // keeps y >= floor once set
const noPlanes = [];
const onePlane = [slicePlane];
let sliceMode = 'off';
let sliceT = 0.5;
let sliceBounds = null;
let clipActive = false;

function cleanTa2(ta2, region, parent, label) {
  const parts = (Array.isArray(ta2) ? ta2 : [])
    .filter((p) => p && !TA2_JUNK.has(p))
    .reverse();
  for (const extra of [region, parent]) {
    if (extra && !parts.includes(extra)) parts.push(extra);
  }
  if (label && !parts.includes(label)) parts.push(label);
  return parts;
}

function showCard(anat) {
  $('cardSide').textContent = anat.side || '—';
  $('cardName').textContent =
    anat.label + (anat.side === 'left' || anat.side === 'right' ? ` (${anat.side})` : '');
  const catLabel = (CATEGORY_STYLE[anat.cat] || {}).label || anat.cat;
  $('cardMeta').textContent = `${catLabel}${anat.region ? ` · ${anat.region}` : ''}`;
  $('cardTa2').textContent = cleanTa2(anat.ta2, anat.region, anat.parent, anat.label).join(' › ') || '—';
  const func = functions[String(anat.id)];
  $('cardFunc').textContent = func || '';
  $('cardFunc').hidden = !func;
  $('funcLabel').hidden = !func;
  $('cardDec').textContent = anat.decussation || '';
  $('cardDec').hidden = !anat.decussation;
  $('decLabel').hidden = !anat.decussation;
  $('cardSrc').textContent = `Source: ${anat.source || 'Z-Anatomy / BodyParts3D'}`;
  $('card').hidden = false;
}

function hideCard() {
  if (selected) {
    selected.material = variantFor(selected, 'base');
    selected = null;
  }
  $('card').hidden = true;
}

function bridgeTap(anat) {
  if (!IS_ANDROID) return;
  try {
    window.Android.onStructureTap(
      anat.id, anat.label, anat.region || '', anat.source || '', anat.cat || '');
  } catch (err) { /* host handles display; viewer card is the fallback */ }
}

function select(mesh) {
  hideCard();
  if (!mesh) return;
  selected = mesh;
  ensureVariants(mesh.userData.anat.cat);
  mesh.material = variantFor(mesh, 'select');
  showCard(mesh.userData.anat);
  bridgeTap(mesh.userData.anat);
}

function setHovered(mesh) {
  if (hovered === mesh) return;
  if (hovered && hovered !== selected) hovered.material = variantFor(hovered, 'base');
  hovered = mesh;
  if (hovered && hovered !== selected) {
    ensureVariants(hovered.userData.anat.cat);
    hovered.material = variantFor(hovered, 'hover');
  }
  renderer.domElement.style.cursor = hovered ? 'pointer' : '';
}

const raycaster = new THREE.Raycaster();
const pointer = new THREE.Vector2();
let downX = 0;
let downY = 0;
function pickAt(cx, cy) {
  pointer.x = (cx / window.innerWidth) * 2 - 1;
  pointer.y = -(cy / window.innerHeight) * 2 + 1;
  raycaster.setFromCamera(pointer, camera);
  // NB: Raycaster ignores Object3D.visible, so filter hidden meshes out:
  // otherwise taps land on invisible (filtered/deselected) structures.
  const hits = raycaster.intersectObjects(anatomyMeshes.filter((m) => m.visible), false);
  return hits.length ? hits[0].object : null;
}
if (renderer && renderer.domElement) {
  renderer.domElement.addEventListener('pointerdown', (e) => { downX = e.clientX; downY = e.clientY; });
  renderer.domElement.addEventListener('pointerup', (e) => {
    if (Math.hypot(e.clientX - downX, e.clientY - downY) > 8) return;
    select(pickAt(e.clientX, e.clientY));
  });
  renderer.domElement.addEventListener('pointermove', (e) => {
    if (e.pointerType === 'touch' || e.buttons !== 0) return;
    setHovered(pickAt(e.clientX, e.clientY));
  });
}

function focusOn(mesh) {
  if (!controls) return;
  controls.target.copy(new THREE.Box3().setFromObject(mesh).getCenter(new THREE.Vector3()));
}

function updateVisibility() {
  for (const m of anatomyMeshes) {
    const a = m.userData.anat;
    const catOn = catState[a.cat] !== false;
    const sideOn = hemi === 'both' || a.side === 'median' || a.side === hemi;
    m.visible = catOn && sideOn && (!matchSet || matchSet.has(m));
    const sp = m.userData.sprite;
    if (sp) sp.visible = labelsOn && m.visible;
  }
}

function setCat(cat, on) {
  catState[cat] = on;
  const box = document.querySelector(`input[data-cat="${cat}"]`);
  if (box) box.checked = on;
}

function allMats() {
  return [...Object.values(baseMats), ...Object.values(hoverMats),
    ...Object.values(selectMats), ...Object.values(trimMats.base),
    ...Object.values(trimMats.hover), ...Object.values(trimMats.select),
    ...labelMats];
}

// Clipping composition: the global slice plane plus, for cord-trimmed
// meshes, the brainstem-floor plane. Arrays are rebuilt from the live
// plane objects so later constant updates apply automatically.
function planesFor(trimmed) {
  if (trimmed && clipActive) return [slicePlane, trimPlane];
  if (trimmed) return [trimPlane];
  if (clipActive) return onePlane;
  return noPlanes;
}

const trimMats = { base: {}, hover: {}, select: {} };

function variantFor(mesh, kind) {
  const cat = mesh.userData.anat.cat;
  if (kind !== 'base') ensureVariants(cat);
  if (!mesh.userData.trimmed) {
    return kind === 'base' ? baseMats[cat] : kind === 'hover' ? hoverMats[cat] : selectMats[cat];
  }
  const cache = trimMats[kind];
  if (!cache[cat]) {
    const src = kind === 'base' ? baseMats[cat] : kind === 'hover' ? hoverMats[cat] : selectMats[cat];
    const m = src.clone();
    m.userData.trimmed = true;
    m.clippingPlanes = planesFor(true);
    cache[cat] = m;
  }
  return cache[cat];
}

function makeLabelSprite(mesh) {
  const anat = mesh.userData.anat;
  const side = anat.side === 'left' ? 'L' : anat.side === 'right' ? 'R' : '';
  const text = side ? `${anat.label} ${side}` : anat.label;
  const c = document.createElement('canvas');
  c.width = 256;
  c.height = 64;
  const g = c.getContext('2d');
  g.fillStyle = 'rgba(10, 13, 17, 0.78)';
  g.strokeStyle = 'rgba(255,255,255,0.35)';
  g.lineWidth = 2;
  if (g.roundRect) {
    g.beginPath();
    g.roundRect(3, 6, 250, 52, 10);
    g.fill();
    g.stroke();
  } else {
    g.fillRect(3, 6, 250, 52);
  }
  g.fillStyle = '#f2f5f7';
  g.font = '600 25px system-ui, sans-serif';
  g.textAlign = 'center';
  g.textBaseline = 'middle';
  g.fillText(text.length > 26 ? text.slice(0, 25) + '…' : text, 128, 33);
  const tex = new THREE.CanvasTexture(c);
  tex.anisotropy = 4;
  const mat = new THREE.SpriteMaterial({ map: tex, transparent: true, depthTest: false, depthWrite: false });
  mat.clippingPlanes = planesFor(false);
  labelMats.add(mat);
  const sp = new THREE.Sprite(mat);
  sp.scale.set(labelWorld * 2.4, labelWorld * 0.6, 1);
  const box = new THREE.Box3().setFromObject(mesh);
  const center = box.getCenter(new THREE.Vector3());
  center.y += box.getSize(new THREE.Vector3()).y * 0.5 + labelWorld * 0.5;
  sp.position.copy(center);
  sp.visible = false;
  mesh.userData.sprite = sp;
  scene.add(sp);
  return sp;
}

function applySlice() {
  if (sliceMode === 'off' || !sliceBounds) {
    if (clipActive) {
      clipActive = false;
      for (const m of allMats()) { m.clippingPlanes = planesFor(m.userData.trimmed); m.needsUpdate = true; }
    }
    return;
  }
  const normals = {
    x: new THREE.Vector3(1, 0, 0),
    z: new THREE.Vector3(0, 0, 1),
    y: new THREE.Vector3(0, 1, 0),
  };
  const [lo, hi] = sliceBounds[sliceMode];
  const pad = (hi - lo) * 0.02;
  const c = hi + pad - sliceT * (hi - lo + pad * 2);
  slicePlane.normal.copy(normals[sliceMode]);
  slicePlane.constant = -c;
  if (!clipActive) {
    clipActive = true;
    for (const m of allMats()) { m.clippingPlanes = planesFor(m.userData.trimmed); m.needsUpdate = true; }
  }
}

// Called by the Android host (X-ray chip): fade cortical surface.
window.setCortexOpacity = function (v) {
  const mat = baseMats.cortex;
  if (!mat) return;
  if (v >= 0.999) {
    mat.opacity = 1;
    mat.transparent = false;
    mat.depthWrite = true;
  } else {
    mat.opacity = v;
    mat.transparent = true;
    mat.depthWrite = v >= 0.35;
  }
  mat.needsUpdate = true;
};

function wireUI() {
  $('focusBtn').addEventListener('click', () => { if (selected) focusOn(selected); });

  const searchInput = $('search');
  const resultsBox = $('results');
  searchInput.addEventListener('input', () => {
    const q = searchInput.value.trim().toLowerCase();
    if (!q) {
      matchSet = null;
      resultsBox.hidden = true;
      resultsBox.innerHTML = '';
      updateVisibility();
      return;
    }
    const matches = searchIndex.filter((e) => e.hay.includes(q));
    matchSet = new Set(matches.map((e) => e.mesh));
    updateVisibility();
    resultsBox.hidden = false;
    resultsBox.innerHTML = '';
    const count = document.createElement('div');
    count.className = 'count';
    count.textContent = matches.length === 0 ? 'No structures found'
      : `${matches.length} match${matches.length === 1 ? '' : 'es'}`;
    resultsBox.appendChild(count);
    for (const e of matches.slice(0, 40)) {
      const b = document.createElement('button');
      b.type = 'button';
      const a = e.mesh.userData.anat;
      b.textContent = `${a.label}${a.side === 'left' || a.side === 'right' ? ` (${a.side})` : ''}`;
      b.addEventListener('click', () => { select(e.mesh); focusOn(e.mesh); });
      resultsBox.appendChild(b);
    }
  });

  $('labelToggle').addEventListener('change', (e) => {
    labelsOn = e.target.checked;
    if (labelsOn) {
      for (const m of anatomyMeshes) {
        if (!m.userData.sprite) makeLabelSprite(m);
      }
    }
    updateVisibility();
  });

  const sliceBtns = [...document.querySelectorAll('[data-slice]')];
  const sliceSlider = $('slicePos');
  sliceBtns.forEach((b) => {
    b.addEventListener('click', () => {
      sliceBtns.forEach((x) => x.classList.toggle('on', x === b));
      sliceMode = b.dataset.slice;
      sliceSlider.disabled = sliceMode === 'off';
      applySlice();
    });
  });
  sliceSlider.addEventListener('input', () => {
    sliceT = sliceSlider.value / 100;
    applySlice();
  });

  // ----- hemisphere: left / right / both (median always visible) -----
  const hemiBtns = [...document.querySelectorAll('[data-hemi]')];
  hemiBtns.forEach((b) => {
    b.addEventListener('click', () => {
      hemiBtns.forEach((x) => x.classList.toggle('on', x === b));
      hemi = b.dataset.hemi;
      updateVisibility();
    });
  });

  // ----- presets -----
  $('deepBtn').addEventListener('click', () => {
    for (const cat of ['cortex', 'cerebellum', 'meninges_dura']) setCat(cat, false);
    updateVisibility();
  });
  $('resetFiltersBtn').addEventListener('click', () => {
    for (const cat of Object.keys(CATEGORY_STYLE)) setCat(cat, true);
    hemi = 'both';
    hemiBtns.forEach((x) => x.classList.toggle('on', x.dataset.hemi === 'both'));
    const searchInput = $('search');
    searchInput.value = '';
    matchSet = null;
    $('results').hidden = true;
    $('results').innerHTML = '';
    sliceMode = 'off';
    document.querySelectorAll('.seg [data-slice]').forEach((x) =>
      x.classList.toggle('on', x.dataset.slice === 'off'));
    $('slicePos').disabled = true;
    applySlice();
    updateVisibility();
  });
}

const manager = new THREE.LoadingManager();
const loadErrors = [];
manager.onProgress = (_url, loaded, total) => {
  if (total > 0) {
    const pct = Math.round((loaded / total) * 100);
    $('loadfill').style.width = `${pct}%`;
  }
};
// Record-only: the decoder ladder below retries other paths, so never
// fail the whole load here. Failures surface in the final report.
manager.onError = (url) => {
  console.error('Failed to load asset:', url);
  loadErrors.push(url);
};

function withTimeout(promise, ms, label) {
  let t;
  const timeout = new Promise((_, reject) => {
    t = setTimeout(() => reject(new Error(`${label} timed out after ${ms / 1000}s`)), ms);
  });
  return Promise.race([promise, timeout]).finally(() => clearTimeout(t));
}

// Decoder ladder: WASM Draco -> asm.js Draco -> uncompressed fallback.
// Each rung covers a different broken environment (no WASM, no workers,
// incompatible decoder build). Errors accumulate for the final report.
async function loadModelLadder() {
  const failures = [];
  // Rung 1: Draco with default (WASM) decoder.
  const draco1 = new DRACOLoader(manager);
  draco1.setDecoderPath(DRACO_PATH);
  draco1.setWorkerLimit(1);
  try {
    step = 'loading 3D model (Draco)';
    $('loadmsg').textContent = 'Loading 3D model';
    const loader = new GLTFLoader(manager);
    loader.setDRACOLoader(draco1);
    const gltf = await withTimeout(loader.loadAsync(MODEL_URL), 90000, 'Draco model load');
    return gltf;
  } catch (e) {
    failures.push('draco-wasm: ' + (e && e.message ? e.message : e));
  } finally {
    try { draco1.dispose(); } catch (ignored) {}
  }
  // Rung 2: Draco with compatibility (asm.js) decoder, no WebAssembly needed.
  const draco2 = new DRACOLoader(manager);
  draco2.setDecoderPath(DRACO_PATH);
  draco2.setWorkerLimit(1);
  draco2.setDecoderConfig({ type: 'js' });
  try {
    step = 'retrying with compatibility decoder';
    $('loadmsg').textContent = 'Retrying with compatibility decoder…';
    const loader = new GLTFLoader(manager);
    loader.setDRACOLoader(draco2);
    const gltf = await withTimeout(loader.loadAsync(MODEL_URL), 120000, 'Compat-decoder model load');
    return gltf;
  } catch (e) {
    failures.push('draco-js: ' + (e && e.message ? e.message : e));
  } finally {
    try { draco2.dispose(); } catch (ignored) {}
  }
  // Rung 3: standard loader without worker / fallback
  try {
    step = 'loading standard model';
    $('loadmsg').textContent = 'Loading 3D model…';
    const loader = new GLTFLoader(manager);
    const gltf = await withTimeout(loader.loadAsync(MODEL_URL), 90000, 'Standard model load');
    return gltf;
  } catch (e) {
    failures.push('standard: ' + (e && e.message ? e.message : e));
  }
  const failedAssets = loadErrors.length ? ` Failed assets: ${[...new Set(loadErrors)].join(', ')}` : '';
  throw new Error(failures.join(' | ') + '.' + failedAssets);
}

async function init() {
  let step = 'starting renderer';
  try {
    step = 'fetching metadata';
    $('loadmsg').textContent = 'Fetching metadata';
    const [manifestRes, funcsRes] = await Promise.all([
      fetch(MANIFEST_URL),
      fetch(FUNCTIONS_URL).catch(() => null),
    ]);
    if (!manifestRes || !manifestRes.ok) {
      throw new Error(`metadata HTTP ${manifestRes ? manifestRes.status : 'unreachable'} @ ${MANIFEST_URL}`);
    }
    const manifest = await manifestRes.json();
    functions = funcsRes && funcsRes.ok ? await funcsRes.json().catch(() => ({})) : {};
  for (const n of manifest.nodes) manifestById.set(n.id, n);
  $('stats').textContent = `${manifest.nodes.length} structures · ${Object.keys(manifest.categories || {}).length} systems`;

  const catlist = $('catlist');
  for (const [catId, info] of Object.entries(manifest.categories || {})) {
    const style = CATEGORY_STYLE[catId] || {};
    const hex = `#${new THREE.Color(style.color ?? 0x999999).getHexString()}`;
    const label = document.createElement('label');
    label.innerHTML = `<input type="checkbox" checked data-cat="${catId}"/>` +
      `<span class="dot" style="background:${hex}"></span>` +
      `<span>${info.label || catId} (${info.count ?? 0})</span>`;
    catlist.appendChild(label);
  }
  catlist.addEventListener('change', (e) => {
    const cat = e.target.dataset.cat;
    if (!cat) return;
    catState[cat] = e.target.checked;
    updateVisibility();
    if (selected && !selected.visible) hideCard();
  });

  step = 'loading 3D model';
  const gltf = await loadModelLadder();
  const model = gltf.scene;

  model.traverse((obj) => {
    if (!obj.isMesh) return;
    let node = obj;
    let extra = null;
    while (node) {
      if (node.userData && node.userData.bx_id !== undefined) { extra = node.userData; break; }
      node = node.parent;
    }
    if (!extra) return;
    const rec = manifestById.get(extra.bx_id);
    const cat = extra.bx_cat || (rec && rec.category) || 'cortex';
    const anat = {
      id: extra.bx_id,
      label: extra.bx_label || (rec && rec.label) || obj.name,
      side: extra.bx_side || (rec && rec.side) || '',
      cat,
      region: extra.bx_region || (rec && rec.region) || '',
      parent: extra.bx_parent || (rec && rec.parent) || '',
      decussation: extra.bx_decussation || (rec && rec.decussation) || '',
      ta2: (rec && rec.ta2) || [],
      source: extra.bx_source || (rec && rec.source) || '',
    };
    if (!baseMats[cat]) baseMats[cat] = tissueMaterial(cat);
    if (TRIM_IDS.includes(anat.id)) obj.userData.trimmed = true;
    obj.material = variantFor(obj, 'base');
    obj.castShadow = false;
    obj.receiveShadow = false;
    obj.userData.anat = anat;
    searchIndex.push({
      mesh: obj,
      hay: `${anat.label} ${anat.region} ${anat.parent} ${(CATEGORY_STYLE[cat] || {}).label || cat}`.toLowerCase(),
    });
    anatomyMeshes.push(obj);
    if (!meshesByCat.has(cat)) meshesByCat.set(cat, []);
    meshesByCat.get(cat).push(obj);
  });

  if (anatomyMeshes.length === 0) throw new Error('Model loaded but no named structures found');

  // Brainstem floor: lowest point of medulla/pyramids/olives. Cord-bound
  // tails (TRIM_IDS) are clipped here; constant defaults to -Infinity
  // (keep everything) if the reference meshes are ever absent.
  step = 'trimming spinal cord tails';
  const trimRefBox = new THREE.Box3();
  let hasTrimRef = false;
  for (const m of anatomyMeshes) {
    if (TRIM_REF_IDS.includes(m.userData.anat.id)) {
      trimRefBox.expandByObject(m);
      hasTrimRef = true;
    }
  }
  if (hasTrimRef) trimPlane.constant = trimRefBox.min.y;

  scene.add(model);
  const bbox = new THREE.Box3().setFromObject(model);
  const center = bbox.getCenter(new THREE.Vector3());
  const size = bbox.getSize(new THREE.Vector3()).length();
  const dir = new THREE.Vector3(0.55, 0.32, 1).normalize();
  homeTarget.copy(center);
  homePos.copy(center).addScaledVector(dir, size * 1.35);
  camera.position.copy(homePos);
  controls.target.copy(homeTarget);
  camera.near = size / 1000;
  camera.far = size * 100;
  camera.updateProjectionMatrix();

  sliceBounds = { x: [bbox.min.x, bbox.max.x], y: [bbox.min.y, bbox.max.y], z: [bbox.min.z, bbox.max.z] };
  labelWorld = size * 0.028;

  wireUI();
  $('loadmsg').textContent = 'Ready';
  $('loader').classList.add('done');
  } catch (err) {
    console.error(err);
    fail(step, err && err.message ? err.message : String(err));
  }
}

init();

window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  if (renderer) renderer.setSize(window.innerWidth, window.innerHeight);
});

if (renderer) {
  renderer.setAnimationLoop(() => {
    if (controls) controls.update();
    renderer.render(scene, camera);
  });

  const cleanup = () => {
    try {
      renderer.setAnimationLoop(null);
      renderer.dispose();
    } catch (ignored) {}
  };
  window.addEventListener('beforeunload', cleanup);
  window.addEventListener('pagehide', cleanup);
}
