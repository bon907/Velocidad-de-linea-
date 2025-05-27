package com.example.velocidaddelinea

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var inputUnidades: EditText
    private lateinit var resultado: TextView
    private lateinit var botonIniciar: Button
    private lateinit var subirVolumen: Button
    private lateinit var bajarVolumen: Button

    private val handler = Handler()
    private var intervalo: Long = 0
    private var volumen = 5
    private val tono = ToneGenerator(AudioManager.STREAM_ALARM, 100)

    private val beepRunnable = object : Runnable {
        override fun run() {
            tono.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
            handler.postDelayed(this, intervalo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputUnidades = findViewById(R.id.inputUnidades)
        resultado = findViewById(R.id.resultado)
        botonIniciar = findViewById(R.id.botonIniciar)
        subirVolumen = findViewById(R.id.subirVolumen)
        bajarVolumen = findViewById(R.id.bajarVolumen)

        botonIniciar.setOnClickListener {
            val unidadesPorHora = inputUnidades.text.toString().toDoubleOrNull()
            if (unidadesPorHora != null && unidadesPorHora > 0) {
                intervalo = ((3600.0 / unidadesPorHora) * 1000).toLong()
                resultado.text = "Intervalo: %.1f segundos".format(intervalo / 1000.0)
                handler.removeCallbacks(beepRunnable)
                handler.post(beepRunnable)
            }
        }

        subirVolumen.setOnClickListener {
            if (volumen < 10) volumen++
        }

        bajarVolumen.setOnClickListener {
            if (volumen > 0) volumen--
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(beepRunnable)
    }
}
