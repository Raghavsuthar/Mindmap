package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import com.example.data.model.Source
import com.example.ui.theme.*

// Official classification browsers. Codes link out; criteria text is never
// reproduced in-app.
const val ICD11_BROWSER_URL = "https://icd.who.int/browse11/l-m/en"
const val DSM_OVERVIEW_URL = "https://www.psychiatry.org/psychiatrists/practice/dsm"

@Composable
fun getLayerColor(layer: EntityLayer): Color {
    val colors = LocalLayerColors.current
    return when (layer) {
        EntityLayer.CIRCUITS -> colors.circuit
        EntityLayer.TRANSMITTERS -> colors.transmitter
        EntityLayer.SYNDROMES -> colors.syndrome
        EntityLayer.DRUGS -> colors.drug
    }
}

@Composable
fun getLayerBorderColor(layer: EntityLayer): Color {
    val colors = LocalLayerColors.current
    return when (layer) {
        EntityLayer.CIRCUITS -> colors.circuitBorder
        EntityLayer.TRANSMITTERS -> colors.transmitterBorder
        EntityLayer.SYNDROMES -> colors.syndromeBorder
        EntityLayer.DRUGS -> colors.drugBorder
    }
}

@Composable
fun getLayerContainerColor(layer: EntityLayer): Color {
    val colors = LocalLayerColors.current
    return when (layer) {
        EntityLayer.CIRCUITS -> colors.circuitContainer
        EntityLayer.TRANSMITTERS -> colors.transmitterContainer
        EntityLayer.SYNDROMES -> colors.syndromeContainer
        EntityLayer.DRUGS -> colors.drugContainer
    }
}

fun getLayerIcon(layer: EntityLayer): ImageVector {
    return when (layer) {
        EntityLayer.CIRCUITS -> Icons.Outlined.Hub
        EntityLayer.TRANSMITTERS -> Icons.Outlined.Bolt
        EntityLayer.SYNDROMES -> Icons.Outlined.Psychology
        EntityLayer.DRUGS -> Icons.Outlined.Medication
    }
}

@Composable
fun LayerBadge(
    layer: EntityLayer,
    text: String = layer.singular,
    modifier: Modifier = Modifier
) {
    val color = getLayerColor(layer)
    val border = getLayerBorderColor(layer)
    val container = getLayerContainerColor(layer)

    Surface(
        modifier = modifier.clip(RoundedCornerShape(4.dp)),
        color = container,
        border = BorderStroke(1.dp, border.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = getLayerIcon(layer),
                contentDescription = null,
                modifier = Modifier.size(11.dp),
                tint = color
            )
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.5.sp,
                    letterSpacing = 0.5.sp
                ),
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LayerChip(
    entityId: String,
    layer: EntityLayer,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    customLabel: String? = null
) {
    val label = customLabel ?: NeuroMapRepository.getEntityTitle(entityId)
    val color = getLayerColor(layer)
    val border = getLayerBorderColor(layer)
    val container = getLayerContainerColor(layer)

    Surface(
        modifier = modifier
            .testTag("chip_${entityId}")
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick),
        color = container.copy(alpha = 0.75f),
        border = BorderStroke(1.dp, border)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CrossLayerConnectionsSection(
    linkedCircuits: List<String> = emptyList(),
    linkedTransmitters: List<String> = emptyList(),
    linkedSyndromes: List<String> = emptyList(),
    linkedDrugs: List<String> = emptyList(),
    onEntityClick: (id: String, layer: EntityLayer) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = CircuitPrimary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "CROSS-LAYER CONNECTIONS",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }

        if (linkedCircuits.isNotEmpty()) {
            CrossLayerGroup(
                title = "Associated Circuits",
                layer = EntityLayer.CIRCUITS,
                ids = linkedCircuits,
                onEntityClick = onEntityClick
            )
        }

        if (linkedTransmitters.isNotEmpty()) {
            CrossLayerGroup(
                title = "Neurotransmitter Systems",
                layer = EntityLayer.TRANSMITTERS,
                ids = linkedTransmitters,
                onEntityClick = onEntityClick
            )
        }

        if (linkedSyndromes.isNotEmpty()) {
            CrossLayerGroup(
                title = "Clinical Indications / Syndromes",
                layer = EntityLayer.SYNDROMES,
                ids = linkedSyndromes,
                onEntityClick = onEntityClick
            )
        }

        if (linkedDrugs.isNotEmpty()) {
            CrossLayerGroup(
                title = "Modulating Psychotropics",
                layer = EntityLayer.DRUGS,
                ids = linkedDrugs,
                onEntityClick = onEntityClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CrossLayerGroup(
    title: String,
    layer: EntityLayer,
    ids: List<String>,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(getLayerColor(layer))
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = getLayerColor(layer)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ids.forEach { entityId ->
                LayerChip(
                    entityId = entityId,
                    layer = layer,
                    onClick = { onEntityClick(entityId, layer) }
                )
            }
        }
    }
}

@Composable
fun SourcesSection(
    sources: List<Source>,
    modifier: Modifier = Modifier
) {
    if (sources.isEmpty()) return
    val uriHandler = LocalUriHandler.current
    val colors = LocalLayerColors.current
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = colors.textSecondary,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = "FURTHER READING — GUIDELINES & REFERENCES",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.8.sp),
                color = colors.textSecondary,
                fontWeight = FontWeight.Bold
            )
        }
        sources.forEach { source ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { uriHandler.openUri(source.url) }
                    .testTag("source_${source.name.filter { it.isLetterOrDigit() }.take(24)}"),
                color = colors.elevatedCard.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, colors.hairlineBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = source.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textHigh,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Open ${source.name}",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SafetyNoticeBanner(modifier: Modifier = Modifier) {
    val colors = LocalLayerColors.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        color = colors.elevatedCard.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, colors.hairlineBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Medical Disclaimer",
                tint = colors.textSecondary,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = "Reference only — not primary diagnostic advice. Verify local dosing guidelines.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = colors.textSecondary
                )
            )
        }
    }
}
