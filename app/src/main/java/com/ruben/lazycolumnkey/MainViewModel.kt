package com.ruben.lazycolumnkey

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 26/10/21
 **/
const val MAX_SIZE = 20

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo): ViewModel() {

    val snapshotStateList = SnapshotStateList<RandomIdiot>()

    init {
        getItemsInternal()
    }

    fun addItem() {
        viewModelScope.launch {
            repo.insert(RandomIdiot(UUID.randomUUID().toString(), "Ruben", Calendar.getInstance().timeInMillis))
        }
    }

    private fun getItemsInternal() {
        viewModelScope.launch {
            repo.getData().collect {
                if (snapshotStateList.size >= MAX_SIZE) {
                    snapshotStateList.removeLast()
                }
                snapshotStateList.add(0, it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch { repo.clear() }
    }
}