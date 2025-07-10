package com.example.assignment6

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        val name = intent.getStringExtra("name") ?: "Unknown"
        val amount = intent.getDoubleExtra("amount", 0.0)
        val date = intent.getStringExtra("date") ?: "No date provided"

        val nameText: TextView = findViewById(R.id.detailName)
        val amountText: TextView = findViewById(R.id.detailAmount)
        val dateText: TextView = findViewById(R.id.detailDate)

        nameText.text = "Name: $name"
        amountText.text = "Amount: $${"%.2f".format(amount)}"
        dateText.text = "Date: $date"
    }
}

