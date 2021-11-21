package com.clasemovil.horoscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.clasemovil.horoscope.databinding.ActivityResultsBinding

class Results : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private var animArray = emptyArray<String>()


    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animArray = resources.getStringArray(R.array.zodiacAnimals)
        val bundle = intent.extras

        val name = bundle!!.getString("Name", "NameErr")
        val account = bundle.getString("Account", "AccountErr")
        val email = bundle.getString("Email", "EmailErr")
        val age = bundle.getString("Age", "AgeErr")
        val zodiacIndex = bundle.getInt("ZodiacSign", 0)

        setText(name, account, email, age, zodiacIndex)
        setImage(zodiacIndex)
    }

    private fun setText(name:String, account:String, email:String, age:String, zodInd: Int){
        binding.NameRes.text = resources.getString(R.string.nameResult, name)
        binding.AccountRes.text = resources.getString(R.string.accountResult, account)
        binding.EmailRes.text = resources.getString(R.string.emailResult, email)
        binding.AgeRes.text = resources.getString(R.string.ageResult, age)
        binding.ZodiacRes.text = animArray[zodInd]
    }

    private fun setImage(zodInd: Int){
        when(zodInd){
            0 -> binding.resultImage.setImageResource(R.drawable.z1)
            1 -> binding.resultImage.setImageResource(R.drawable.z2)
            2 -> binding.resultImage.setImageResource(R.drawable.z3)
            3 -> binding.resultImage.setImageResource(R.drawable.z4)
            4 -> binding.resultImage.setImageResource(R.drawable.z5)
            5 -> binding.resultImage.setImageResource(R.drawable.z6)
            6 -> binding.resultImage.setImageResource(R.drawable.z7)
            7 -> binding.resultImage.setImageResource(R.drawable.z8)
            8 -> binding.resultImage.setImageResource(R.drawable.z9)
            9 -> binding.resultImage.setImageResource(R.drawable.z10)
            10 -> binding.resultImage.setImageResource(R.drawable.z11)
            11 -> binding.resultImage.setImageResource(R.drawable.z12)
        }

    }
}