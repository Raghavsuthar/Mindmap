package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import com.example.ui.components.LayerBadge
import com.example.ui.components.SafetyNoticeBanner
import com.example.ui.components.getLayerColor
import com.example.ui.components.getLayerContainerColor
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToMap: (circuitId: String?) -> Unit,
    onNavigateToSearch: (initialQuery: String) -> Unit,
    onNavigateToTaxonomy: (layer: EntityLayer) -> Unit,
    onEntitySelect: (id: String, layer: EntityLayer) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalLayerColors.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                color = colors.elevatedCard,
                border = BorderStroke(1.dp, colors.hairlineBorder)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF0F172A),
                                    colors.elevatedCard
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = CircuitContainerDark,
                                border = BorderStroke(1.dp, CircuitBorder),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "CLINICAL REFERENCE v1.0",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        letterSpacing = 0.8.sp
                                    ),
                                    color = CircuitPrimary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "DSM-5-TR • ICD-11",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = colors.textSecondary
                            )
                        }

                        Text(
                            text = "NeuroMap",
                            style = MaterialTheme.typography.displayLarge,
                            color = colors.textHigh,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Psychiatric neurocircuitry & psychopharmacology cross-layer architecture. Explore connections between circuits, neurotransmitters, clinical syndromes, and drug affinities.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Global Search Bar Trigger
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onNavigateToSearch("") }
                    .testTag("home_search_bar"),
                color = colors.elevatedCard,
                border = BorderStroke(1.dp, colors.hairlineBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Search circuits, drugs (Ki), syndromes (ICD/DSM)...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        color = colors.hairlineBorder.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "⌘K",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace
                            ),
                            color = colors.textSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // 4 Taxonomy Navigator Cards
        item {
            Text(
                text = "TAXONOMY LAYERS",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = colors.textSecondary,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaxonomyCard(
                        layer = EntityLayer.CIRCUITS,
                        count = NeuroMapRepository.circuits.size,
                        highlight = "CSTC, DMN, Mesolimbic, Salience",
                        onClick = { onNavigateToTaxonomy(EntityLayer.CIRCUITS) },
                        modifier = Modifier.weight(1f)
                    )
                    TaxonomyCard(
                        layer = EntityLayer.TRANSMITTERS,
                        count = NeuroMapRepository.neurotransmitters.size,
                        highlight = "5-HT, DA, NE, GABA, Glu, ACh",
                        onClick = { onNavigateToTaxonomy(EntityLayer.TRANSMITTERS) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaxonomyCard(
                        layer = EntityLayer.SYNDROMES,
                        count = NeuroMapRepository.syndromes.size,
                        highlight = "MDD, Schizo, Bipolar, OCD, PTSD",
                        onClick = { onNavigateToTaxonomy(EntityLayer.SYNDROMES) },
                        modifier = Modifier.weight(1f)
                    )
                    TaxonomyCard(
                        layer = EntityLayer.DRUGS,
                        count = NeuroMapRepository.drugs.size,
                        highlight = "SSRIs, SNRIs, SGAs, Mood Stabilizers",
                        onClick = { onNavigateToTaxonomy(EntityLayer.DRUGS) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Interactive Canvas Preview Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onNavigateToMap("cstc_loop") }
                    .testTag("preview_map_card"),
                color = colors.elevatedCard,
                border = BorderStroke(1.dp, CircuitPrimary.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = null,
                                tint = CircuitPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "INTERACTIVE CONNECTOME MAP",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.5.sp),
                                color = CircuitPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Surface(
                            color = CircuitContainerDark,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "LIVE CANVAS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp
                                ),
                                color = CircuitPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Visual Brain Circuit & Region Navigator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textHigh
                    )

                    Text(
                        text = "Interact with 16 brain hubs (DLPFC, vmPFC, NAcc, VTA, Raphe, Amygdala, Thalamus). Zoom, pan, switch active pathway projections, and inspect cross-layer ties.",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                        lineHeight = 17.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { onNavigateToMap("cstc_loop") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CircuitBorder,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text("Launch Connectome Map", style = MaterialTheme.typography.labelMedium)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // High-Yield Clinical Pearls for Psychiatry Exams & Wards
        item {
            Text(
                text = "BOARD PEARLS & PSYCHOPHARMACOLOGY",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = colors.textSecondary,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(highYieldPearls) { pearl ->
                    Surface(
                        modifier = Modifier
                            .width(280.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                onEntitySelect(pearl.entityId, pearl.layer)
                            },
                        color = colors.elevatedCard,
                        border = BorderStroke(1.dp, colors.hairlineBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LayerBadge(layer = pearl.layer, text = pearl.tag)
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = colors.textSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = pearl.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = colors.textHigh
                            )
                            Text(
                                text = pearl.content,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            SafetyNoticeBanner()
        }
    }
}

@Composable
fun TaxonomyCard(
    layer: EntityLayer,
    count: Int,
    highlight: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalLayerColors.current
    val layerColor = getLayerColor(layer)
    val container = getLayerContainerColor(layer)

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .testTag("taxonomy_${layer.name.lowercase()}"),
        color = colors.elevatedCard,
        border = BorderStroke(1.dp, colors.hairlineBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = container,
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, layerColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "$count ENTRIES",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = layerColor
                        ),
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowOutward,
                    contentDescription = null,
                    tint = colors.textSecondary,
                    modifier = Modifier.size(14.dp)
                )
            }

            Text(
                text = layer.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textHigh
            )

            Text(
                text = highlight,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = colors.textSecondary,
                maxLines = 1
            )
        }
    }
}

data class ClinicalPearl(
    val entityId: String,
    val layer: EntityLayer,
    val tag: String,
    val title: String,
    val content: String
)

private val highYieldPearls = listOf(
    ClinicalPearl(
        entityId = "cstc_loop",
        layer = EntityLayer.CIRCUITS,
        tag = "CSTC Loop",
        title = "Worry Loop vs Motor Loop",
        content = "Dorsal cognitive loop (DLPFC-striatum) governs executive planning; orbitofrontal loop governs repetitive error checking in OCD."
    ),
    ClinicalPearl(
        entityId = "aripiprazole",
        layer = EntityLayer.DRUGS,
        tag = "D2 Partial Agonism",
        title = "Why Aripiprazole Has Low EPS",
        content = "D2 intrinsic activity of ~30% provides sufficient baseline basal ganglia transmission to prevent the >80% striatal shutdown causing parkinsonism."
    ),
    ClinicalPearl(
        entityId = "esketamine",
        layer = EntityLayer.DRUGS,
        tag = "Glutamate",
        title = "Rapid NMDA Antidepressant Action",
        content = "Blocks NMDA on GABA interneurons -> disinhibits pyramidal glutamate surge -> AMPA throughput -> rapid BDNF release and synaptogenesis within hours."
    ),
    ClinicalPearl(
        entityId = "default_mode_network",
        layer = EntityLayer.CIRCUITS,
        tag = "DMN in MDD",
        title = "Depression Rumination Hub",
        content = "PCC and vmPFC hyperconnectivity prevents task-positive switching, locking patients into pathological autobiographical rumination."
    ),
    ClinicalPearl(
        entityId = "clozapine",
        layer = EntityLayer.DRUGS,
        tag = "Clozapine Rule",
        title = "Suicide Reduction in Schizophrenia",
        content = "The only agent proven to reduce suicide in schizophrenia. Absolute Neutrophil Count (ANC) must be >=1500/mcL to initiate."
    )
)
