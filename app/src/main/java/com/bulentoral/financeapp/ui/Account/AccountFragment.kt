package com.bulentoral.financeapp.ui.Account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bulentoral.financeapp.R
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentAccountBinding
import com.bulentoral.financeapp.ui.Auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>(
    FragmentAccountBinding::inflate
) {
    private lateinit var viewModelFactory: AccountViewModelFactory
    override val viewModel: AccountViewModel by viewModels { viewModelFactory }
    private lateinit var auth: FirebaseAuth
    private lateinit var bankAccountAdapter: BankAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()

        val repository = AccountRepository(firestore)

        viewModelFactory = AccountViewModelFactory(repository)

        bankAccountAdapter = BankAccountAdapter(emptyList())
    }

    override fun onCreateFinished() {
        binding.recyclerviewAccount.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bankAccountAdapter
        }

        auth = FirebaseAuth.getInstance()
        binding.progressBarAccount.visibility = View.GONE
        viewModel.fetchBankAccounts()
    }

    override fun initializeListeners() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_add_account -> {
                    findNavController().navigate(R.id.action_accountFragment_to_addAccountFragment)
                    true
                }
                R.id.action_logout ->{
                    logoutUser()
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
    private fun logoutUser() {
        auth.signOut()
        val intent = Intent(activity, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }
}