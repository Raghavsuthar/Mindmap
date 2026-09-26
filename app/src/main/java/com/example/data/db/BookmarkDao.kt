package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE entityId = :id")
    fun getBookmarkById(id: String): Flow<BookmarkEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE entityId = :id)")
    fun isBookmarked(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE entityId = :id")
    suspend fun deleteBookmark(id: String)

    @Query("UPDATE bookmarks SET notes = :notes WHERE entityId = :id")
    suspend fun updateNotes(id: String, notes: String)
}
