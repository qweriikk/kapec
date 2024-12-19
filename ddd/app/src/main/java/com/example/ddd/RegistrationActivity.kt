package com.example.ddd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registerButton: Button = findViewById(R.id.registerButton)
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginLink: TextView = findViewById(R.id.loginLink) // Ссылка на экран входа

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Проверка на пустые поля
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Проверка, существует ли пользователь
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val userExists = sharedPreferences.contains(username)

            if (userExists) {
                Toast.makeText(this, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Сохраняем нового пользователя
            val editor = sharedPreferences.edit()
            editor.putString(username, password)

            // Проверяем результат сохранения
            val result = editor.commit()
            if (result) {
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                Log.d("DEBUG", "User saved successfully: $username")

                // Сохраняем текущего пользователя
                sharedPreferences.edit().putString("current_user", username).apply()

                // Переход на главный экран
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Ошибка сохранения данных", Toast.LENGTH_SHORT).show()
                Log.e("DEBUG", "Failed to save user: $username")
            }
        }

        // Обработчик для перехода на экран входа
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Закрываем экран регистрации
        }
    }
}



























