package com.example.data.model

enum class EntityLayer(
    val title: String,
    val singular: String,
    val codePrefix: String
) {
    CIRCUITS("Circuits", "Circuit", "CIRC"),
    TRANSMITTERS("Neurotransmitters", "Transmitter", "NT"),
    SYNDROMES("Syndromes", "Syndrome", "DX"),
    DRUGS("Psychotropics", "Drug", "RX")
}

data class Circuit(
    val id: String,
    val name: String,
    val tierCode: String,
    val brainStructures: List<String>,
    val function: String,
    val description: String,
    val keyNodes: List<String>,
    val linkedTransmitters: List<String>,
    val linkedSyndromes: List<String>,
    val linkedDrugs: List<String>
)

data class Neurotransmitter(
    val id: String,
    val name: String,
    val symbol: String,
    val description: String,
    val receptorSubtypes: List<ReceptorSubtype>,
    val majorPathways: List<String>,
    val linkedCircuits: List<String>,
    val linkedSyndromes: List<String>,
    val linkedDrugs: List<String>
)

data class ReceptorSubtype(
    val name: String,
    val mechanism: String,
    val primaryFunction: String,
    val brainLocations: String
)

data class Syndrome(
    val id: String,
    val name: String,
    val icd11Code: String,
    val dsm5Code: String,
    val category: String,
    val clinicalDefinition: String,
    val primaryPathophysiologicalHallmark: String,
    val linkedCircuits: List<String>,
    val linkedTransmitters: List<String>,
    val linkedDrugs: List<String>,
    val summary: String = "",
    val sources: List<Source> = emptyList()
)

data class Source(
    val name: String,
    val url: String
)

data class Drug(
    val id: String,
    val genericName: String,
    val brandName: String,
    val drugClass: String,
    val atcCode: String,
    val receptorTargets: List<ReceptorTarget>,
    val pharmacokinetics: Pharmacokinetics,
    val dosingRange: DosingRange,
    val commonSideEffects: List<String>,
    val sideEffectMechanisms: List<SideEffectMechanism>,
    val blackBoxWarnings: List<String>,
    val linkedSyndromes: List<String>,
    val linkedCircuits: List<String>,
    val linkedTransmitters: List<String>,
    val clinicalPearls: String,
    val summary: String = "",
    val sources: List<Source> = emptyList()
)

data class ReceptorTarget(
    val target: String,
    val action: String,
    val kiNm: Double?,
    val affinityRating: String,
    val clinicalRelevance: String
)

data class Pharmacokinetics(
    val halfLife: String,
    val bioavailability: String,
    val cypMetabolism: String,
    val timeToPeak: String
)

data class DosingRange(
    val startingDose: String,
    val targetDose: String,
    val maxDose: String,
    val titrationSchedule: String
)

data class SideEffectMechanism(
    val symptom: String,
    val receptorMediation: String,
    val clinicalManagement: String
)

data class BrainRegion(
    val id: String,
    val name: String,
    val abbreviation: String,
    val anatomicalGroup: String,
    val role: String,
    val xPercent: Float, // 0.0 to 1.0 on brain map
    val yPercent: Float, // 0.0 to 1.0 on brain map
    val associatedCircuits: List<String>
)

data class SearchResult(
    val id: String,
    val name: String,
    val subtitle: String,
    val layer: EntityLayer,
    val codeBadge: String,
    val summary: String
)
