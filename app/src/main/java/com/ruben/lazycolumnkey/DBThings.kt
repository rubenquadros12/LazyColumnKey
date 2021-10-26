package com.ruben.lazycolumnkey

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

/**
 * Created by Ruben Quadros on 27/10/21
 **/

@Entity(tableName = "test_table")
data class RandomIdiot(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDB(randomIdiot: RandomIdiot)

    @Query("SELECT * FROM `test_table` ORDER BY `created_at` DESC LIMIT 1")
    fun getData(): Flow<RandomIdiot>

    @Query("DELETE FROM `test_table`")
    suspend fun delete()
}

@Database(entities = [RandomIdiot::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun myDao(): MyDao
}