package com.example.androidmaster.superheroapp

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.androidmaster.R
import com.example.androidmaster.databinding.ActivityDetailSuperheroBinding
import com.example.androidmaster.databinding.ActivitySuperHeroListBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {

    companion object {//Declaramos una constante
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailSuperheroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInformation(id)

    }

    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = getRetrofit().create(ApiService::class.java).getSuperheroDetail(id)
                val superheroDetail = response.body()
                if (superheroDetail != null) {
                    runOnUiThread { createUI(superheroDetail) }
                } else {
                    Log.e("DetailSuperheroActivity", "El detalle del superhéroe es nulo.")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtener información del API")
            }
        }
    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        superhero.image?.url?.let {
            Picasso.get().load(it).into(binding.ivSuperhero)
            binding.tvSuperHeroName.text = superhero.name
            prepareStats(superhero.powerstats)

            binding.tvSuperHeroRealName.text = superhero.biography.fullName
            binding.tvPublisher.text = superhero.biography.publisher
        } ?: run {
            // Maneja el caso donde la imagen es nula
            Log.e("DetailSuperheroActivity", "La URL de la imagen es nula.")
        }
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        updateHeight(binding.viewCombat, powerstats.combat)
        updateHeight(binding.viewDurability, powerstats.durability)
        updateHeight(binding.viewSpeed, powerstats.speed)
        updateHeight(binding.viewStrength, powerstats.strength)
        updateHeight(binding.viewIntelligence, powerstats.intelligence)
        updateHeight(binding.viewPower, powerstats.power)

    }

        private fun updateHeight(view: View, stat:String){

            val params = view.layoutParams
            params.height = pxToDp(stat.toFloat())
            view.layoutParams = params
        }

    private fun pxToDp(px:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}