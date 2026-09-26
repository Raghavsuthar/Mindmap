package com.example.ui.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.BookmarkEntity
import com.example.data.model.EntityLayer
import com.example.ui.components.LayerBadge
import com.example.ui.components.getLayerColor
import com.example.ui.theme.*

@Composable
fun SavedScreen(
    bookmarks: List<BookmarkEntity>,
    onDeleteBookmark: (entityId: String) -> Unit,
    onUpdateNotes: (entityId: String, notes: String) -> Unit,
    onEntitySelect: (id: String, layer: EntityLayer) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = LocalLayerColors.current
    var selectedLayerFilter by remember { mutableStateOf<EntityLayer?>(null) }
    var editingEntityId by remember { mutableStateOf<String?>(null) }
    var editingNotesText by remember { mutableStateOf("") }

    val filteredBookmarks = remember(bookmarks, selectedLayerFilter) {
        if (selectedLayerFilter == null) {
            bookmarks
        } else {
            bookmarks.filter { it.layerType == selectedLayerFilter!!.name }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Action Bar
        Surface(
            color = colors.elevatedCard,
            border = BorderStroke(1.dp, colors.hairlineBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Clinical Bookmarks",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.textHigh
                        )
                        Text(
                            text = "${bookmarks.size} saved entities with personal notes",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }

                    if (bookmarks.isNotEmpty()) {
                        Button(
                            onClick = { exportBookmarksSummary(context, bookmarks) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CircuitBorder,
                                contentColor = androidx.compose.ui.graphics.Color.White
                            ),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("export_bookmarks_button")
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Export / Share", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                // Filter chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedLayerFilter == null,
                        onClick = { selectedLayerFilter = null },
                        label = { Text("All (${bookmarks.size})", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CircuitPrimary.copy(alpha = 0.2f),
                            selectedLabelColor = CircuitPrimary
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selectedLayerFilter == null) CircuitPrimary else colors.hairlineBorder
                        )
                    )

                    EntityLayer.values().forEach { layer ->
                        val count = bookmarks.count { it.layerType == layer.name }
                        val layerColor = getLayerColor(layer)
                        val isSelected = selectedLayerFilter == layer

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedLayerFilter = if (isSelected) null else layer
                            },
                            label = { Text("${layer.title} ($count)", fontSize = 12.sp) },
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

        // Bookmark list or empty state
        if (filteredBookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        tint = colors.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No saved clinical bookmarks",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textHigh
                    )
                    Text(
                        text = "Tap the bookmark icon on any circuit, syndrome, neurotransmitter, or psychotropic drug to save it for rapid clinical reference and add personal study notes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredBookmarks, key = { it.entityId }) { bookmark ->
                    val layer = try {
                        EntityLayer.valueOf(bookmark.layerType)
                    } catch (e: Exception) {
                        EntityLayer.CIRCUITS
                    }

                    SavedItemCard(
                        bookmark = bookmark,
                        layer = layer,
                        isEditingNotes = editingEntityId == bookmark.entityId,
                        notesText = if (editingEntityId == bookmark.entityId) editingNotesText else bookmark.notes,
                        onStartEditNotes = {
                            editingEntityId = bookmark.entityId
                            editingNotesText = bookmark.notes
                        },
                        onNotesChange = { editingNotesText = it },
                        onSaveNotes = {
                            onUpdateNotes(bookmark.entityId, editingNotesText)
                            editingEntityId = null
                        },
                        onCancelEditNotes = { editingEntityId = null },
                        onDelete = { onDeleteBookmark(bookmark.entityId) },
                        onClick = { onEntitySelect(bookmark.entityId, layer) }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedItemCard(
    bookmark: BookmarkEntity,
    layer: EntityLayer,
    isEditingNotes: Boolean,
    notesText: String,
    onStartEditNotes: () -> Unit,
    onNotesChange: (String) -> Unit,
    onSaveNotes: () -> Unit,
    onCancelEditNotes: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val colors = LocalLayerColors.current
    val layerColor = getLayerColor(layer)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .testTag("saved_item_${bookmark.entityId}"),
        color = colors.elevatedCard,
        border = BorderStroke(1.dp, colors.hairlineBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    LayerBadge(layer = layer)
                    if (bookmark.codeBadge.isNotEmpty()) {
                        Surface(
                            color = colors.elevatedCard,
                            border = BorderStroke(1.dp, layerColor.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = bookmark.codeBadge,
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    color = layerColor
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onStartEditNotes,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Edit Study Note",
                            tint = colors.textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Remove bookmark",
                            tint = colors.textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = bookmark.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textHigh
            )

            if (bookmark.subtitle.isNotEmpty()) {
                Text(
                    text = bookmark.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }

            // Clinical Study Note
            if (isEditingNotes) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = onNotesChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Add personal clinical notes, ward pearls, or exam mnemonics...", fontSize = 12.sp) },
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CircuitPrimary,
                            unfocusedBorderColor = colors.hairlineBorder
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onCancelEditNotes) {
                            Text("Cancel", style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = onSaveNotes,
                            colors = ButtonDefaults.buttonColors(containerColor = CircuitBorder)
                        ) {
                            Text("Save Note", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            } else if (bookmark.notes.isNotEmpty()) {
                Surface(
                    color = colors.elevatedCard.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, colors.hairlineBorder.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notes,
                            contentDescription = null,
                            tint = CircuitPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = bookmark.notes,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                            color = colors.textHigh,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

private fun exportBookmarksSummary(context: Context, bookmarks: List<BookmarkEntity>) {
    val builder = StringBuilder()
    builder.appendLine("==========================================")
    builder.appendLine("NEUROMAP CLINICAL REFERENCE EXPORT")
    builder.appendLine("Psychiatric Neurocircuitry & Psychopharmacology")
    builder.appendLine("==========================================\n")

    val grouped = bookmarks.groupBy { it.layerType }
    grouped.forEach { (layerType, items) ->
        builder.appendLine("## $layerType (${items.size})")
        items.forEach { item ->
            builder.appendLine("• ${item.title} [${item.codeBadge}]")
            if (item.subtitle.isNotEmpty()) {
                builder.appendLine("  Classification: ${item.subtitle}")
            }
            if (item.notes.isNotEmpty()) {
                builder.appendLine("  Clinical Notes: ${item.notes}")
            }
        }
        builder.appendLine()
    }

    builder.appendLine("==========================================")
    builder.appendLine("Generated via NeuroMap Clinical Tool")
    builder.appendLine("Reference only — not primary diagnostic advice")

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, builder.toString())
        putExtra(Intent.EXTRA_SUBJECT, "NeuroMap Clinical Study Export")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Export / Share NeuroMap Clinical Bookmarks")
    context.startActivity(shareIntent)
}
