package com.example.data

import org.json.JSONArray
import org.json.JSONObject

/**
 * Evidence-based 3D stereotactic atlas for NeuroMap.
 *
 * Approximate MNI152 centroids (x, y, z in mm) compiled from:
 * - AAL3 atlas (Rolls et al., NeuroImage 2020)
 * - BrainMap / Talairach Daemon converge zones (Fox et al.)
 * - Mai / Paxinos human brainstem atlas for LC, DRN, VTA, SNc
 *
 * Values are intentionally approximate cortical/subcortical centroids for
 * education — not surgical targets. Single-hemisphere biased positions are
 * used where bilateral (right-sided) for 3D readability; midline structures
 * use x ≈ 0.
 */
data class AtlasNode3D(
    val regionId: String,
    val mniX: Int,
    val mniY: Int,
    val mniZ: Int,
    val system: String
)

object BrainAtlas3D {

    val nodes: List<AtlasNode3D> = listOf(
        AtlasNode3D("dlpfc", -38, 32, 28, "prefrontal"),
        AtlasNode3D("vmpfc", 2, 44, -10, "prefrontal"),
        AtlasNode3D("ofc", 4, 34, -14, "prefrontal"),
        AtlasNode3D("dacc", 2, 20, 30, "cingulate"),
        AtlasNode3D("insula", 36, 18, 2, "paralimbic"),
        AtlasNode3D("pcc", -2, -52, 28, "cingulate"),
        AtlasNode3D("caudate", 12, 10, 12, "basal_ganglia"),
        AtlasNode3D("nacc", 10, 10, -6, "basal_ganglia"),
        AtlasNode3D("amygdala", 22, -4, -16, "limbic"),
        AtlasNode3D("hippocampus", 26, -16, -14, "limbic"),
        AtlasNode3D("thalamus", 8, -14, 8, "diencephalon"),
        AtlasNode3D("hypothalamus", 2, -4, -8, "diencephalon"),
        AtlasNode3D("vta", -4, -16, -10, "midbrain"),
        AtlasNode3D("sn", 10, -18, -12, "midbrain"),
        AtlasNode3D("locus_coeruleus", -4, -36, -22, "brainstem"),
        AtlasNode3D("raphe_nuclei", 0, -28, -8, "brainstem")
    )

    /** MNI (mm) -> Three.js scene units. Anterior (+y MNI) = +z, superior (+z MNI) = +y. */
    fun toScene(mniX: Int, mniY: Int, mniZ: Int): Triple<Float, Float, Float> {
        val x = (mniX / 45f) * 1.15f
        val y = (mniZ / 45f) * 1.25f
        val z = (mniY / 55f) * 1.55f
        return Triple(x, y, z)
    }

    /**
     * Explicit coarse mapping from a tapped true-anatomy structure onto the
     * 16-region clinical model. Returns the coarse region id, or null when no
     * defensible mapping exists — the caller then shows the atlas card alone
     * instead of guessing. Rules use manifest label/region/source/category.
     */
    fun mapToCoarseRegion(
        label: String,
        region: String,
        source: String,
        category: String
    ): String? {
        val l = label.lowercase()
        val s = source.lowercase()
        if ("amygdala" in s || "amygdala" in l) return "amygdala"
        if ("hippocamp" in l) return "hippocampus"
        if ("fornix" in l || "stria terminalis" in l) return "hippocampus"
        if ("caudate" in l || "putamen" in l) return "caudate"
        if ("accumbens" in l) return "nacc"
        if ("thalam" in l) return "thalamus"
        if ("hypothalam" in l || "pituitary" in l || "adenohypophysis" in l ||
            "neurohypophysis" in l || "mamillary" in l
        ) return "hypothalamus"
        if ("substantia nigra" in l) return "sn"
        if ("insula" in l && category == "cortex") return "insula"
        // NOTE: `region` (e.g. "Frontal lobe") is deliberately unused: lobe-level
        // granularity cannot identify one of the 16 coarse regions without guessing.
        return null
    }

    fun scenePos(regionId: String): Triple<Float, Float, Float> {
        val n = nodes.firstOrNull { it.regionId == regionId } ?: return Triple(0f, 0f, 0f)
        return toScene(n.mniX, n.mniY, n.mniZ)
    }

    /** Full payload consumed by assets/brain3d/brain3d.html via initBrain(json). */
    fun toJsonPayload(): String {
        val regions = JSONArray()
        NeuroMapRepository.brainRegions.forEach { r ->
            val atlas = nodes.firstOrNull { it.regionId == r.id }
            val (sx, sy, sz) = if (atlas != null) toScene(atlas.mniX, atlas.mniY, atlas.mniZ)
            else Triple(r.xPercent * 2 - 1, 0.5f - r.yPercent, 0f)
            val o = JSONObject()
            o.put("id", r.id)
            o.put("name", r.name)
            o.put("abbr", r.abbreviation)
            o.put("role", r.role)
            o.put("group", r.anatomicalGroup)
            o.put("system", atlas?.system ?: "cortex")
            o.put("mni", JSONArray(listOf(atlas?.mniX ?: 0, atlas?.mniY ?: 0, atlas?.mniZ ?: 0)))
            o.put("pos", JSONArray(listOf(sx, sy, sz)))
            o.put("circuits", JSONArray(r.associatedCircuits))
            regions.put(o)
        }
        val circuits = JSONArray()
        val palette = listOf("#38BDF8", "#2DD4BF", "#F59E0B", "#A855F7", "#F472B6", "#A3E635", "#FB7185")
        NeuroMapRepository.circuits.forEachIndexed { i, c ->
            val o = JSONObject()
            o.put("id", c.id)
            o.put("name", c.name)
            o.put("tierCode", c.tierCode)
            o.put("function", c.function)
            o.put("color", palette[i % palette.size])
            o.put("nodes", JSONArray(c.keyNodes))
            // Full link map for one-tap cross-layer jumps
            o.put("transmitters", JSONArray(c.linkedTransmitters))
            o.put("syndromes", JSONArray(c.linkedSyndromes))
            o.put("drugs", JSONArray(c.linkedDrugs))
            circuits.put(o)
        }
        // Transmitter -> region affinity (for layer tinting in JS)
        val txLinks = JSONObject()
        NeuroMapRepository.neurotransmitters.forEach { tx ->
            txLinks.put(tx.id, JSONArray(tx.linkedCircuits))
        }
        val root = JSONObject()
        root.put("regions", regions)
        root.put("circuits", circuits)
        root.put("txLinks", txLinks)
        return root.toString()
    }
}
