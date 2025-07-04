package com.example.assignment5

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val showNameButton = findViewById<Button>(R.id.showNameButton)
        val outputTextView = findViewById<TextView>(R.id.outputTextView)

        showNameButton.setOnClickListener {
            val name = nameEditText.text.toString()
            outputTextView.text = "Hello, $name!"
        }
    }
}
