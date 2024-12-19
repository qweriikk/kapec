package com.example.ddd

import java.io.Serializable

data class Recipe(
    val title: String,
    val ingredients: String,
    val description: String,
    val username: String // Добавляем поле для имени пользователя
) : Serializable // Делаем класс Serializable

