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

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerLink: TextView = findViewById(R.id.registerLink) // Найти TextView

        // Обработчик для кнопки "Войти"
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameEditText).text.toString().trim()
            val password = findViewById<EditText>(R.id.passwordEditText).text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val storedPassword = sharedPreferences.getString(username, null)

            if (storedPassword != null && storedPassword == password) {
                Toast.makeText(this, "Авторизация успешна!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        // Обработчик для перехода на экран регистрации
        registerLink.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}























