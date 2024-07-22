package com.bulentoral.financeapp.ui.transfer.transeferInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bulentoral.financeapp.databinding.ItemViewAccountTransactionBinding
import com.bulentoral.financeapp.model.BankAccount

class AccountTransactionAdapter(
    var bankAccounts: List<BankAccount>,
    var selectedPosition: Int = -1 
) : RecyclerView.Adapter<AccountTransactionAdapter.BankAccountViewHolder>() {

    inner class BankAccountViewHolder(val binding: ItemViewAccountTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bankAccount: BankAccount, position: Int) {
            binding.bankName.text = bankAccount.bankName
            binding.ibanInfoText.text = bankAccount.iban
            binding.balanceInfoText.text = "${bankAccount.accountBalance} TL"

            binding.radioButton.isChecked = (position == selectedPosition)

            binding.root.setOnClickListener {
                if (selectedPosition != adapterPosition) {
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = adapterPosition

                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }

            binding.radioButton.setOnClickListener {
                if (selectedPosition != adapterPosition) {
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = adapterPosition

                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding = ItemViewAccountTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankAccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        val bankAccount = bankAccounts[position]
        holder.bind(bankAccount, position)
    }

    override fun getItemCount(): Int = bankAccounts.size

    fun updateBankAccounts(newBankAccounts: List<BankAccount>) {
        bankAccounts = newBankAccounts
        notifyDataSetChanged()
    }
}
