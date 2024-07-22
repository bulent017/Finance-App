package com.bulentoral.financeapp.ui.AddAccount

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentAddAccountBinding
import com.bulentoral.financeapp.model.BankAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddAccountFragment : BaseFragment<FragmentAddAccountBinding, AddAccountViewModel>(
    FragmentAddAccountBinding::inflate
) {
    private lateinit var viewModelFactory: AddAccountViewModelFactory
    override val viewModel: AddAccountViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()

        val repository = AddAccountRepository(firestore)

        viewModelFactory = AddAccountViewModelFactory(repository)
    }

    override fun onCreateFinished() {
        // Başlatma işlemleri burada yapılabilir
        binding.progressBarAddAccount.visibility= View.GONE
    }

    override fun initializeListeners() {
        binding.addButton.setOnClickListener {
            val bankName = binding.bankEditText.text.toString()
            val iban = "TR"+binding.ibanEditText.text.toString()
            val accountBalance = binding.amountEditText.text.toString().toLongOrNull()
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (bankName.isNotEmpty() && iban.isNotEmpty() && accountBalance != null && userId != null) {
                val bankAccount = BankAccount(
                    userId = userId,
                    bankName = bankName,
                    iban = iban,
                    accountBalance = accountBalance
                )
                viewModel.saveAccount(bankAccount)
            } else {
                Toast.makeText(context, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun observeEvents() {
        viewModel.uploadProgress.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                // Show progress bar
                binding.progressBarAddAccount.visibility = View.VISIBLE
                binding.addButton.visibility = View.GONE
            } else {
                // Hide progress bar
                binding.progressBarAddAccount.visibility = View.GONE
                binding.addButton.visibility = View.VISIBLE
            }
        })

        viewModel.uploadError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                // Show error message
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
