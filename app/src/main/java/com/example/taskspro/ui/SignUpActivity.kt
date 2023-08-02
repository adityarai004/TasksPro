package com.example.taskspro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskspro.databinding.ActivitySignUpBinding
import com.example.taskspro.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authViewModel:AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.signupBtn.setOnClickListener {
            authViewModel.viewModelScope.launch(Dispatchers.IO){
                if(authViewModel.signUp(binding.emailSignupEt.text.toString(),binding.passwordSignupEt.text.toString())){
                    authViewModel.login(binding.emailSignupEt.text.toString(),binding.passwordSignupEt.text.toString())
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
                else{
                    authViewModel.login(binding.emailSignupEt.text.toString(),binding.passwordSignupEt.text.toString())
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
            }

        }
    }
}