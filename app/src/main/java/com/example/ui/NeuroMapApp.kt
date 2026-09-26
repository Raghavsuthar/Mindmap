package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.EntityLayer
import com.example.ui.components.EntityDetailBottomSheet
import com.example.ui.screens.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeuroMapApp(
    viewModel: NeuroMapViewModel = viewModel()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val activeSheetEntity by viewModel.activeSheetEntity.collectAsStateWithLifecycle()

    NeuroMapTheme(darkTheme = isDarkTheme) {
        val colors = LocalLayerColors.current

        // Handle Back navigation across screens
        BackHandler(enabled = currentScreen !is ScreenNav.Home) {
            viewModel.navigateBack()
        }

        Scaffold(
            topBar = {
                // Only show main app bar on top-level tabs (Home, Map, Saved)
                if (currentScreen is ScreenNav.Home || currentScreen is ScreenNav.Map || currentScreen is ScreenNav.Saved) {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Clinical Brandmark Badge
                                Surface(
                                    color = Color(0xFF131D2E),
                                    border = BorderStroke(1.dp, CircuitPrimary),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(CircuitPrimary)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(DrugPrimary)
                                        )
                                    }
                                }

                                Text(
                                    text = "NeuroMap",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.textHigh
                                )

                                Surface(
                                    color = colors.elevatedCard,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "PSYCH-REF",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 9.sp,
                                            color = colors.textSecondary
                                        ),
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        },
                        actions = {
                            // Dark / Light Theme Toggle
                            IconButton(
                                onClick = { viewModel.toggleTheme() },
                                modifier = Modifier.testTag("theme_toggle_button")
                            ) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                                    contentDescription = "Toggle Theme",
                                    tint = if (isDarkTheme) SyndromePrimary else colors.textHigh
                                )
                            }

                            // Quick search trigger
                            IconButton(
                                onClick = { viewModel.navigateTo(ScreenNav.Search()) },
                                modifier = Modifier.testTag("top_search_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = colors.textHigh
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = colors.elevatedCard,
                            titleContentColor = colors.textHigh
                        )
                    )
                }
            },
            bottomBar = {
                // Bottom navigation bar
                NavigationBar(
                    containerColor = colors.elevatedCard,
                    contentColor = colors.textHigh,
                    tonalElevation = 4.dp
                ) {
                    NavigationBarItem(
                        selected = currentScreen is ScreenNav.Home,
                        onClick = { viewModel.navigateTo(ScreenNav.Home) },
                        icon = {
                            Icon(
                                if (currentScreen is ScreenNav.Home) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitPrimary,
                            selectedTextColor = CircuitPrimary,
                            indicatorColor = CircuitContainerDark
                        ),
                        modifier = Modifier.testTag("nav_home")
                    )

                    NavigationBarItem(
                        selected = currentScreen is ScreenNav.Map,
                        onClick = { viewModel.navigateTo(ScreenNav.Map()) },
                        icon = {
                            Icon(
                                if (currentScreen is ScreenNav.Map) Icons.Filled.Hub else Icons.Outlined.Hub,
                                contentDescription = "Connectome Map"
                            )
                        },
                        label = { Text("Main Map", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitPrimary,
                            selectedTextColor = CircuitPrimary,
                            indicatorColor = CircuitContainerDark
                        ),
                        modifier = Modifier.testTag("nav_map")
                    )

                    NavigationBarItem(
                        selected = currentScreen is ScreenNav.Search,
                        onClick = { viewModel.navigateTo(ScreenNav.Search()) },
                        icon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitPrimary,
                            selectedTextColor = CircuitPrimary,
                            indicatorColor = CircuitContainerDark
                        ),
                        modifier = Modifier.testTag("nav_search")
                    )

                    NavigationBarItem(
                        selected = currentScreen is ScreenNav.Saved,
                        onClick = { viewModel.navigateTo(ScreenNav.Saved) },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (bookmarks.isNotEmpty()) {
                                        Badge(
                                            containerColor = CircuitBorder,
                                            contentColor = Color.White
                                        ) {
                                            Text("${bookmarks.size}")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    if (currentScreen is ScreenNav.Saved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Saved"
                                )
                            }
                        },
                        label = { Text("Saved", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitPrimary,
                            selectedTextColor = CircuitPrimary,
                            indicatorColor = CircuitContainerDark
                        ),
                        modifier = Modifier.testTag("nav_saved")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (val screen = currentScreen) {
                    is ScreenNav.Home -> {
                        HomeScreen(
                            onNavigateToMap = { circuitId ->
                                viewModel.navigateTo(ScreenNav.Map(circuitId))
                            },
                            onNavigateToSearch = { query ->
                                viewModel.navigateTo(ScreenNav.Search(initialQuery = query))
                            },
                            onNavigateToTaxonomy = { layer ->
                                viewModel.navigateTo(ScreenNav.Taxonomy(layer))
                            },
                            onEntitySelect = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }

                    is ScreenNav.Map -> {
                        MapScreen(
                            initialCircuitId = screen.initialCircuitId,
                            onEntitySelect = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }

                    is ScreenNav.Search -> {
                        SearchScreen(
                            initialQuery = screen.initialQuery,
                            initialLayer = screen.initialLayer,
                            onEntitySelect = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }

                    is ScreenNav.Saved -> {
                        SavedScreen(
                            bookmarks = bookmarks,
                            onDeleteBookmark = { id -> viewModel.deleteBookmark(id) },
                            onUpdateNotes = { id, notes -> viewModel.updateBookmarkNotes(id, notes) },
                            onEntitySelect = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }

                    is ScreenNav.Taxonomy -> {
                        TaxonomyScreen(
                            layer = screen.layer,
                            onBack = { viewModel.navigateBack() },
                            onEntitySelect = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }

                    is ScreenNav.DrugDetail -> {
                        val isBookmarked = viewModel.isEntityBookmarked(screen.drugId)
                        DrugDetailScreen(
                            drugId = screen.drugId,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = {
                                viewModel.toggleBookmark(screen.drugId, EntityLayer.DRUGS)
                            },
                            onBack = { viewModel.navigateBack() },
                            onEntityClick = { id, layer ->
                                viewModel.openDetailSheet(id, layer)
                            }
                        )
                    }
                }
            }

            // Universal Cross-Layer Detail Bottom Sheet
            activeSheetEntity?.let { (entityId, layer) ->
                val isBookmarked = viewModel.isEntityBookmarked(entityId)
                EntityDetailBottomSheet(
                    entityId = entityId,
                    entityLayer = layer,
                    isBookmarked = isBookmarked,
                    onToggleBookmark = { viewModel.toggleBookmark(entityId, layer) },
                    onDismiss = { viewModel.closeDetailSheet() },
                    onEntityClick = { nextId, nextLayer ->
                        // Smoothly switch sheet to the newly clicked cross-layer entity!
                        viewModel.openDetailSheet(nextId, nextLayer)
                    },
                    onOpenFullDrugDetail = { drugId ->
                        viewModel.closeDetailSheet()
                        viewModel.navigateTo(ScreenNav.DrugDetail(drugId))
                    },
                    onHighlightOnMap = { circuitId ->
                        viewModel.closeDetailSheet()
                        viewModel.navigateTo(ScreenNav.Map(initialCircuitId = circuitId))
                    }
                )
            }
        }
    }
}
