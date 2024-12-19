package com.example.ddd

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var recipeTitleEditText: EditText
    private lateinit var recipeIngredientsEditText: EditText
    private lateinit var recipeDescriptionEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // Инициализация полей
        recipeTitleEditText = findViewById(R.id.recipeTitleEditText)
        recipeIngredientsEditText = findViewById(R.id.recipeIngredientsEditText)
        recipeDescriptionEditText = findViewById(R.id.recipeDescriptionEditText)
        saveButton = findViewById(R.id.saveRecipeButton)

        // Обработчик нажатия на кнопку сохранения
        saveButton.setOnClickListener {
            val title = recipeTitleEditText.text.toString().trim()
            val ingredients = recipeIngredientsEditText.text.toString().trim()
            val description = recipeDescriptionEditText.text.toString().trim()

            // Проверка на пустые поля
            if (title.isEmpty() || ingredients.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Сохранение рецепта
            saveRecipe(title, ingredients, description)

            // Переход обратно на главный экран
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveRecipe(title: String, ingredients: String, description: String) {
        val sharedPreferences = getSharedPreferences("RecipePrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Сохраняем название рецепта в Set для быстрого поиска
        val recipeTitles = sharedPreferences.getStringSet("recipeTitles", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        recipeTitles.add(title)
        editor.putStringSet("recipeTitles", recipeTitles)

        // Сохраняем детали рецепта
        editor.putString("${title}_ingredients", ingredients)
        editor.putString("${title}_description", description)

        editor.apply() // Сохраняем изменения
    }
}












































