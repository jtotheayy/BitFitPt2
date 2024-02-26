package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FoodEnterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_entry_activity)

        val submitButton: Button = findViewById(R.id.submit_button)
        val editTextFoodName: EditText = findViewById(R.id.editTextFoodName)
        val editTextCalories: EditText = findViewById(R.id.editTextCal)


        submitButton.setOnClickListener {
            val foodName = editTextFoodName.text.toString()
            val calories = editTextCalories.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("foodName", foodName)
            resultIntent.putExtra("calories", calories)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}