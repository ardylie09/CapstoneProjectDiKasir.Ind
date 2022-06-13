package com.dicoding.dikasirind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.dicoding.dikasirind.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var binding: ActivityLogin2Binding
    lateinit var  fireAt: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        fireAt = Firebase.auth

        setAction()
    }

    private fun setAction(){
        binding.textSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity :: class.java))
            onPause()
        }
        binding.btnLogin.setOnClickListener {
            konfirmasiInfo()
        }
    }

    private fun konfirmasiInfo(){
        val ikon = AppCompatResources.getDrawable(this,R.drawable.ic_baseline_warning_24)
        ikon?.setBounds(0,0,ikon.intrinsicWidth,ikon.intrinsicHeight)
        when{
            TextUtils.isEmpty(email.text.toString().trim())->
            {
                email.setError("Wajib Diisi", ikon)
            }
            TextUtils.isEmpty(password.text.toString().trim())->
            {
                password.setError("Wajib Diisi", ikon)
            }
            email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty()->
            {
                if(email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    cloudSign()
                }else{

                }
            }
        }
    }
    private fun cloudSign(){
        binding.btnLogin.isEnabled = false
        binding.btnLogin.alpha = 0.5f
        fireAt.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener {
                task->if(task.isSuccessful){
            startActivity(Intent(this, HomeActivity :: class.java))
            onDestroy()
        }else{
            binding.btnLogin.isEnabled = true
            binding.btnLogin.alpha = 1.0f
            Toast.makeText(this,task.exception?.message, Toast.LENGTH_SHORT).show()
        }
        }
    }
}