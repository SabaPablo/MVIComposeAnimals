package com.doce.cactus.saba.nvianimalscompose.api

import com.doce.cactus.saba.nvianimalscompose.model.Animal
import retrofit2.http.GET

interface AnimalApi {

    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}