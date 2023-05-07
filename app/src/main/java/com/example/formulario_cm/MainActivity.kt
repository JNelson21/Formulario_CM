package com.example.formulario_cm

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.formulario_cm.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayCarreras: ArrayAdapter<CharSequence>

    //Inicializando variables
    private var idImg = 0
    private var nombre = ""
    private var apellido = ""
    private var fecha_nacimiento = Triple(0,0,0)
    private var correo_electronico = ""
    private var num_cuenta = ""
    private var carrera = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSpinner()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setDatePicker()
        }
    }

    private fun setSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        arrayCarreras = ArrayAdapter.createFromResource(
            this,
            R.array.careers_array,
            android.R.layout.simple_spinner_item
        )
        binding.spinnerCarrera.adapter = arrayCarreras
        binding.spinnerCarrera.onItemSelectedListener = this
    }

    @RequiresApi(Build.VERSION_CODES.N)


    private fun setDatePicker() {
        val today = Calendar.getInstance()
        binding.inputFechaNacimiento.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val newMonth = month + 1
            fecha_nacimiento = Triple(day, newMonth, year)
        }
    }

    fun newProfile(view: View) {
        nombre = binding.inputNombre.text.toString()
        apellido = binding.inputApellido.text.toString()
        correo_electronico = binding.inputEmail.text.toString()
        num_cuenta = binding.inputNumeroCuenta.text.toString()

        var estudiante = Estudiante(nombre,apellido,fecha_nacimiento,correo_electronico,num_cuenta,carrera)
        if (estudiante.check()) {
            val (isOk, errorMsg) = estudiante.checkAttributes(resources)
            if (isOk) {
                val intent = Intent(this, Profile::class.java)
                val bundle = Bundle()
                bundle.putParcelable("Estudiante", estudiante)
                bundle.putInt("idImg", idImg)
                intent.putExtras(bundle)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    errorMsg,
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                getString(R.string.completarFormulario),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, p: Int, id: Long) {
        carrera = arrayCarreras.getItem(p).toString()
        idImg = p
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}