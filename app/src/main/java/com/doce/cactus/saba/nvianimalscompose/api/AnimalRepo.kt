package com.doce.cactus.saba.nvianimalscompose.api

class AnimalRepo(private val api:AnimalApi) {
    suspend fun getAnimals() = api.getAnimals()
}