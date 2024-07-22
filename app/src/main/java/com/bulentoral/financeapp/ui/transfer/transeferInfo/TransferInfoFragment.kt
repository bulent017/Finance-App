package com.bulentoral.financeapp.ui.transfer.transeferInfo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentTransferInfoBinding
import com.google.firebase.firestore.FirebaseFirestore

class TransferInfoFragment : BaseFragment<FragmentTransferInfoBinding, TransferInfoViewModel>(
    FragmentTransferInfoBinding::inflate
){
    private lateinit var viewModelFactory: TransactionInfoViewModelFactory
    override val viewModel: TransferInfoViewModel by viewModels { viewModelFactory }
    private lateinit var ibanInfo: String
    private lateinit var accountTransactionAdapter: AccountTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()

        val repository = TransferInfoRepository(firestore)

        viewModelFactory = TransactionInfoViewModelFactory(repository)

        accountTransactionAdapter = AccountTransactionAdapter(emptyList())
    }

    override fun onCreateFinished() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountTransactionAdapter
        }

        arguments?.let {
            val iban = TransferInfoFragmentArgs.fromBundle(it).ibanInfo
            ibanInfo = iban
            Log.d("TransferInfo", ibanInfo)
        }
    }




        override fun initializeListeners() {
            binding.sentButton.setOnClickListener {
                val selectedPosition = accountTransactionAdapter.selectedPosition
                if (selectedPosition != -1) {
                    val selectedAccount = accountTransactionAdapter.bankAccounts[selectedPosition]
                    val amount = binding.amountEditText.text.toString().toDoubleOrNull()
                    val note = binding.descriptionEditText.text.toString()

                    if (amount != null && amount > 0) {
                        viewModel.transferAmount(selectedAccount, ibanInfo, amount, note)
                    } else {
                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please select an account", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun observeEvents() {
        viewModel.bankAccounts.observe(viewLifecycleOwner, Observer { bankAccounts ->
            accountTransactionAdapter.updateBankAccounts(bankAccounts)
        })
        viewModel.transactionResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                Toast.makeText(context, "Transaction successful", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
