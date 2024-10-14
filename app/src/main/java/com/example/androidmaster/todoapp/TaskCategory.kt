package com.example.androidmaster.todoapp

sealed class TaskCategory (var isSelected:Boolean = true){
    // Esto se hace para asignarle un atributo a todos los objetos, si se desea solo
    // que sea aplicado a unos pero no a otros se debe aplicar no como objeto sino como
    // data class
    object Personal : TaskCategory()
    object Business : TaskCategory()
    object Other : TaskCategory()

}

/*
package com.example.androidmaster.todoapp

enum class TaskCategory {
    Business,
    Other,
    Personal
}
*/

