package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import com.example.data.model.ReceptorTarget
import com.example.ui.components.CrossLayerConnectionsSection
import com.example.ui.components.LayerBadge
import com.example.ui.components.SafetyNoticeBanner
import com.example.ui.components.SourcesSection
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugDetailScreen(
    drugId: String,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onBack: () -> Unit,
    onEntityClick: (id: String, layer: EntityLayer) -> Unit
) {
    val drug = NeuroMapRepository.getDrugById(drugId)
    val colors = LocalLayerColors.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = drug?.genericName ?: "Drug Detail",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = drug?.drugClass ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.textHigh
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.testTag("drug_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Save drug",
                            tint = if (isBookmarked) DrugPrimary else colors.textSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.elevatedCard,
                    titleContentColor = colors.textHigh
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (drug == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Psychotropic entity not found.", color = colors.textSecondary)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Card
            item {
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LayerBadge(layer = EntityLayer.DRUGS, text = drug.drugClass)
                            Surface(
                                color = colors.drugContainer,
                                border = BorderStroke(1.dp, colors.drugBorder),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "ATC: ${drug.atcCode}",
                                    style = ClinicalMonoSmall,
                                    color = colors.drug,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = drug.genericName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.textHigh
                        )

                        Text(
                            text = "Commercial Brands: ${drug.brandName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.textSecondary
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = colors.drugContainer.copy(alpha = 0.35f),
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, colors.drugBorder.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = colors.drug,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = drug.clinicalPearls,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.textHigh,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            // Evidence-based clinical summary
            item {
                if (drug.summary.isNotEmpty()) {
                    Surface(
                        color = colors.drugContainer.copy(alpha = 0.35f),
                        border = BorderStroke(1.dp, colors.hairlineBorder),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "CLINICAL SUMMARY (EVIDENCE-BASED)",
                                style = MaterialTheme.typography.labelSmall,
                                color = DrugPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = drug.summary,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.textHigh,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // Black-Box Warnings (if any)
            if (drug.blackBoxWarnings.isNotEmpty()) {
                item {
                    Surface(
                        color = AlertRedContainer.copy(alpha = 0.85f),
                        border = BorderStroke(1.dp, AlertRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Boxed Warning",
                                    tint = AlertRed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "FDA BOXED WARNING / CRITICAL SAFETY ALERT",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AlertRed,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            drug.blackBoxWarnings.forEach { warning ->
                                Text(
                                    text = "• $warning",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }
            }

            // Receptor Binding Profile Table (Ki Values)
            item {
                SectionHeader(
                    icon = Icons.Outlined.DeviceHub,
                    title = "RECEPTOR BINDING PROFILE & AFFINITY (Ki VALUES)"
                )
            }

            item {
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Table Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TARGET & ACTION",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.textSecondary,
                                modifier = Modifier.weight(1.8f)
                            )
                            Text(
                                text = "AFFINITY",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.textSecondary,
                                modifier = Modifier.weight(1.0f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Ki (nM)",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.textSecondary,
                                modifier = Modifier.weight(1.0f),
                                textAlign = TextAlign.End
                            )
                        }

                        Divider(color = colors.hairlineBorder, modifier = Modifier.padding(bottom = 6.dp))

                        drug.receptorTargets.forEach { target ->
                            ReceptorTargetRow(target = target)
                            Divider(
                                color = colors.hairlineBorder.copy(alpha = 0.5f),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Side-Effect Mechanisms
            item {
                SectionHeader(
                    icon = Icons.Outlined.HealthAndSafety,
                    title = "RECEPTOR-MEDIATED ADVERSE EFFECT MECHANISMS"
                )
            }

            items(drug.sideEffectMechanisms) { mechanism ->
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = mechanism.symptom,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.textHigh
                            )
                            Surface(
                                color = colors.drugContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = mechanism.receptorMediation,
                                    style = ClinicalMonoSmall,
                                    color = colors.drug,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Clinical Strategy: ${mechanism.clinicalManagement}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            // Dosing & Titration Card
            item {
                SectionHeader(icon = Icons.Outlined.Scale, title = "DOSING, TITRATION & TARGET RANGES")
            }

            item {
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DoseMetricBox(label = "STARTING", value = drug.dosingRange.startingDose, modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(8.dp))
                            DoseMetricBox(label = "TARGET", value = drug.dosingRange.targetDose, highlight = true, modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(8.dp))
                            DoseMetricBox(label = "MAXIMUM", value = drug.dosingRange.maxDose, modifier = Modifier.weight(1f))
                        }

                        Divider(color = colors.hairlineBorder)

                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(
                                text = "TITRATION PROTOCOL",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.textSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = drug.dosingRange.titrationSchedule,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textHigh,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Pharmacokinetics Card
            item {
                SectionHeader(icon = Icons.Outlined.Science, title = "CLINICAL PHARMACOKINETICS")
            }

            item {
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PkRow(label = "Elimination Half-Life", value = drug.pharmacokinetics.halfLife)
                        PkRow(label = "Oral Bioavailability", value = drug.pharmacokinetics.bioavailability)
                        PkRow(label = "CYP450 Metabolism", value = drug.pharmacokinetics.cypMetabolism)
                        PkRow(label = "Time to Peak (Tmax)", value = drug.pharmacokinetics.timeToPeak)
                    }
                }
            }

            // Cross-Layer Connections Section
            item {
                SectionHeader(icon = Icons.Outlined.Share, title = "CROSS-LAYER PATHWAY INTEGRATION")
            }

            item {
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, colors.hairlineBorder),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        CrossLayerConnectionsSection(
                            linkedCircuits = drug.linkedCircuits,
                            linkedTransmitters = drug.linkedTransmitters,
                            linkedSyndromes = drug.linkedSyndromes,
                            onEntityClick = onEntityClick
                        )
                    }
                }
            }

            item {
                SourcesSection(sources = drug.sources)
            }

            item {
                SafetyNoticeBanner()
            }
        }
    }
}

@Composable
fun SectionHeader(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    val colors = LocalLayerColors.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.textSecondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.8.sp),
            fontWeight = FontWeight.Bold,
            color = colors.textSecondary
        )
    }
}

@Composable
fun ReceptorTargetRow(target: ReceptorTarget) {
    val colors = LocalLayerColors.current
    val kiText = if (target.kiNm != null) "${target.kiNm}" else "N/A"

    val affinityColor = when (target.affinityRating) {
        "Very High" -> DrugPrimary
        "High" -> CircuitPrimary
        "Moderate" -> TransmitterPrimary
        else -> colors.textSecondary
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.8f)) {
                Text(
                    text = target.target,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textHigh
                )
                Text(
                    text = target.action,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = colors.textSecondary
                )
            }

            Surface(
                color = affinityColor.copy(alpha = 0.18f),
                border = BorderStroke(1.dp, affinityColor.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier.weight(1.0f)
            ) {
                Text(
                    text = target.affinityRating,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = affinityColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }

            Text(
                text = kiText,
                style = ClinicalMonoMedium,
                fontWeight = FontWeight.Bold,
                color = if (target.kiNm != null && target.kiNm < 10.0) DrugPrimary else colors.textHigh,
                modifier = Modifier.weight(1.0f),
                textAlign = TextAlign.End
            )
        }

        Text(
            text = target.clinicalRelevance,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = colors.textSecondary.copy(alpha = 0.9f),
            lineHeight = 15.sp
        )
    }
}

@Composable
fun DoseMetricBox(label: String, value: String, highlight: Boolean = false, modifier: Modifier = Modifier) {
    val colors = LocalLayerColors.current
    Surface(
        color = if (highlight) colors.drugContainer else colors.elevatedCard.copy(alpha = 0.7f),
        border = BorderStroke(1.dp, if (highlight) colors.drugBorder else colors.hairlineBorder),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = if (highlight) colors.drug else colors.textSecondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = value,
                style = ClinicalMonoSmall,
                fontWeight = FontWeight.Bold,
                color = colors.textHigh,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PkRow(label: String, value: String) {
    val colors = LocalLayerColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = ClinicalMonoSmall,
            color = colors.textHigh,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.4f)
        )
    }
}
