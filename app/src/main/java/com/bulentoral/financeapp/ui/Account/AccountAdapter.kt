package com.bulentoral.financeapp.ui.Account


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bulentoral.financeapp.databinding.ItemViewAccountBinding
import com.bulentoral.financeapp.model.BankAccount

class BankAccountAdapter(
    private val bankAccounts: List<BankAccount>
) : RecyclerView.Adapter<BankAccountAdapter.BankAccountViewHolder>() {

    inner class BankAccountViewHolder(val binding: ItemViewAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bankAccount: BankAccount) {
            binding.bankName.text = bankAccount.bankName
            binding.ibanInfoText.text = bankAccount.iban
            binding.balanceInfoText.text = "${bankAccount.accountBalance} TL"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding = ItemViewAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankAccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        val bankAccount = bankAccounts[position]
        holder.bind(bankAccount)
    }

    override fun getItemCount(): Int = bankAccounts.size
}
