package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val items: List<DisplayFood>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val foodNameView: TextView
        val foodCalorieView: TextView

        init{
            foodNameView = itemView.findViewById(R.id.food)
            foodCalorieView = itemView.findViewById(R.id.calories_num)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemView = inflater.inflate(R.layout.food_item, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.foodNameView.text = item.foodItem
        holder.foodCalorieView.text = item.foodCalories

    }

}