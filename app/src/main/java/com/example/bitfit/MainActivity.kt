package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var foods: MutableList<DisplayFood>
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foods = mutableListOf()
        val layoutManager = LinearLayoutManager(this) // Use LinearLayoutManager or another layout manager

        val foodsRV = findViewById<RecyclerView>(R.id.foodsRV)
        foodsRV.layoutManager = LinearLayoutManager(this)
        foodAdapter = FoodAdapter(foods)
        foodsRV.adapter = foodAdapter // Set the adapter to the RecyclerView

        lifecycleScope.launch {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.foodName,
                        entity.foodCal,

                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener {
            val intent = Intent(this, FoodEnterActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val foodName = data?.getStringExtra("foodName").toString()
            val calories = data?.getStringExtra("calories").toString()

            if (foodName.isNotEmpty() && calories.isNotEmpty()) {
                // 1. Create a FoodEntity for the new food
                val newFoodEntity = FoodEntity(0, foodName, calories)

                // 2. Use a Coroutine to insert the data into the Room database
                lifecycleScope.launch(IO) {
                    (application as FoodApplication).db.foodDao().insert(newFoodEntity)
                }

                // 3. Update the UI with the new food data
                val newFood = DisplayFood(foodName, calories)
                foods.add(newFood)
                foodAdapter.notifyDataSetChanged()
            }
        }
    }

}
