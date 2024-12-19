package com.example.ddd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var recipesListView: ListView
    private lateinit var recipesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Инициализация ListView
        recipesListView = findViewById(R.id.recipesListView)
        recipesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, loadRecipeTitles())
        recipesListView.adapter = recipesAdapter

        // Обработчик нажатия на рецепт
        recipesListView.setOnItemClickListener { _, _, position, _ ->
            val recipeTitle = recipesAdapter.getItem(position) ?: return@setOnItemClickListener
            val recipeDetails = getRecipeDetails(recipeTitle)

            // Переход к экрану с подробной информацией
            val intent = Intent(this, ViewRecipeActivity::class.java)
            intent.putExtra("recipeTitle", recipeTitle)
            intent.putExtra("recipeIngredients", recipeDetails.first)
            intent.putExtra("recipeDescription", recipeDetails.second)
            startActivityForResult(intent, 1) // Ждем результат
        }

        // Кнопка добавления рецепта
        val addRecipeButton: Button = findViewById(R.id.addRecipeButton)
        addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        // Кнопка выхода
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            sharedPreferences.edit().remove("current_user").apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закрываем текущую активность
        }
    }

    // Загружаем только названия рецептов как изменяемую коллекцию
    private fun loadRecipeTitles(): List<String> {
        val sharedPreferences = getSharedPreferences("RecipePrefs", MODE_PRIVATE)
        val recipeTitles = sharedPreferences.getStringSet("recipeTitles", emptySet()) ?: emptySet()

        // Преобразуем в изменяемый список (ArrayList)
        return ArrayList(recipeTitles)
    }

    // Получаем детали рецепта по названию
    private fun getRecipeDetails(title: String): Pair<String, String> {
        val sharedPreferences = getSharedPreferences("RecipePrefs", MODE_PRIVATE)
        val ingredients = sharedPreferences.getString("${title}_ingredients", "Ингредиенты не указаны") ?: "Ингредиенты не указаны"
        val description = sharedPreferences.getString("${title}_description", "Описание не указано") ?: "Описание не указано"
        return Pair(ingredients, description)
    }

    // Обновление списка рецептов
    private fun updateRecipeList() {
        val updatedTitles = loadRecipeTitles()
        recipesAdapter.clear() // Очистка адаптера
        recipesAdapter.addAll(updatedTitles) // Добавление новых данных
        recipesAdapter.notifyDataSetChanged() // Уведомляем адаптер об изменениях
    }

    override fun onResume() {
        super.onResume()
        updateRecipeList() // Обновляем список рецептов при возврате
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Обновляем список рецептов, если он был изменен
            updateRecipeList()
        }
    }
}












































































