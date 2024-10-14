package com.example.androidmaster.IMCcalculator

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.androidmaster.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
class ImcCalculatorActivity : AppCompatActivity() {
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false

    private var currentWeight: Int = 70
    private var currentAge: Int = 30
    private var currentHeight: Int = 120

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight:FloatingActionButton
    private lateinit var btnPlusWeight:FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    companion object{
        const val IMC_KEY = "IMC_RESULT"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_imc_calculator)
        supportActionBar?.hide()
        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubstractAge = findViewById(R.id.btnSubtractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            // Si se selecciona hombre, deseleccionar mujer
            isMaleSelected = true
            isFemaleSelected = false
            setGenderColor()
        }

        viewFemale.setOnClickListener {
            // Si se selecciona mujer, deseleccionar hombre
            isFemaleSelected = true
            isMaleSelected = false
            setGenderColor()
        }

        rsHeight.addOnChangeListener { _, value, _ ->

            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }

        btnPlusWeight.setOnClickListener{
            currentWeight += 1
            setWeight()
        }

        btnSubtractWeight.setOnClickListener{
            currentWeight -= 1
            setWeight()
        }

        btnPlusAge.setOnClickListener{
            currentAge += 1
            setAge()
        }

        btnSubstractAge.setOnClickListener{
            currentAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener{
           val result = calculateIMC()
            navigateToResult(result)
        }


    }

    private fun navigateToResult(result:Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY,result)
        startActivity(intent)
    }

    private fun calculateIMC():Double {
        val df= DecimalFormat("#.##")
        val imc = currentWeight/((currentHeight.toDouble()/100)*(currentHeight.toDouble()/100))
        return df.format(imc).toDouble()

    }

    private fun setAge() {
        tvAge.text=currentAge.toString()
    }

    private fun setWeight() {
        tvWeight.text=currentWeight.toString()
    }


    private fun setGenderColor() {
        // Aplicar colores en base al estado de selección
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        // Devolver color seleccionado o no seleccionado
        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }

}