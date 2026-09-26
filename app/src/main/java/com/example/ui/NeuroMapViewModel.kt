package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BookmarkRepository
import com.example.data.NeuroMapRepository
import com.example.data.db.AppDatabase
import com.example.data.db.BookmarkEntity
import com.example.data.model.EntityLayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenNav {
    object Home : ScreenNav()
    data class Map(val initialCircuitId: String? = null) : ScreenNav()
    data class Search(val initialQuery: String = "", val initialLayer: EntityLayer? = null) : ScreenNav()
    object Saved : ScreenNav()
    data class Taxonomy(val layer: EntityLayer) : ScreenNav()
    data class DrugDetail(val drugId: String) : ScreenNav()
}

class NeuroMapViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkRepo = BookmarkRepository(
        AppDatabase.getDatabase(application).bookmarkDao()
    )

    val bookmarks: StateFlow<List<BookmarkEntity>> = bookmarkRepo.allBookmarks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _currentScreen = MutableStateFlow<ScreenNav>(ScreenNav.Home)
    val currentScreen: StateFlow<ScreenNav> = _currentScreen.asStateFlow()

    private val navStack = mutableListOf<ScreenNav>(ScreenNav.Home)

    // Active bottom sheet entity
    private val _activeSheetEntity = MutableStateFlow<Pair<String, EntityLayer>?>(null)
    val activeSheetEntity: StateFlow<Pair<String, EntityLayer>?> = _activeSheetEntity.asStateFlow()

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun navigateTo(screen: ScreenNav) {
        if (_currentScreen.value != screen) {
            navStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (navStack.size > 1) {
            navStack.removeAt(navStack.size - 1)
            val previous = navStack.last()
            _currentScreen.value = previous
            return true
        }
        return false
    }

    fun openDetailSheet(entityId: String, layer: EntityLayer) {
        _activeSheetEntity.value = Pair(entityId, layer)
    }

    fun closeDetailSheet() {
        _activeSheetEntity.value = null
    }

    fun toggleBookmark(entityId: String, layer: EntityLayer) {
        val currentlySaved = bookmarks.value.any { it.entityId == entityId }
        val title = NeuroMapRepository.getEntityTitle(entityId)
        val subtitle = NeuroMapRepository.getEntitySubtitle(entityId)
        val codeBadge = when (layer) {
            EntityLayer.CIRCUITS -> NeuroMapRepository.getCircuitById(entityId)?.tierCode ?: ""
            EntityLayer.TRANSMITTERS -> NeuroMapRepository.getTransmitterById(entityId)?.symbol ?: ""
            EntityLayer.SYNDROMES -> NeuroMapRepository.getSyndromeById(entityId)?.icd11Code ?: ""
            EntityLayer.DRUGS -> NeuroMapRepository.getDrugById(entityId)?.atcCode ?: ""
        }

        viewModelScope.launch {
            bookmarkRepo.toggleBookmark(
                entityId = entityId,
                layerType = layer.name,
                title = title,
                subtitle = subtitle,
                codeBadge = codeBadge,
                currentlySaved = currentlySaved
            )
        }
    }

    fun deleteBookmark(entityId: String) {
        viewModelScope.launch {
            bookmarkRepo.deleteBookmark(entityId)
        }
    }

    fun updateBookmarkNotes(entityId: String, notes: String) {
        viewModelScope.launch {
            bookmarkRepo.updateNotes(entityId, notes)
        }
    }

    fun isEntityBookmarked(entityId: String): Boolean {
        return bookmarks.value.any { it.entityId == entityId }
    }
}
