package com.example.androidmaster.superheroapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/984009360f20e136e65004daacd2984c/search/{name}")
    //Para usar Corutinas
    suspend fun getSuperheroes(@Path("name") superheroName:String):Response<SuperHeroDataResponse>
    @GET("/api/984009360f20e136e65004daacd2984c/{id}")
    suspend fun getSuperheroDetail(@Path("id") superheroId:String):Response<SuperHeroDetailResponse>
}