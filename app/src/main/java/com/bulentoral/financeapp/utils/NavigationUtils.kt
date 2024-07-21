package com.bulentoral.financeapp.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

object NavigationUtils {
    // Activity'ler arası geçiş için fonksiyon
    fun <T : AppCompatActivity> Context.navigateToActivity(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
    }

    // Fragmentlar arası geçiş için fonksiyon
    fun navigateToFragment(fragment: Fragment, destinationId: Int) {
        fragment.findNavController().navigate(destinationId)
    }

    //Argümanlar ile veri taşınacaksa bunu kullan, method overloading
    fun Fragment.navigateToFragment(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    fun navigateToFragment(fragment: Fragment?, directions: Int, bundle: Bundle?) {
        // Eğer bir Bundle varsa, NavController'e bu Bundle'ı ekleyerek navigasyon yapın
        if (bundle != null) {
            NavHostFragment.findNavController(fragment!!).navigate(directions, bundle)
        } else {
            NavHostFragment.findNavController(fragment!!).navigate(directions)
        }
    }

    fun navigateToFragmentFromAdapter(view: View, directions: Int, bundle: Bundle?) {
        val navController = Navigation.findNavController(view)
        if (bundle != null) {
            navController.navigate(directions, bundle)
        } else {
            navController.navigate(directions)
        }
    }

    fun navigateAndClearBackStack(navController: NavController, actionId: Int, args: Bundle? = null, destinationId: Int? = null, inclusive: Boolean = true) {
        val navOptions = NavOptions.Builder().apply {
            setLaunchSingleTop(true)  // Aynı destination varsa yeniden başlatmaz
            setPopUpTo(destinationId ?: navController.graph.startDestinationId, inclusive)
        }.build()

        navController.navigate(actionId, args, navOptions)
    }

    fun handleNavigateBack(fragment: Fragment, actionId:Int, viewLifecycleOwner: LifecycleOwner, fragmentActivity: FragmentActivity) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    navigateToFragment(
                        fragment,
                        actionId
                    )
                }
            }
        fragmentActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    fun replaceFragmentAndClearBackStack(fragmentManager: FragmentManager, newFragment: Fragment, containerId: Int, args: Bundle) {
        // Önceki tüm fragment'leri stack'ten kaldır
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        // Yeni fragment'a argümanları ekle
        newFragment.arguments = args

        // Yeni fragment'ı transaction ile ekle
        fragmentManager.beginTransaction().apply {
            replace(containerId, newFragment)
            commit()
        }
    }



}