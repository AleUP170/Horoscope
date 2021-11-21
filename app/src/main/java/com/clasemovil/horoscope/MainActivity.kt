package com.clasemovil.horoscope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.clasemovil.horoscope.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dateArr = mutableListOf<Int>()
    private var animArray = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar barra de estado
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        animArray = resources.getStringArray(R.array.zodiacAnimals)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Para abrir calendario
        binding.dateInput.setOnClickListener{
            showDateDialog()
        }

        // Para verificar datos
        binding.button.setOnClickListener{
            verifyData()
        }

    }

    private fun showDateDialog() {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int){
        val correctMonth : Int = month + 1
        binding.dateInput.setText("$day / $correctMonth / $year")
        dateArr.clear()
        dateArr.add(day)
        dateArr.add(month)
        dateArr.add(year)
    }


    private fun verifyData(){
        val name : String = binding.nameInput.text.toString()
        val email : String = binding.mailInput.text.toString()
        val account : String = binding.accountInput.text.toString()
        var zodiacIndex : Int
        var age: String

        // Verificar nombre
        if(name.length >= 3){
            // Verificar correo
            if(verifyEmail(email)){
                // Verificar numero de cuenta
                if(verifyAccount(account))
                {
                    // Verificar fecha de nacimiento
                    // Si no tiene 3 elementos entonces no ha ingresado algo
                    if(dateArr.size == 3){
                        age = getAge()
                        zodiacIndex = getZodiacIndex()
                        goToResults(name, account, email, age, zodiacIndex)
                    }
                    // Error de fecha
                    else {
                        Toast.makeText(this, R.string.dateError, Toast.LENGTH_LONG).show()
                    }
                }
                // Error de cuenta
                else
                {
                    Toast.makeText(this, R.string.numError, Toast.LENGTH_LONG).show()
                }
            }
            // Error de correo
            else{
                Toast.makeText(this, R.string.emailError, Toast.LENGTH_LONG).show()
            }
        }
        // Error de nombre
        else{
            Toast.makeText(this, R.string.nameError, Toast.LENGTH_LONG).show()
        }
    }

    // Obtiene edad según fecha de nacimiento
    private fun getAge(): String{
        val todayDate = Calendar.getInstance()

        var years : Int = todayDate.get(Calendar.YEAR) - dateArr[2] // Year
        if (todayDate.get(Calendar.MONTH) < dateArr[1])
        {
            years -= 1
        }
        else if(todayDate.get(Calendar.DAY_OF_MONTH) < dateArr[0]){
            years -= 1
        }

        return years.toString()
    }

    // Verifica correo usando la función proveída por Android
    private fun verifyEmail(s : String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }

    // Verifica si el número de cuenta es un número de 9 dígitos
    private fun verifyAccount(s : String): Boolean{
        if(s.length == 9){
            val accNum : Int? = s.toIntOrNull()
            if(accNum != null){
                return true
            }
        }
        return false
    }

    private fun getZodiacIndex(): Int{
        var temp : Int
        // 12 signos, empezando por el año de la rata
        // Para que coincidan, se resta 4 al año
        temp = (dateArr[2] - 4) % 12
        // Si el mes de nacimiento es enero, restarle 1 al índice
        if(dateArr[1] == 0){
            temp -= 1
            if(temp == -1){
                temp = 11
            }
        }
        return temp
    }

    private fun goToResults(name:String, account:String, email:String, age:String, zodInd:Int){
        val intent = Intent(this, Results::class.java)
        val params = Bundle()

        params.putString("Name", name)
        params.putString("Account", account)
        params.putString("Email", email)
        params.putString("Age", age)
        params.putInt("ZodiacSign", zodInd)

        intent.putExtras(params)

        startActivity(intent)
    }
}