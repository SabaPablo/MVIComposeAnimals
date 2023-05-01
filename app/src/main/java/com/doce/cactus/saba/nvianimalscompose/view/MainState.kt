package com.doce.cactus.saba.nvianimalscompose.view

import com.doce.cactus.saba.nvianimalscompose.model.Animal

sealed class MainState {

    object Idle: MainState()
    object Loading: MainState()
    data class Animals(val animals: List<Animal>): MainState()
    data class Error(val error: String?): MainState()
}