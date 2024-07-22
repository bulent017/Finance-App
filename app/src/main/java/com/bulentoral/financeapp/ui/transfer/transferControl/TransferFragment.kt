package com.bulentoral.financeapp.ui.transfer.transferControl

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentTransferBinding
import com.bulentoral.financeapp.utils.NavigationUtils
import com.bulentoral.financeapp.utils.NavigationUtils.navigateToFragment

class TransferFragment : BaseFragment<FragmentTransferBinding, TransferViewModel>(
    FragmentTransferBinding::inflate
) {
    override val viewModel by viewModels<TransferViewModel>()
    private lateinit var iban: String
    override fun onCreateFinished() {
    }

    override fun initializeListeners() {
        binding.ibanEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length ==2) {
                    iban = s.toString()
                    viewModel.searchIban(s.toString())
                }
            }
        })
        binding.continueButton.setOnClickListener {
            val action = TransferFragmentDirections.actionTransferFragmentToTransferInfoFragment(iban)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun observeEvents() {
        viewModel.userFullName.observe(viewLifecycleOwner) { fullName ->
            binding.fullNameEditText.setText(fullName)


        }
    }
}