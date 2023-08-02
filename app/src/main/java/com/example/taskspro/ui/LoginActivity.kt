package com.example.taskspro.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskspro.databinding.ActivityLoginBinding
import com.example.taskspro.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            if(binding.emailLoginEt.text.toString() != "" && binding.passwordLoginEt.text.toString() != "" ){
               authViewModel.viewModelScope.launch{
                   if(authViewModel.login(binding.emailLoginEt.text.toString(),binding.passwordLoginEt.text.toString())) {
                       startActivity(Intent(applicationContext, MainActivity::class.java))
                   }
                   else{
                       Toast.makeText(applicationContext,"UserId or Password is incorrect",Toast.LENGTH_SHORT).show()
                   }
               }
            }
        }
    }
}