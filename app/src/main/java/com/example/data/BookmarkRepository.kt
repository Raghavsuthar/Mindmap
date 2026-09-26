package com.example.data

import com.example.data.db.BookmarkDao
import com.example.data.db.BookmarkEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()

    fun isBookmarked(id: String): Flow<Boolean> = bookmarkDao.isBookmarked(id)

    fun getBookmark(id: String): Flow<BookmarkEntity?> = bookmarkDao.getBookmarkById(id)

    suspend fun toggleBookmark(
        entityId: String,
        layerType: String,
        title: String,
        subtitle: String,
        codeBadge: String,
        currentlySaved: Boolean
    ) {
        if (currentlySaved) {
            bookmarkDao.deleteBookmark(entityId)
        } else {
            bookmarkDao.saveBookmark(
                BookmarkEntity(
                    entityId = entityId,
                    layerType = layerType,
                    title = title,
                    subtitle = subtitle,
                    codeBadge = codeBadge
                )
            )
        }
    }

    suspend fun updateNotes(entityId: String, notes: String) {
        bookmarkDao.updateNotes(entityId, notes)
    }

    suspend fun deleteBookmark(entityId: String) {
        bookmarkDao.deleteBookmark(entityId)
    }
}
