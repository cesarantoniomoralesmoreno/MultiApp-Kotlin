package com.example.androidmaster.todoapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmaster.R
import com.example.androidmaster.todoapp.TaskCategory.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ToDoActivity : AppCompatActivity() {

    private val categories = listOf(
        Business,
        Other,
        Personal
    )

    private val tasks = mutableListOf(
        Task("PruebaBusiness", Business),
        Task("PruebaPersonal", Personal),
        Task("PruebaOther", Other)
        )



    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var rvTask:RecyclerView
    private lateinit var tasksAdapter: TaskAdapter

    private lateinit var fabAddTask:FloatingActionButton
    private lateinit var rgCategories: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_to_do)
        initComponents()
        initUI()
        initListeners()
    }

    private fun initListeners() {
        fabAddTask.setOnClickListener{ showDialog()}
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)

        btnAddTask.setOnClickListener{
            val currentTask = etTask.text.toString()
            if(currentTask.isNotEmpty()) {
                val selectedId = rgCategories.checkedRadioButtonId
                val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)
                val currentCategory: TaskCategory = when (selectedRadioButton.text) {
                    getString(R.string.bussines) -> Business
                    getString(R.string.personal) -> Personal
                    else -> Other
                }


                tasks.add(Task(currentTask, currentCategory))
                updateTasks()
                dialog.hide()
            }
        }
        dialog.show()
    }

    private fun initComponents() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTask = findViewById(R.id.rvTask)
        fabAddTask = findViewById(R.id.fabAddTask)

    }

    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) {position -> updateCategories(position) }
        rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter

        tasksAdapter = TaskAdapter(tasks) {position -> onItemSelected(position)}// se puede poner solo it
        rvTask.layoutManager = LinearLayoutManager(this)//Para solo vertical basta con el this
        rvTask.adapter =  tasksAdapter
    }

    private fun onItemSelected(position: Int){
        tasks[position].isSelected = !tasks[position].isSelected
        updateTasks()
    }
    private fun updateCategories(position: Int){
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }
    private fun updateTasks(){
        val selectedCategories : List<TaskCategory> = categories.filter { it.isSelected }
        val newTasks = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = newTasks
        tasksAdapter.notifyDataSetChanged()
    }
}
