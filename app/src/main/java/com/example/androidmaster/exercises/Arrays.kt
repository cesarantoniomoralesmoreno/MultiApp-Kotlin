package com.example.androidmaster.exercises

fun main(){
    var name:String ="AristiDevs1"
    var name1:String ="AristiDevs2"
    var name2:String ="AristiDevs3"
    var name3:String ="AristiDevs4"

    val weekDays = arrayOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo")
    for (i in weekDays.indices) {
        println(weekDays[i])
    }

    for(position in weekDays.indices){//dame la posicion de cada indice
        println("he pasado por aqui: $position")//Me devuelve cada indice
        println(weekDays[position])
    }
    for((position,value) in weekDays.withIndex()){
        println("La posicion $position, contiene $value")
    }
}