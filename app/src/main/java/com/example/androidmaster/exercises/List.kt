package com.example.androidmaster.exercises

fun main(){
    //inmutableList()
    mutableList()

}

fun mutableList() {
   val weekDays:MutableList<String> = mutableListOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo")
    weekDays.add(0,"AristiDay")
    println(weekDays)

    if(weekDays.isEmpty()){
        print("No hay elementos en la lista")
    }else{
        weekDays.forEach{weekDays-> println(weekDays)}
    }

    if(weekDays.isNotEmpty()){
        weekDays.forEach{println(it)}
    }

    weekDays.last()


}

fun inmutableList() {
    val readOnly:List<String> = listOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo")
    println(readOnly.size)
    println(readOnly)
    println(readOnly.last())
    println(readOnly.first())
//Filtrar
    val example = readOnly.filter { it.contains("M") }
    println(example)
    //Iterar
    readOnly.forEach{weekDay->println(weekDay)}  //Otra forma de llamar a la lista
}
