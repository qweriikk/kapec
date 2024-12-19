package com.example.ddd

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ArrayAdapter

class RecipeAdapter(context: Context, private val recipes: List<String>) :
    ArrayAdapter<String>(context, 0, recipes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)

        val recipeTextView: TextView = view.findViewById(R.id.recipeTextView)
        val viewButton: Button = view.findViewById(R.id.viewRecipeButton)

        // Извлекаем название рецепта
        val recipeData = recipes[position]
        val title = recipeData.split("|")[0] // Название рецепта, которое до первого разделителя
        recipeTextView.text = title

        // Обработчик нажатия на кнопку "Посмотреть рецепт"
        viewButton.setOnClickListener {
            val intent = Intent(context, ViewRecipeActivity::class.java)

            // Получаем все данные для рецепта
            val recipeDetails = loadRecipeDetails(title) // Функция для получения данных рецепта
            intent.putExtra("recipeTitle", recipeDetails.first) // Название
            intent.putExtra("recipeIngredients", recipeDetails.second) // Ингредиенты
            intent.putExtra("recipeDescription", recipeDetails.third) // Описание

            context.startActivity(intent)
        }

        return view
    }

    // Функция для загрузки подробных данных о рецепте из SharedPreferences
    private fun loadRecipeDetails(recipeTitle: String): Triple<String, String, String> {
        val sharedPreferences = context.getSharedPreferences("RecipePrefs", Context.MODE_PRIVATE)
        val recipes = sharedPreferences.getString("recipes", "") ?: ""

        // Ищем рецепт в списке, предполагая, что рецепт имеет формат "Название|Ингредиенты|Описание"
        val recipe = recipes.split(";").find { it.startsWith(recipeTitle) } ?: return Triple("Неизвестный рецепт", "Ингредиенты отсутствуют", "Описание отсутствует")

        // Разделяем данные рецепта по "|"
        val recipeParts = recipe.split("|")
        val title = recipeParts.getOrNull(0) ?: "Неизвестный рецепт"
        val ingredients = recipeParts.getOrNull(1) ?: "Ингредиенты отсутствуют"
        val description = recipeParts.getOrNull(2) ?: "Описание отсутствует"

        return Triple(title, ingredients, description)
    }
}





