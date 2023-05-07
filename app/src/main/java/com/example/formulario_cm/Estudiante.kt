package com.example.formulario_cm

import android.content.res.Resources
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Estudiante(
    val nombre: String?,
    val apellido: String?,
    val birthdate: Triple<Int, Int, Int>?,
    val correo: String?,
    val numero_cuenta: String?,
    val carrera: String?
) : Parcelable {

    fun getYears(): Int {
        val today = Calendar.getInstance()
        return today.get(Calendar.YEAR) - (birthdate?.third ?: 25000000)
    }

    fun getZodiSign(): Int {
        val month: Int? = birthdate?.second
        var day: Int? = birthdate?.first ?: 0
        day = day ?: 0

        return when (month) {
            1 -> if (day >= 20) R.string.Acuario else R.string.Capricornio
            2 -> if (day >= 19) R.string.Piscis else R.string.Acuario
            3 -> if (day >= 21) R.string.Aries else R.string.Piscis
            4 -> if (day >= 20) R.string.Tauro else R.string.Aries
            5 -> if (day >= 21) R.string.Geminis else R.string.Tauro
            6 -> if (day >= 21) R.string.Cancer else R.string.Geminis
            7 -> if (day >= 23) R.string.Leo else R.string.Cancer
            8 -> if (day >= 23) R.string.Virgo else R.string.Leo
            9 -> if (day >= 23) R.string.Libra else R.string.Virgo
            10 -> if (day >= 23) R.string.Escorpio else R.string.Libra
            11 -> if (day >= 22) R.string.Sagitario else R.string.Escorpio
            12 -> if (day >= 22) R.string.Capricornio else R.string.Sagitario
            else -> 0
        }
    }

    fun getChineseHoroscope(): Int {
        return when (birthdate?.third) {
            0 -> R.string.Mono
            1 -> R.string.Pollo
            2 -> R.string.Perro
            3 -> R.string.Cerdo
            4 -> R.string.Rata
            5 -> R.string.Buey
            6 -> R.string.Tigre
            7 -> R.string.Conejo
            8 -> R.string.Dragon
            9 -> R.string.Serpiente
            10 -> R.string.Caballo
            else -> R.string.Oveja
        }
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
        return pattern.matches(email)
    }


    fun check(): Boolean {
        val isComplete = !(nombre.isNullOrEmpty() || apellido.isNullOrEmpty() || correo.isNullOrEmpty() ||
                numero_cuenta.isNullOrEmpty() || carrera == "Ninguno" || carrera == "None")

        if ((birthdate?.toList()?.distinct()?.size ?: 0) == 1) {
            return false
        }

        return isComplete
    }

    fun checkAttributes(resources: Resources): Pair<Boolean, String> {
        var ok = true
        var msg = ""

        if (numero_cuenta?.length != 9) {
            ok = false
            msg = resources.getString(R.string.msCuenta)
        } else if (carrera == "Ninguno") {
            ok = false
            msg = resources.getString(R.string.msCarrera)
        } else if (getYears() < 0) {
            ok = false
            msg = resources.getString(R.string.msFechaNacimiento)
        } else if (!correo?.let { isEmailValid(it) }!!) {
            ok = false
            msg = resources.getString(R.string.msCorreoElectronico)
        }

        return Pair(ok, msg)
    }


}
