package com.doce.cactus.saba.nvianimalscompose.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doce.cactus.saba.nvianimalscompose.api.AnimalApi
import com.doce.cactus.saba.nvianimalscompose.api.AnimalRepo

class ViewModelFactory(private val api: AnimalApi): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}