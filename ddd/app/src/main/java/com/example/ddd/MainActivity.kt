package com.example.ddd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка текущего пользователя
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val currentUser = sharedPreferences.getString("current_user", null)

        val intent = if (currentUser != null) {
            // Пользователь уже авторизован
            Intent(this, HomeActivity::class.java)
        } else {
            // Пользователь не авторизован
            Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish() // Закрываем MainActivity
    }
}





