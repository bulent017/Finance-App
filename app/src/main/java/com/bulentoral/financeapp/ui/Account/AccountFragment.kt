package com.bulentoral.financeapp.ui.Account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bulentoral.financeapp.R
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentAccountBinding
import com.google.firebase.firestore.FirebaseFirestore


class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>(
    FragmentAccountBinding::inflate
) {
    private lateinit var viewModelFactory: AccountViewModelFactory
    override val viewModel: AccountViewModel by viewModels { viewModelFactory }

    private lateinit var bankAccountAdapter: BankAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase Firestore instance
        val firestore = FirebaseFirestore.getInstance()

        // Create repository
        val repository = AccountRepository(firestore)

        // Create ViewModelFactory
        viewModelFactory = AccountViewModelFactory(repository)

        // Initialize adapter
        bankAccountAdapter = BankAccountAdapter(emptyList())
    }

    override fun onCreateFinished() {
        binding.recyclerviewAccount.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bankAccountAdapter
        }


        binding.progressBarAccount.visibility = View.GONE
    }

    override fun initializeListeners() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_add_account -> {
                    findNavController().navigate(R.id.action_accountFragment_to_addAccountFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun observeEvents() {
        viewModel.bankAccounts.observe(viewLifecycleOwner, Observer { bankAccounts ->
            bankAccountAdapter = BankAccountAdapter(bankAccounts)
            binding.recyclerviewAccount.adapter = bankAccountAdapter
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBarAccount.visibility = View.VISIBLE
                binding.recyclerviewAccount.visibility = View.GONE
            } else {
                binding.progressBarAccount.visibility = View.GONE
                binding.recyclerviewAccount.visibility = View.VISIBLE
            }
        })
        viewModel.totalBalance.observe(viewLifecycleOwner, Observer { totalBalance ->
            binding.balanceInfoText.text = "$totalBalance TL"
        })
    }
}