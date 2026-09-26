package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BrainAtlas3D
import com.example.data.NeuroMapRepository
import com.example.data.model.BrainRegion
import com.example.data.model.Circuit
import com.example.data.model.EntityLayer
import com.example.ui.components.Brain3DView
import com.example.ui.components.LayerBadge
import com.example.ui.components.getLayerColor
import com.example.ui.theme.*
import kotlin.math.sqrt

@Composable
fun MapScreen(
    initialCircuitId: String? = null,
    onEntitySelect: (id: String, layer: EntityLayer) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalLayerColors.current
    var selectedLayerFilter by remember { mutableStateOf<EntityLayer?>(null) }
    var selectedCircuitId by remember { mutableStateOf<String?>(initialCircuitId ?: "cstc_loop") }
    var selectedRegion by remember { mutableStateOf<BrainRegion?>(null) }
    // Default to the native 2D connectome map for instant launch and rock-solid stability
    // across all devices and emulators; true 3D interactive brain is one tap away.
    var is3D by remember { mutableStateOf(false) }
    var xray by remember { mutableStateOf(false) }
    var showFallbackNotice by remember { mutableStateOf(false) }
    var fallbackError by remember { mutableStateOf<String?>(null) }

    // Zoom & pan transformations
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.7f, 3.5f)
        offset += offsetChange
    }

    // Pathway animation pulse
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulsePhase"
    )

    val regions = NeuroMapRepository.brainRegions
    val activeCircuit = remember(selectedCircuitId) {
        NeuroMapRepository.circuits.find { it.id == selectedCircuitId }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Controls: Layer Switcher & Circuit Carousel
        Surface(
            color = colors.elevatedCard,
            border = BorderStroke(1.dp, colors.hairlineBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                // Layer Filter Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedLayerFilter == null,
                        onClick = { selectedLayerFilter = null },
                        label = { Text("All Connectome", fontSize = 12.sp) },
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
                        val layerColor = getLayerColor(layer)
                        val isSelected = selectedLayerFilter == layer
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedLayerFilter = if (isSelected) null else layer
                            },
                            label = { Text(layer.title, fontSize = 12.sp) },
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

                Spacer(modifier = Modifier.height(6.dp))

                // Circuit Selector Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NeuroMapRepository.circuits.forEach { circuit ->
                        val isSelected = selectedCircuitId == circuit.id
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    selectedCircuitId = if (isSelected) null else circuit.id
                                },
                            color = if (isSelected) CircuitContainerDark else colors.elevatedCard.copy(alpha = 0.6f),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) CircuitPrimary else colors.hairlineBorder
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) CircuitPrimary else colors.textSecondary)
                                )
                                Text(
                                    text = circuit.name.replace("Pathway", "").replace("Circuit", "").trim(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) CircuitPrimary else colors.textHigh
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 3D / 2D + X-ray toggles
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = is3D,
                        onClick = { is3D = true },
                        label = { Text("3D Brain", fontSize = 12.sp) },
                        leadingIcon = { Text("🧠", fontSize = 12.sp) },
                        modifier = Modifier.testTag("toggle_3d")
                    )
                    FilterChip(
                        selected = !is3D,
                        onClick = { is3D = false },
                        label = { Text("2D Map", fontSize = 12.sp) },
                        modifier = Modifier.testTag("toggle_2d")
                    )
                    if (is3D) {
                        FilterChip(
                            selected = xray,
                            onClick = { xray = !xray },
                            label = { Text("X-ray see-through", fontSize = 12.sp) },
                            modifier = Modifier.testTag("toggle_xray")
                        )
                    }
                }

                if (showFallbackNotice && !is3D) {
                    Surface(
                        color = colors.elevatedCard,
                        border = BorderStroke(1.dp, colors.hairlineBorder),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Using 2D Connectome Map for maximum stability on this device.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.textSecondary,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { showFallbackNotice = false },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Dismiss",
                                        tint = colors.textSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            if (fallbackError != null) {
                                Text(
                                    text = "3D error: $fallbackError",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.5.sp
                                    ),
                                    color = AlertRed,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Button(
                                    onClick = {
                                        fallbackError = null
                                        showFallbackNotice = false
                                        is3D = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = CircuitBorder,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .testTag("retry_3d_button")
                                ) {
                                    Text("Retry 3D view", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (is3D) {
            // True 3D interactive brain: orbit / zoom / pan / one-tap nodes
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .testTag("interactive_brain_3d_container")
            ) {
                Brain3DView(
                    activeCircuitId = selectedCircuitId,
                    xray = xray,
                    onRegionTap = { regionId ->
                        selectedRegion = regions.find { it.id == regionId }
                    },
                    onStructureTap = { _, label, region, source, category ->
                        val coarseId = BrainAtlas3D.mapToCoarseRegion(label, region, source, category)
                        selectedRegion = coarseId?.let { id -> regions.find { it.id == id } }
                    },
                    on3DFailed = { step, message ->
                        is3D = false
                        fallbackError = "$step: $message"
                        showFallbackNotice = true
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Bottom active selection banner (shared with 2D)
                if (selectedRegion != null) {
                    val region = selectedRegion!!
                    Surface(
                        color = colors.elevatedCard,
                        border = BorderStroke(1.dp, CircuitPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${region.abbreviation} • ${region.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textHigh
                                )
                                Text(
                                    text = region.role,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.textSecondary,
                                    maxLines = 2
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Button(
                                        onClick = {
                                            val circuit = region.associatedCircuits.firstOrNull()
                                            if (circuit != null) onEntitySelect(circuit, EntityLayer.CIRCUITS)
                                        },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) { Text("Circuit", fontSize = 12.sp) }
                                    OutlinedButton(
                                        onClick = {
                                            // One-touch: jump to first linked transmitter / syndrome via detail sheet
                                            val circuit = region.associatedCircuits.firstOrNull()
                                            if (circuit != null) onEntitySelect(circuit, EntityLayer.CIRCUITS)
                                        },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) { Text("Receptors • Dx • Rx", fontSize = 12.sp) }
                                }
                            }
                            IconButton(
                                onClick = { selectedRegion = null },
                                modifier = Modifier.testTag("dismiss_region_button")
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = colors.textSecondary)
                            }
                        }
                    }
                }
            }
        }

        // Central Interactive Brain Canvas (2D fallback)
        if (!is3D) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RectangleShape)
                .background(colors.hairlineBorder.copy(alpha = 0.15f))
                .transformable(state = transformState)
                .testTag("interactive_brain_canvas")
        ) {
            // Custom Drawing Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { tapOffset ->
                            // Find closest brain node in viewport coordinates
                            val canvasW = size.width
                            val canvasH = size.height

                            var tappedRegion: BrainRegion? = null
                            var minDistance = Float.MAX_VALUE

                            regions.forEach { region ->
                                val nodeX = region.xPercent * canvasW * scale + offset.x
                                val nodeY = region.yPercent * canvasH * scale + offset.y
                                val dx = tapOffset.x - nodeX
                                val dy = tapOffset.y - nodeY
                                val dist = sqrt(dx * dx + dy * dy)
                                if (dist < 45.dp.toPx() && dist < minDistance) {
                                    minDistance = dist
                                    tappedRegion = region
                                }
                            }

                            selectedRegion = tappedRegion
                        }
                    }
            ) {
                val canvasW = size.width
                val canvasH = size.height

                // Draw anatomical brain outline silhouette in sagittal view
                drawBrainSilhouette(canvasW, canvasH, scale, offset, colors.hairlineBorder)

                // Draw Circuit Pathway Connections
                if (activeCircuit != null) {
                    val circuitNodes = regions.filter { activeCircuit.keyNodes.contains(it.id) }
                    drawCircuitPathways(
                        nodes = circuitNodes,
                        canvasW = canvasW,
                        canvasH = canvasH,
                        scale = scale,
                        offset = offset,
                        pulsePhase = pulsePhase,
                        lineColor = CircuitPrimary
                    )
                } else {
                    // Default structural connections
                    drawDefaultConnectome(regions, canvasW, canvasH, scale, offset, colors.hairlineBorder)
                }

                // Draw Region Nodes
                regions.forEach { region ->
                    val isNodeInActiveCircuit = activeCircuit?.keyNodes?.contains(region.id) == true
                    val isSelected = selectedRegion?.id == region.id

                    val nodeColor = when {
                        isSelected -> AlertRed
                        isNodeInActiveCircuit -> CircuitPrimary
                        selectedLayerFilter == EntityLayer.CIRCUITS -> CircuitPrimary
                        selectedLayerFilter == EntityLayer.TRANSMITTERS -> TransmitterPrimary
                        selectedLayerFilter == EntityLayer.SYNDROMES -> SyndromePrimary
                        selectedLayerFilter == EntityLayer.DRUGS -> DrugPrimary
                        else -> CircuitPrimary.copy(alpha = 0.8f)
                    }

                    val posX = region.xPercent * canvasW * scale + offset.x
                    val posY = region.yPercent * canvasH * scale + offset.y

                    // Outer halo if selected or active in circuit
                    if (isNodeInActiveCircuit || isSelected) {
                        drawCircle(
                            color = nodeColor.copy(alpha = 0.25f),
                            radius = (14.dp.toPx() * (1f + (pulsePhase * 0.3f))).coerceAtLeast(10f),
                            center = Offset(posX, posY)
                        )
                    }

                    // Node center circle
                    drawCircle(
                        color = colors.elevatedCard,
                        radius = 8.dp.toPx(),
                        center = Offset(posX, posY)
                    )
                    drawCircle(
                        color = nodeColor,
                        radius = 6.dp.toPx(),
                        center = Offset(posX, posY)
                    )

                    // Draw region abbreviation label
                    // Node dot drawing complete
                }
            }

            // Node Labels Overlay in Composable layer for crisp typography
            regions.forEach { region ->
                val isNodeInActiveCircuit = activeCircuit?.keyNodes?.contains(region.id) == true
                val isSelected = selectedRegion?.id == region.id

                val nodeColor = when {
                    isSelected -> AlertRed
                    isNodeInActiveCircuit -> CircuitPrimary
                    else -> colors.textHigh
                }

                Box(
                    modifier = Modifier
                        .offset(
                            x = ((region.xPercent * 340f) * scale + (offset.x / 3.5f)).dp,
                            y = ((region.yPercent * 400f) * scale + (offset.y / 3.5f) + 8f).dp
                        )
                        .clip(RoundedCornerShape(3.dp))
                        .background(colors.elevatedCard.copy(alpha = 0.85f))
                        .border(
                            0.5.dp,
                            if (isNodeInActiveCircuit) CircuitPrimary else colors.hairlineBorder,
                            RoundedCornerShape(3.dp)
                        )
                        .clickable { selectedRegion = region }
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = region.abbreviation,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = nodeColor
                        )
                    )
                }
            }

            // Controls on canvas (Zoom In, Zoom Out, Reset, Legend)
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FloatingActionButton(
                    onClick = { scale = (scale * 1.25f).coerceAtMost(3.5f) },
                    modifier = Modifier.size(36.dp),
                    containerColor = colors.elevatedCard,
                    contentColor = colors.textHigh,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Zoom In", modifier = Modifier.size(16.dp))
                }

                FloatingActionButton(
                    onClick = { scale = (scale / 1.25f).coerceAtLeast(0.7f) },
                    modifier = Modifier.size(36.dp),
                    containerColor = colors.elevatedCard,
                    contentColor = colors.textHigh,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Zoom Out", modifier = Modifier.size(16.dp))
                }

                FloatingActionButton(
                    onClick = {
                        scale = 1f
                        offset = Offset.Zero
                    },
                    modifier = Modifier.size(36.dp),
                    containerColor = colors.elevatedCard,
                    contentColor = CircuitPrimary,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset View", modifier = Modifier.size(16.dp))
                }
            }

            // Bottom active selection banner / quick region drawer trigger
            if (selectedRegion != null) {
                val region = selectedRegion!!
                Surface(
                    color = colors.elevatedCard,
                    border = BorderStroke(1.dp, CircuitPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clickable {
                            val circuit = region.associatedCircuits.firstOrNull()
                            if (circuit != null) {
                                onEntitySelect(circuit, EntityLayer.CIRCUITS)
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    color = CircuitContainerDark,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = region.abbreviation,
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                        color = CircuitPrimary,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = region.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textHigh
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = region.role,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textSecondary,
                                maxLines = 2
                            )
                        }

                        IconButton(
                            onClick = {
                                val circuitId = region.associatedCircuits.firstOrNull()
                                if (circuitId != null) {
                                    onEntitySelect(circuitId, EntityLayer.CIRCUITS)
                                }
                            },
                            modifier = Modifier.testTag("inspect_region_button")
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Inspect",
                                tint = CircuitPrimary
                            )
                        }
                    }
                }
            }
        }
        } // end if (!is3D)

        // Bottom Map Footer: Active Circuit info + Legend
        activeCircuit?.let { circuit ->
            Surface(
                color = colors.elevatedCard,
                border = BorderStroke(1.dp, colors.hairlineBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(CircuitPrimary)
                            )
                            Text(
                                text = circuit.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.textHigh
                            )
                        }
                        Text(
                            text = circuit.function,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary,
                            maxLines = 1
                        )
                    }

                    Button(
                        onClick = { onEntitySelect(circuit.id, EntityLayer.CIRCUITS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CircuitBorder,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("view_circuit_detail_button")
                    ) {
                        Text("View Circuit", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

// Canvas helper: Draw realistic anatomical sagittal brain silhouette
private fun DrawScope.drawBrainSilhouette(
    width: Float,
    height: Float,
    scale: Float,
    offset: Offset,
    borderColor: Color
) {
    val path = Path().apply {
        // Sagittal medial contour: Prefrontal pole -> Vertex -> Occipital -> Cerebellum -> Brainstem
        val x0 = 0.12f * width * scale + offset.x
        val y0 = 0.50f * height * scale + offset.y

        moveTo(x0, y0)
        // Orbital frontal floor
        cubicTo(
            0.15f * width * scale + offset.x, 0.60f * height * scale + offset.y,
            0.24f * width * scale + offset.x, 0.62f * height * scale + offset.y,
            0.32f * width * scale + offset.x, 0.60f * height * scale + offset.y
        )
        // Temporal pole
        cubicTo(
            0.36f * width * scale + offset.x, 0.70f * height * scale + offset.y,
            0.50f * width * scale + offset.x, 0.72f * height * scale + offset.y,
            0.62f * width * scale + offset.x, 0.68f * height * scale + offset.y
        )
        // Midbrain & Pons anterior boundary
        lineTo(0.55f * width * scale + offset.x, 0.85f * height * scale + offset.y)
        // Medulla down
        lineTo(0.58f * width * scale + offset.x, 0.92f * height * scale + offset.y)
        lineTo(0.66f * width * scale + offset.x, 0.90f * height * scale + offset.y)
        // Cerebellar tonsils & folia
        cubicTo(
            0.78f * width * scale + offset.x, 0.88f * height * scale + offset.y,
            0.88f * width * scale + offset.x, 0.78f * height * scale + offset.y,
            0.82f * width * scale + offset.x, 0.64f * height * scale + offset.y
        )
        // Occipital pole
        cubicTo(
            0.92f * width * scale + offset.x, 0.54f * height * scale + offset.y,
            0.90f * width * scale + offset.x, 0.38f * height * scale + offset.y,
            0.82f * width * scale + offset.x, 0.28f * height * scale + offset.y
        )
        // Parietal vertex
        cubicTo(
            0.72f * width * scale + offset.x, 0.16f * height * scale + offset.y,
            0.50f * width * scale + offset.x, 0.14f * height * scale + offset.y,
            0.35f * width * scale + offset.x, 0.18f * height * scale + offset.y
        )
        // Frontal curve down to pole
        cubicTo(
            0.20f * width * scale + offset.x, 0.24f * height * scale + offset.y,
            0.12f * width * scale + offset.x, 0.36f * height * scale + offset.y,
            x0, y0
        )
        close()
    }

    // Fill silhouette
    drawPath(
        path = path,
        color = Color(0xFF131D2E).copy(alpha = 0.65f)
    )
    // Stroke outline
    drawPath(
        path = path,
        color = borderColor,
        style = Stroke(width = 1.5f * scale)
    )

    // Draw Corpus Callosum C-shaped arch
    val ccPath = Path().apply {
        moveTo(0.30f * width * scale + offset.x, 0.44f * height * scale + offset.y)
        cubicTo(
            0.35f * width * scale + offset.x, 0.34f * height * scale + offset.y,
            0.65f * width * scale + offset.x, 0.34f * height * scale + offset.y,
            0.70f * width * scale + offset.x, 0.44f * height * scale + offset.y
        )
    }
    drawPath(
        path = ccPath,
        color = borderColor.copy(alpha = 0.6f),
        style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round)
    )
}

// Canvas helper: Draw glowing active circuit loops
private fun DrawScope.drawCircuitPathways(
    nodes: List<BrainRegion>,
    canvasW: Float,
    canvasH: Float,
    scale: Float,
    offset: Offset,
    pulsePhase: Float,
    lineColor: Color
) {
    if (nodes.size < 2) return

    for (i in 0 until nodes.size) {
        val startNode = nodes[i]
        val endNode = nodes[(i + 1) % nodes.size]

        val startX = startNode.xPercent * canvasW * scale + offset.x
        val startY = startNode.yPercent * canvasH * scale + offset.y
        val endX = endNode.xPercent * canvasW * scale + offset.x
        val endY = endNode.yPercent * canvasH * scale + offset.y

        // Pathway line
        drawLine(
            color = lineColor.copy(alpha = 0.7f),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 2.5f * scale,
            cap = StrokeCap.Round
        )

        // Animated pulse packet travelling along the axon pathway
        val packetX = startX + (endX - startX) * pulsePhase
        val packetY = startY + (endY - startY) * pulsePhase

        drawCircle(
            color = Color.White,
            radius = 3.5f * scale,
            center = Offset(packetX, packetY)
        )
        drawCircle(
            color = lineColor,
            radius = 6.5f * scale,
            center = Offset(packetX, packetY)
        )
    }
}

// Canvas helper: Draw subtle default interconnects between anatomical hubs
private fun DrawScope.drawDefaultConnectome(
    regions: List<BrainRegion>,
    canvasW: Float,
    canvasH: Float,
    scale: Float,
    offset: Offset,
    lineColor: Color
) {
    val connections = listOf(
        Pair("dlpfc", "caudate"),
        Pair("caudate", "thalamus"),
        Pair("thalamus", "dlpfc"),
        Pair("vta", "nacc"),
        Pair("nacc", "vmpfc"),
        Pair("vmpfc", "amygdala"),
        Pair("amygdala", "hippocampus"),
        Pair("dacc", "insula"),
        Pair("pcc", "vmpfc"),
        Pair("locus_coeruleus", "thalamus"),
        Pair("raphe_nuclei", "amygdala")
    )

    connections.forEach { (srcId, destId) ->
        val src = regions.find { it.id == srcId }
        val dest = regions.find { it.id == destId }
        if (src != null && dest != null) {
            val startX = src.xPercent * canvasW * scale + offset.x
            val startY = src.yPercent * canvasH * scale + offset.y
            val endX = dest.xPercent * canvasW * scale + offset.x
            val endY = dest.yPercent * canvasH * scale + offset.y

            drawLine(
                color = lineColor.copy(alpha = 0.4f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 1f * scale,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
            )
        }
    }
}
