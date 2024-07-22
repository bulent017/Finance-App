package com.bulentoral.financeapp.ui.Auth.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bulentoral.financeapp.ui.MainActivity
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentLoginBinding
import com.bulentoral.financeapp.utils.NavigationUtils.navigateToActivity
import com.bulentoral.financeapp.utils.NavigationUtils.navigateToFragment
import com.bulentoral.financeapp.utils.ValidationUtils.showToast
import com.bulentoral.financeapp.utils.ValidationUtils.validateFields


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()

    override fun onCreateFinished() {

        binding.progressBarLogin.visibility = View.GONE
    }

    override fun initializeListeners() {
        binding.signUpButton.setOnClickListener {
            navigateToFragment(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.loginButton.setOnClickListener {
            val email = binding.userEmailEditText.text.toString()
            val password = binding.userPasswordEditText.text.toString()
            if (validateFields(
                    Pair(email, "Email"),
                    Pair(password, "Password")
                )
            ) {
                viewModel.loginUser(email, password)
            } else {
                showToast(requireContext(), "Please fill in all fields.")
            }

        }
        binding.rememberMeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.rememberMe(requireContext(), isChecked)
        }



    }

    override fun observeEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.loginButton.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            val (success, message) = result
            if (success) {
            showToast(requireActivity(),"Login successful")
                requireActivity().navigateToActivity(MainActivity::class.java)

            } else {
                showToast(requireActivity(), message ?: "An error occurred")
            }
        })
    }
}