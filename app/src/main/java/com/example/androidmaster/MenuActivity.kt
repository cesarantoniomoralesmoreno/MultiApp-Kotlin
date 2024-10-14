package com.example.androidmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import com.example.androidmaster.IMCcalculator.ImcCalculatorActivity
import com.example.androidmaster.todoapp.ToDoActivity
import com.example.androidmaster.exercises.firstApp.FirstAppActivity
import com.example.androidmaster.settings.SettingsActivity
import com.example.androidmaster.superheroapp.SuperHeroListActivity


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)
        supportActionBar?.hide()
        //Emparejando Botones
        val btnSaludApp = findViewById<Button>(R.id.btnSaludApp)
        val btnIMCApp = findViewById<Button>(R.id.btnIMCApp)
        val btnToDo = findViewById<Button>(R.id.btnToDo)
        val btnSuperhero = findViewById<Button>(R.id.btnSuperhero)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        
        //Listeners
        btnSaludApp.setOnClickListener { navigateToSaludApp() }
        btnIMCApp.setOnClickListener { navigateToIMCApp() }
        btnToDo.setOnClickListener { navigateToDo() }
        btnSuperhero.setOnClickListener{navigateSuperheroApp()}
        btnSettings.setOnClickListener{navigateSettings()}
    }


    private fun navigateToDo() {
        val intent = Intent(this,ToDoActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToIMCApp() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSaludApp() {
        val intent = Intent(this, FirstAppActivity::class.java)
        startActivity(intent)

    }
    private fun navigateSuperheroApp() {
        val intent = Intent(this, SuperHeroListActivity::class.java)
        startActivity(intent)
    }

    private fun navigateSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}