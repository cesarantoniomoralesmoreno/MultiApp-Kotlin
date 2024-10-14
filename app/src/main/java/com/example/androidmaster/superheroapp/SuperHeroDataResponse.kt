package com.example.androidmaster.superheroapp

import android.media.Image
import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superHeroes: List<SuperheroItemResponse>
)//Recomendacion colocarlo de esta forma

data class SuperheroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superheroImage : SuperheroImageResponse  //Esto me arroja una url
)//Aqui se debe pasar el id para que haga match

data class SuperheroImageResponse(@SerializedName("url") val url: String)