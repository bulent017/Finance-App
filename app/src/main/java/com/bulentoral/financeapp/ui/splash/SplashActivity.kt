package com.bulentoral.financeapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bulentoral.financeapp.ui.MainActivity
import com.bulentoral.financeapp.R
import com.bulentoral.financeapp.ui.Auth.AuthActivity
import com.bulentoral.financeapp.ui.Auth.AuthRepository
import com.bulentoral.financeapp.utils.NavigationUtils.navigateToActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private val authRepository = AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler(Looper.getMainLooper()).postDelayed({
            val isRemembered = authRepository.isRemembered(this)
            if (isRemembered) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    navigateToActivity(MainActivity::class.java)
                } else {
                    navigateToActivity(AuthActivity::class.java)
                }
            } else {
                navigateToActivity(AuthActivity::class.java)
            }
            finish()
        }, 2000)
    }
}