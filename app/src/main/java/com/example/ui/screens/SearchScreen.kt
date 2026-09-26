package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NeuroMapRepository
import com.example.data.model.EntityLayer
import com.example.data.model.SearchResult
import com.example.ui.components.LayerBadge
import com.example.ui.components.getLayerColor
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    initialQuery: String = "",
    initialLayer: EntityLayer? = null,
    onEntitySelect: (id: String, layer: EntityLayer) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(initialQuery) }
    var selectedLayer by remember { mutableStateOf<EntityLayer?>(initialLayer) }
    val colors = LocalLayerColors.current

    val searchResults by remember(query, selectedLayer) {
        derivedStateOf {
            NeuroMapRepository.searchAll(query, selectedLayer)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search Input Header
        Surface(
            color = colors.elevatedCard,
            border = BorderStroke(1.dp, colors.hairlineBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_text_field"),
                    placeholder = {
                        Text(
                            "Search entities, Ki nM, ICD-11, DSM-5, CYP, structures...",
                            fontSize = 13.sp,
                            color = colors.textSecondary.copy(alpha = 0.7f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = colors.textSecondary
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = colors.textSecondary
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.elevatedCard,
                        unfocusedContainerColor = colors.elevatedCard,
                        focusedBorderColor = CircuitPrimary,
                        unfocusedBorderColor = colors.hairlineBorder
                    )
                )

                // Layer Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedLayer == null,
                        onClick = { selectedLayer = null },
                        label = { Text("All (${searchResults.size})", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CircuitPrimary.copy(alpha = 0.2f),
                            selectedLabelColor = CircuitPrimary
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selectedLayer == null) CircuitPrimary else colors.hairlineBorder
                        )
                    )

                    EntityLayer.values().forEach { layer ->
                        val isSelected = selectedLayer == layer
                        val layerColor = getLayerColor(layer)
                        val layerCount = remember(query) {
                            NeuroMapRepository.searchAll(query, layer).size
                        }

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedLayer = if (isSelected) null else layer
                            },
                            label = { Text("${layer.title} ($layerCount)", fontSize = 12.sp) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(layerColor)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = layerColor.copy(alpha = 0.2f),
                                selectedLabelColor = layerColor
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) layerColor else colors.hairlineBorder
                            )
                        )
                    }
                }
            }
        }

        // Search Results List
        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = colors.textSecondary.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "No matching clinical entities found",
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.textHigh
                    )
                    Text(
                        text = "Try searching for a receptor (e.g. '5-HT2A'), a drug ('Sertraline'), a syndrome ('OCD'), or a circuit ('CSTC').",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchResults, key = { it.id }) { result ->
                    SearchResultCard(
                        result = result,
                        onClick = { onEntitySelect(result.id, result.layer) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(
    result: SearchResult,
    onClick: () -> Unit
) {
    val colors = LocalLayerColors.current
    val layerColor = getLayerColor(result.layer)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .testTag("search_result_${result.id}"),
        color = colors.elevatedCard,
        border = BorderStroke(1.dp, colors.hairlineBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LayerBadge(layer = result.layer)

                Surface(
                    color = colors.elevatedCard.copy(alpha = 0.7f),
                    border = BorderStroke(1.dp, layerColor.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = result.codeBadge,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = layerColor
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = result.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textHigh
            )

            if (result.subtitle.isNotEmpty()) {
                Text(
                    text = result.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }

            Text(
                text = result.summary,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                color = colors.textSecondary.copy(alpha = 0.9f),
                lineHeight = 16.sp
            )
        }
    }
}
