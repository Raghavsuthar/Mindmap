package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityDetailBottomSheet(
    entityId: String,
    entityLayer: EntityLayer,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onDismiss: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit,
    onOpenFullDrugDetail: (drugId: String) -> Unit,
    onHighlightOnMap: (circuitId: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val colors = LocalLayerColors.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = colors.elevatedCard,
        contentColor = colors.textHigh,
        tonalElevation = 6.dp,
        dragHandle = {
            BottomSheetDefaults.DragHandle(color = colors.hairlineBorder)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (entityLayer) {
                EntityLayer.CIRCUITS -> {
                    val circuit = NeuroMapRepository.getCircuitById(entityId)
                    if (circuit != null) {
                        CircuitDetailContent(
                            circuit = circuit,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = onToggleBookmark,
                            onEntityClick = onEntityClick,
                            onHighlightOnMap = {
                                onDismiss()
                                onHighlightOnMap(circuit.id)
                            }
                        )
                    }
                }
                EntityLayer.TRANSMITTERS -> {
                    val nt = NeuroMapRepository.getTransmitterById(entityId)
                    if (nt != null) {
                        TransmitterDetailContent(
                            nt = nt,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = onToggleBookmark,
                            onEntityClick = onEntityClick
                        )
                    }
                }
                EntityLayer.SYNDROMES -> {
                    val syn = NeuroMapRepository.getSyndromeById(entityId)
                    if (syn != null) {
                        SyndromeDetailContent(
                            syn = syn,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = onToggleBookmark,
                            onEntityClick = onEntityClick
                        )
                    }
                }
                EntityLayer.DRUGS -> {
                    val drug = NeuroMapRepository.getDrugById(entityId)
                    if (drug != null) {
                        DrugDetailQuickContent(
                            drug = drug,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = onToggleBookmark,
                            onEntityClick = onEntityClick,
                            onOpenFullDrugDetail = {
                                onDismiss()
                                onOpenFullDrugDetail(drug.id)
                            }
                        )
                    }
                }
            }

            SafetyNoticeBanner(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun CircuitDetailContent(
    circuit: com.example.data.model.Circuit,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit,
    onHighlightOnMap: () -> Unit
) {
    val colors = LocalLayerColors.current
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                LayerBadge(layer = EntityLayer.CIRCUITS, text = circuit.tierCode)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = circuit.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textHigh,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.testTag("bookmark_button")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save circuit",
                    tint = if (isBookmarked) CircuitPrimary else colors.textSecondary
                )
            }
        }

        // Action button to highlight on map
        OutlinedButton(
            onClick = onHighlightOnMap,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("highlight_map_button"),
            border = BorderStroke(1.dp, CircuitPrimary),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = CircuitPrimary
            )
        ) {
            Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Highlight Circuit On Connectome Map")
        }

        // Functional summary
        Surface(
            color = colors.circuitContainer.copy(alpha = 0.4f),
            border = BorderStroke(1.dp, colors.hairlineBorder),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "PRIMARY PHYSIOLOGICAL FUNCTION",
                    style = MaterialTheme.typography.labelSmall,
                    color = CircuitPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = circuit.function,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textHigh
                )
            }
        }

        Text(
            text = circuit.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            lineHeight = 20.sp
        )

        // Brain structures
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "KEY ANATOMICAL STRUCTURES",
                style = MaterialTheme.typography.labelSmall,
                color = colors.textSecondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = circuit.brainStructures.joinToString("  →  "),
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                color = CircuitPrimary,
                fontWeight = FontWeight.Medium
            )
        }

        Divider(color = colors.hairlineBorder)

        CrossLayerConnectionsSection(
            linkedTransmitters = circuit.linkedTransmitters,
            linkedSyndromes = circuit.linkedSyndromes,
            linkedDrugs = circuit.linkedDrugs,
            onEntityClick = onEntityClick
        )
    }
}

@Composable
fun TransmitterDetailContent(
    nt: com.example.data.model.Neurotransmitter,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit
) {
    val colors = LocalLayerColors.current
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                LayerBadge(layer = EntityLayer.TRANSMITTERS, text = nt.symbol)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = nt.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textHigh,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.testTag("bookmark_button")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save transmitter",
                    tint = if (isBookmarked) TransmitterPrimary else colors.textSecondary
                )
            }
        }

        Text(
            text = nt.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            lineHeight = 20.sp
        )

        // Receptors Table
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "PRIMARY RECEPTOR SUBTYPES & SIGNALING",
                style = MaterialTheme.typography.labelSmall,
                color = TransmitterPrimary,
                fontWeight = FontWeight.Bold
            )
            nt.receptorSubtypes.forEach { receptor ->
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = receptor.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.textHigh
                            )
                            Text(
                                text = receptor.mechanism,
                                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                                color = TransmitterPrimary
                            )
                        }
                        Text(
                            text = receptor.primaryFunction,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                        Text(
                            text = "Distribution: ${receptor.brainLocations}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp),
                            color = colors.textSecondary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Divider(color = colors.hairlineBorder)

        CrossLayerConnectionsSection(
            linkedCircuits = nt.linkedCircuits,
            linkedSyndromes = nt.linkedSyndromes,
            linkedDrugs = nt.linkedDrugs,
            onEntityClick = onEntityClick
        )
    }
}

@Composable
fun SyndromeDetailContent(
    syn: com.example.data.model.Syndrome,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit
) {
    val colors = LocalLayerColors.current
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    LayerBadge(layer = EntityLayer.SYNDROMES, text = syn.category)
                    Surface(
                        color = colors.syndromeContainer,
                        border = BorderStroke(1.dp, colors.syndromeBorder),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "ICD-11: ${syn.icd11Code}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp
                            ),
                            color = colors.syndrome,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = syn.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textHigh,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "DSM-5 Diagnostic Code: ${syn.dsm5Code}",
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                    color = colors.textSecondary
                )
            }
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.testTag("bookmark_button")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save syndrome",
                    tint = if (isBookmarked) SyndromePrimary else colors.textSecondary
                )
            }
        }

        // Definition
        Surface(
            color = colors.elevatedCard,
            border = BorderStroke(1.dp, colors.hairlineBorder),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "CLINICAL DEFINITION & CRITERIA",
                    style = MaterialTheme.typography.labelSmall,
                    color = SyndromePrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = syn.clinicalDefinition,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textHigh,
                    lineHeight = 20.sp
                )
            }
        }

        // Pathophysiological hallmark
        Surface(
            color = colors.syndromeContainer.copy(alpha = 0.35f),
            border = BorderStroke(1.dp, colors.hairlineBorder),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "NEUROBIOLOGICAL HALLMARK",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.syndrome,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = syn.primaryPathophysiologicalHallmark,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textHigh
                )
            }
        }

        Divider(color = colors.hairlineBorder)

        CrossLayerConnectionsSection(
            linkedCircuits = syn.linkedCircuits,
            linkedTransmitters = syn.linkedTransmitters,
            linkedDrugs = syn.linkedDrugs,
            onEntityClick = onEntityClick
        )
    }
}

@Composable
fun DrugDetailQuickContent(
    drug: com.example.data.model.Drug,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit,
    onOpenFullDrugDetail: () -> Unit
) {
    val colors = LocalLayerColors.current
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    LayerBadge(layer = EntityLayer.DRUGS, text = drug.drugClass)
                    Surface(
                        color = colors.drugContainer,
                        border = BorderStroke(1.dp, colors.drugBorder),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "ATC: ${drug.atcCode}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.5.sp
                            ),
                            color = colors.drug,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = drug.genericName,
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textHigh,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Brand: ${drug.brandName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.testTag("bookmark_button")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save drug",
                    tint = if (isBookmarked) DrugPrimary else colors.textSecondary
                )
            }
        }

        // Full Profile Nav Button
        Button(
            onClick = onOpenFullDrugDetail,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("open_full_drug_detail_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = DrugBorder,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(6.dp)
        ) {
            Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Open Full Receptor & PK Profile")
        }

        // Quick Clinical Highlights
        Surface(
            color = colors.elevatedCard,
            border = BorderStroke(1.dp, colors.hairlineBorder),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "HIGH-YIELD CLINICAL PEARL",
                    style = MaterialTheme.typography.labelSmall,
                    color = DrugPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = drug.clinicalPearls,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textHigh
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Target Dosing:",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                    Text(
                        text = drug.dosingRange.targetDose,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = colors.textHigh
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Elimination T½:",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                    Text(
                        text = drug.pharmacokinetics.halfLife.take(30),
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        color = colors.textHigh
                    )
                }
            }
        }

        Divider(color = colors.hairlineBorder)

        CrossLayerConnectionsSection(
            linkedCircuits = drug.linkedCircuits,
            linkedTransmitters = drug.linkedTransmitters,
            linkedSyndromes = drug.linkedSyndromes,
            onEntityClick = onEntityClick
        )
    }
}
