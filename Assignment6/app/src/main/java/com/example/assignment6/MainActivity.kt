package com.example.assignment6

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment6.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ExpenseAdapter
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ExpenseAdapter(expenses) { position ->
            expenses.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = adapter

        val calendar = Calendar.getInstance()
        binding.dateEditText.setOnClickListener {
            DatePickerDialog(this, { _, year, month, day ->
                binding.dateEditText.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.addExpenseBtn.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val amountText = binding.amountEditText.text.toString().trim()
            val date = binding.dateEditText.text.toString().trim()

            if (name.isEmpty()) {
                binding.nameLayout.error = "Name required"
                return@setOnClickListener
            } else {
                binding.nameLayout.error = null
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                binding.amountLayout.error = "Enter valid amount"
                return@setOnClickListener
            } else {
                binding.amountLayout.error = null
            }

            val expense = Expense(name, amount, if (date.isNotEmpty()) date else null)
            expenses.add(expense)
            adapter.notifyItemInserted(expenses.size - 1)

            binding.nameEditText.text?.clear()
            binding.amountEditText.text?.clear()
            binding.dateEditText.text?.clear()

            Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
        }
    }
}
