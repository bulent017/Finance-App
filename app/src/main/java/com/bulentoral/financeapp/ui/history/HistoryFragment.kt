package com.bulentoral.financeapp.ui.history

import androidx.fragment.app.viewModels
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentHistoryBinding
import com.bulentoral.financeapp.databinding.FragmentHomeBinding
import com.bulentoral.financeapp.ui.Home.HomeViewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HomeViewModel>(
    FragmentHistoryBinding::inflate
){
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateFinished() {
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
    }

}

