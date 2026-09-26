import * as THREE from 'three';
import { OrbitControls } from 'three/addons/controls/OrbitControls.js';
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js';
import { DRACOLoader } from 'three/addons/loaders/DRACOLoader.js';
import { RoomEnvironment } from 'three/addons/environments/RoomEnvironment.js';

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
  cortex: { color: 0xc79a90, roughness: 0.42, label: 'Cortex' },
  cerebellum: { color: 0xbd9489, roughness: 0.46, label: 'Cerebellum' },
  brainstem: { color: 0xb3937a, roughness: 0.5, label: 'Brainstem' },
  deep_grey: { color: 0x9c6a5c, roughness: 0.48, label: 'Deep grey' },
  diencephalon: { color: 0xa67e6b, roughness: 0.48, label: 'Diencephalon' },
  white_matter: { color: 0xe2d6c2, roughness: 0.55, label: 'White matter' },
  tracts: { color: 0xd6c5a2, roughness: 0.5, label: 'Tracts' },
  ventricles: { color: 0xafcadd, roughness: 0.25, opacity: 0.55, label: 'Ventricles' },
  arteries: { color: 0xa83a32, roughness: 0.35, label: 'Arteries' },
  veins_sinuses: { color: 0x416b99, roughness: 0.35, label: 'Veins & sinuses' },
  cranial_nerves: { color: 0xd3b268, roughness: 0.45, label: 'Cranial nerves' },
  meninges_dura: { color: 0xc9ced1, roughness: 0.6, opacity: 0.35, label: 'Meninges' },
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
}
$('retryBtn').addEventListener('click', () => window.location.reload());
window.addEventListener('error', (e) => {
  if (!$('loader').classList.contains('done')) fail('Error: ' + (e.message || 'failed to start'));
});

const container = $('scene');
const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.toneMapping = THREE.ACESFilmicToneMapping;
renderer.toneMappingExposure = 1.1;
renderer.shadowMap.enabled = true;
renderer.shadowMap.type = THREE.PCFSoftShadowMap;
renderer.localClippingEnabled = true;
container.appendChild(renderer.domElement);

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(40, window.innerWidth / window.innerHeight, 0.01, 100);
camera.position.set(0.35, 0.22, 0.62);

const pmrem = new THREE.PMREMGenerator(renderer);
scene.environment = pmrem.fromScene(new RoomEnvironment(), 0.04).texture;

scene.add(new THREE.HemisphereLight(0xfff1e6, 0x232b34, 0.55));
const key = new THREE.DirectionalLight(0xfff4ea, 1.6);
key.position.set(0.6, 0.9, 0.7);
key.castShadow = true;
key.shadow.mapSize.set(1024, 1024);
key.shadow.camera.near = 0.01;
key.shadow.camera.far = 5;
key.shadow.bias = -0.0002;
scene.add(key);
const fill = new THREE.DirectionalLight(0xdfe8ff, 0.45);
fill.position.set(-0.7, 0.1, 0.5);
scene.add(fill);
const rim = new THREE.DirectionalLight(0xffe2d2, 0.9);
rim.position.set(-0.2, 0.4, -0.8);
scene.add(rim);

const controls = new OrbitControls(camera, renderer.domElement);
controls.enableDamping = true;
controls.dampingFactor = 0.08;
controls.autoRotate = true;
controls.autoRotateSpeed = 0.7;
controls.minDistance = 0.05;
controls.maxDistance = 3;

let spinWanted = true;
let idleTimer = null;
controls.addEventListener('start', () => {
  controls.autoRotate = false;
  if (idleTimer) clearTimeout(idleTimer);
});
controls.addEventListener('end', () => {
  if (idleTimer) clearTimeout(idleTimer);
  idleTimer = setTimeout(() => { if (spinWanted) controls.autoRotate = true; }, 5000);
});

function tissueMaterial(cat) {
  const style = CATEGORY_STYLE[cat] || { color: 0xb9a89c, roughness: 0.5 };
  const color = new THREE.Color(style.color);
  let mat;
  if (cat === 'cortex' || cat === 'cerebellum') {
    mat = new THREE.MeshPhysicalMaterial({
      color, roughness: style.roughness, metalness: 0.0,
      clearcoat: 0.5, clearcoatRoughness: 0.55,
      sheen: 1.0, sheenColor: new THREE.Color(0xffd9cd), sheenRoughness: 0.55,
      envMapIntensity: 0.65,
    });
  } else {
    mat = new THREE.MeshStandardMaterial({
      color, roughness: style.roughness, metalness: 0.0, envMapIntensity: 0.7,
    });
  }
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
    m.emissiveIntensity = 1;
    hoverMats[cat] = m;
  }
  if (!selectMats[cat]) {
    const m = baseMats[cat].clone();
    m.emissive = new THREE.Color(baseMats[cat].color).multiplyScalar(0.5);
    m.emissiveIntensity = 1;
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
renderer.domElement.addEventListener('pointerdown', (e) => { downX = e.clientX; downY = e.clientY; });
renderer.domElement.addEventListener('pointerup', (e) => {
  if (Math.hypot(e.clientX - downX, e.clientY - downY) > 8) return;
  select(pickAt(e.clientX, e.clientY));
});
renderer.domElement.addEventListener('pointermove', (e) => {
  if (e.pointerType === 'touch' || e.buttons !== 0) return;
  setHovered(pickAt(e.clientX, e.clientY));
});

function focusOn(mesh) {
  controls.target.copy(new THREE.Box3().setFromObject(mesh).getCenter(new THREE.Vector3()));
}

function updateVisibility() {
  for (const m of anatomyMeshes) {
    const catOn = catState[m.userData.anat.cat] !== false;
    m.visible = catOn && (!matchSet || matchSet.has(m));
    const sp = m.userData.sprite;
    if (sp) sp.visible = labelsOn && m.visible;
  }
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

  const segBtns = [...document.querySelectorAll('.seg button')];
  const sliceSlider = $('slicePos');
  segBtns.forEach((b) => {
    b.addEventListener('click', () => {
      segBtns.forEach((x) => x.classList.toggle('on', x === b));
      sliceMode = b.dataset.slice;
      sliceSlider.disabled = sliceMode === 'off';
      applySlice();
    });
  });
  sliceSlider.addEventListener('input', () => {
    sliceT = sliceSlider.value / 100;
    applySlice();
  });
}

const manager = new THREE.LoadingManager();
manager.onProgress = (_url, loaded, total) => {
  $('loadfill').style.width = `${Math.round((loaded / total) * 100)}%`;
};

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

  $('loadmsg').textContent = 'Loading 3D model';
  step = 'loading 3D model';
  const draco = new DRACOLoader(manager);
  draco.setDecoderPath(DRACO_PATH);
  const loader = new GLTFLoader(manager);
  loader.setDRACOLoader(draco);

  const gltf = await loader.loadAsync(MODEL_URL);
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
      ta2: (rec && rec.ta2) || [],
      source: extra.bx_source || (rec && rec.source) || '',
    };
    if (!baseMats[cat]) baseMats[cat] = tissueMaterial(cat);
    if (TRIM_IDS.includes(anat.id)) obj.userData.trimmed = true;
    obj.material = variantFor(obj, 'base');
    obj.castShadow = true;
    obj.receiveShadow = true;
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
  renderer.setSize(window.innerWidth, window.innerHeight);
});

renderer.setAnimationLoop(() => {
  controls.update();
  renderer.render(scene, camera);
});
