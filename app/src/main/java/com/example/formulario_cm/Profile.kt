package com.example.formulario_cm

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulario_cm.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private val arrayImagenes  = setArrayCarreras()
    private var idImg = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setArrayCarreras()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            val estudiante = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
                bundle.getParcelable("Estudiante", Estudiante::class.java)
            } else {
                bundle.getParcelable("Estudiante")
            }
            idImg = bundle.getInt("idImg")

            if (estudiante != null) {
                passContent(estudiante)
            }
        }

    }

    private fun passContent(estudiante: Estudiante) {
        binding.imgCarrera.setImageResource(arrayImagenes[idImg])
        binding.tvNombreCompleto.text = getString(R.string.nombreCompleto, estudiante.nombre, estudiante.apellido)
        binding.tvEdad.text = resources.getQuantityString(R.plurals.yearsOld, estudiante.getYears(), estudiante.getYears())
        binding.tvCorreoElectronico.text = estudiante.correo
        binding.tvNumeroCuenta.text = estudiante.numero_cuenta
        binding.tvSignoZodiacal.text = getString(estudiante.getZodiSign())
        binding.tvHoroscopoChino.text = getString(estudiante.getChineseHoroscope())
    }

    private fun setArrayCarreras() : ArrayList<Int>{
        val arrayImagenes = ArrayList<Int>(16)
        arrayImagenes.add(0)
        arrayImagenes.add(R.drawable.ingenieria_espacial)
        arrayImagenes.add(R.drawable.ingenieria_civil)
        arrayImagenes.add(R.drawable.ingenieria_geomatica)
        arrayImagenes.add(R.drawable.ingenieria_ambiental)
        arrayImagenes.add(R.drawable.ingenieria_geofisica)
        arrayImagenes.add(R.drawable.ingenieria_geologica)
        arrayImagenes.add(R.drawable.ingenieria_petrolera)
        arrayImagenes.add(R.drawable.ingenieria_minas)
        arrayImagenes.add(R.drawable.ingenieria_computacion)
        arrayImagenes.add(R.drawable.ingenieria_electronica)
        arrayImagenes.add(R.drawable.ingenieria_telecomunicaciones)
        arrayImagenes.add(R.drawable.ingenieria_mecanica)
        arrayImagenes.add(R.drawable.ingenieria_industrial)
        arrayImagenes.add(R.drawable.ingenieria_mecatronica)
        arrayImagenes.add(R.drawable.ingenieria_biomedica)
        return arrayImagenes
    }
}