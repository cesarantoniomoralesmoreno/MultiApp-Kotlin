package com.example.androidmaster.exercises.firstApp

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidmaster.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)
        supportActionBar?.hide()
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val name:String = intent.extras?.getString("EXTRA_NAME").orEmpty()
        tvResult.text = "Hola $name"
    }
}