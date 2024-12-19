package com.example.ddd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import org.json.JSONObject


class ViewRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recipe)

        val recipeTitleTextView: TextView = findViewById(R.id.recipeTitleTextView)
        val recipeIngredientsTextView: TextView = findViewById(R.id.recipeIngredientsTextView)
        val recipeDescriptionTextView: TextView = findViewById(R.id.recipeDescriptionTextView)
        val deleteButton: Button = findViewById(R.id.deleteButton)
        val backButton: Button = findViewById(R.id.backButton)

        val title = intent.getStringExtra("recipeTitle") ?: "Название не указано"
        val ingredients = intent.getStringExtra("recipeIngredients") ?: "Ингредиенты не указаны"
        val description = intent.getStringExtra("recipeDescription") ?: "Описание не указано"

        recipeTitleTextView.text = title
        recipeIngredientsTextView.text = "Ингредиенты: $ingredients"
        recipeDescriptionTextView.text = "Описание: $description"

        backButton.setOnClickListener { finish() }

        deleteButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("RecipePrefs", MODE_PRIVATE)
            val recipeTitles = sharedPreferences.getStringSet("recipeTitles", emptySet())?.toMutableSet() ?: mutableSetOf()

            // Удаляем рецепт из списка названий
            recipeTitles.remove(title)

            // Сохраняем обновленный список рецептов
            sharedPreferences.edit().putStringSet("recipeTitles", recipeTitles).apply()

            // Удаляем данные рецепта
            sharedPreferences.edit().remove("${title}_ingredients").remove("${title}_description").apply()

            // Показать уведомление
            Toast.makeText(this, "Рецепт удален", Toast.LENGTH_SHORT).show()

            // Уведомляем HomeActivity о том, что был удален рецепт
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}











































































