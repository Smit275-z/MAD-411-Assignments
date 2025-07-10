package com.example.assignment6

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
        Log.d("Lifecycle", "onCreate called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.header_container, HeaderFragment())
            .commit()

        val footerFragment = FooterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer_container, footerFragment)
            .commit()

        adapter = ExpenseAdapter(
            expenses,
            onDeleteClick = { position ->
                expenses.removeAt(position)
                adapter.notifyItemRemoved(position)
                val updatedTotal = expenses.sumOf { it.amount }
                (supportFragmentManager.findFragmentById(R.id.footer_container) as? FooterFragment)?.updateTotal(updatedTotal)
            },
            onDetailsClick = { expense ->
                val intent = Intent(this, ExpenseDetailsActivity::class.java).apply {
                    putExtra("name", expense.name)
                    putExtra("amount", expense.amount)
                    putExtra("date", expense.date)
                }
                startActivity(intent)
            }
        )

        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = adapter

        val calendar = Calendar.getInstance()
        binding.dateEditText.setOnClickListener {
            DatePickerDialog(
                this, { _, year, month, day ->
                    binding.dateEditText.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
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

            val updatedTotal = expenses.sumOf { it.amount }
            (supportFragmentManager.findFragmentById(R.id.footer_container) as? FooterFragment)?.updateTotal(updatedTotal)

            binding.nameEditText.text?.clear()
            binding.amountEditText.text?.clear()
            binding.dateEditText.text?.clear()

            Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
        }

        binding.btnFinancialTips.setOnClickListener {
            val url = "https://www.canada.ca/en/services/finance.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy called")
    }
}
