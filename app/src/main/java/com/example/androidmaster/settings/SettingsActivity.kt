package com.example.androidmaster.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidmaster.R
import com.example.androidmaster.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")// Delegado para crear una unica instancia de la base de datos

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_BLUETOOTH = "key_bluetooth"
        const val KEY_VIBRATION = "key_vibration"
        const val KEY_DARK_MODE = "key_dark_mode"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var firstTime:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        //La coleccion de datos debe hacerse en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect{ settingsModel ->
                if(settingsModel!=null){
                    runOnUiThread{ //Esto al modificar la interfaz grafica debe ejecutarse en el hilo principal
                        binding.switchVibration.isChecked = settingsModel.vibration
                        binding.switchBluetooth.isChecked = settingsModel.bluetooth
                        binding.switchDarkMode.isChecked = settingsModel.darkMode
                        binding.rsVolume.setValues(settingsModel.volume.toFloat())
                        firstTime = false
                    }

                }
                //datos SettingsModel()


            }
        }

        initUI()

    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            Log.i("Cesar", "El valor es $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolumen(value.toInt())
            }//El IO es para procesos largos como base de datos, backend etc

        }
        binding.switchBluetooth.setOnCheckedChangeListener { _, value ->

            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_BLUETOOTH, value)
            }//El IO es para procesos largos como base de datos, backend etc
        }

        binding.switchVibration.setOnCheckedChangeListener { _, value ->

            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATION, value)
            }//El IO es para procesos largos como base de datos, backend etc
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            //Para cambios de la interfaz grafica se hace en el hilo principal
            if (value) {
                enableDarkMode()
            }else{
                disableDarkMode()
            }
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_DARK_MODE, value)
            }//El IO es para procesos largos como base de datos, backend etc
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private suspend fun saveVolumen(value: Int) {//Para poder correr en hilos secundarios con coroutinas se usa el suspend
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_LVL)] = value//Todo depende del tipo de variable
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {//Funcion para recuperar los datos a traves de un flujo
        return dataStore.data.map { preferences ->
            SettingsModel(
                volume = preferences[intPreferencesKey(VOLUME_LVL)]
                    ?: 50, //operador ? si no encuentras un valor que no sea nulo devuelve el 50
                bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: true

            )
        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }
    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}