package com.bulentoral.financeapp.ui.Auth.register

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bulentoral.financeapp.base.BaseFragment
import com.bulentoral.financeapp.databinding.FragmentRegisterBinding
import com.bulentoral.financeapp.model.User
import com.bulentoral.financeapp.utils.NavigationUtils.navigateToFragment
import com.bulentoral.financeapp.utils.ValidationUtils.showToast
import com.bulentoral.financeapp.utils.ValidationUtils.validateFields


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate
) {


    override val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateFinished() {
        binding.progressBar.visibility = View.GONE
    }

    override fun initializeListeners() {
        binding.signUpButton.setOnClickListener {
            val email = binding.userEmailEditText.text.toString()
            val password = binding.userPasswordEditText.text.toString()
            val userName = binding.userNameEditText.text.toString()
            val phoneNumber = binding.userPhoneEditText.text.toString()
            val userLastName = binding.userLastEditText.text.toString()
            if (validateFields(
                    Pair(email, "Email"),
                    Pair(password, "Password"),
                    Pair(userName, "User Name"),
                    Pair(phoneNumber, "Phone Number"),
                    Pair(userLastName, "User Last Name")
                )
            ) {
                val user = User(
                    fullName = userName,
                    lastName = userLastName,
                    email = email,
                    phoneNumber = phoneNumber
                )
                viewModel.registerUser(user, password)
            } else {
                showToast(requireContext(),"Please fill in all fields.")
            }
        }
    }

    override fun observeEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.signUpButton.visibility = if (isLoading) View.GONE else View.VISIBLE
        })

        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            val (success, message) = result
            if (success) {
                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                navigateToFragment(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            } else {
                Toast.makeText(context, "Registration failed: $message", Toast.LENGTH_SHORT).show()
            }
        })
    }



}