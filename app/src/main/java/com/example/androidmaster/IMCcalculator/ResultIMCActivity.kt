package com.example.androidmaster.IMCcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidmaster.IMCcalculator.ImcCalculatorActivity.Companion.IMC_KEY
import com.example.androidmaster.R

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnRecalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result_imcactivity)
        val result: Double = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initComponents()
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btnRecalculate.setOnClickListener { onBackPressed() }
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()
        when (result) {
            //Bajo Peso
            in 0.00..18.50 -> {
                tvResult.text = getString(R.string.low_weight)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.low_weight))
                tvDescription.text =
                    getString(R.string.you_are_below_optimal_for_your_weight_and_height)
            }
            //Peso Normal
            in 18.51..24.99 -> {
                tvResult.text = getString(R.string.normal_weight)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.normal_weight))
                tvDescription.text =
                    getString(R.string.you_are_within_the_normal_range_for_your_weight_and_height)
            }
            //Peso Sobrepeso
            in 25.00..29.99 -> {
                tvResult.text = getString(R.string.over_weight)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.over_weight))
                tvDescription.text =
                    getString(R.string.you_are_above_the_normal_range_for_your_weight_and_height)
            }

            //Peso Obesidad
            in 30.00..99.00 -> {
                tvResult.text = getString(R.string.obesity)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesity_weight))
                tvDescription.text =
                    getString(R.string.you_are_well_above_the_normal_range_for_your_weight_and_height)
            }
            //Error
            else -> {
                tvIMC.text = getString(R.string.error)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesity_weight))
                tvResult.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }

        }
    }

    private fun initComponents() {
        tvIMC = findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)

    }
}