package com.clasemovil.horoscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.clasemovil.horoscope.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dateArr = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Verificar nombre
        if(name.length > 3){
            // Verificar correo
            if(verifyEmail(email)){
                // Verificar numero de cuenta
                if(account.length == 9)
                {
                    // Verificar fecha de nacimiento
                    if(dateArr.size == 3){
                        Toast.makeText(this, getAge(), Toast.LENGTH_LONG).show()
                    }
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

    private fun verifyEmail(s : String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }
}