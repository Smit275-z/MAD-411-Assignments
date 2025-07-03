package com.example.assignment6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val expenses: MutableList<Expense>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.expenseName)
        val amount: TextView = itemView.findViewById(R.id.expenseAmount)
        val date: TextView = itemView.findViewById(R.id.expenseDate)
        val deleteBtn: Button = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.name.text = expense.name
        holder.amount.text = "$%.2f".format(expense.amount)
        holder.date.text = expense.date ?: "No date"
        holder.deleteBtn.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onDeleteClick(currentPosition)
            }
        }

    }

    override fun getItemCount(): Int = expenses.size
}
