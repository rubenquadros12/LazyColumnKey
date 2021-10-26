package com.ruben.lazycolumnkey

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 27/10/21
 **/
class Repo @Inject constructor(
    @ApplicationContext context: Context
) {

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "test.db").build()

    suspend fun insert(randomIdiot: RandomIdiot) {
        db.myDao().insertDB(randomIdiot)
    }

    fun getData() = db.myDao().getData().filterNotNull()

    suspend fun clear() {
        db.myDao().delete()
    }

}