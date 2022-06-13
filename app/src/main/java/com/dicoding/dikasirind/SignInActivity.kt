package com.dicoding.dikasirind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.dicoding.dikasirind.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var konfirmasi: EditText
    lateinit var binding: ActivitySignInBinding
    lateinit var fireAt: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = findViewById(R.id.email_SI)
        password = findViewById(R.id.password_SI)
        konfirmasi = findViewById(R.id.konfirm_pass)
        fireAt = Firebase.auth

        setAction()
    }

    private fun setAction(){
        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity :: class.java))
            onPause()
        }
        binding.btnSI.setOnClickListener {
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
            TextUtils.isEmpty(konfirmasi.text.toString().trim())->
            {
                konfirmasi.setError("Wajib Diisi", ikon)
            }
            email.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    konfirmasi.text.toString().isNotEmpty()->
            {
                if(email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    if(password.text.toString()==konfirmasi.text.toString()){
                        cloudSign()
                    }else{
                        konfirmasi.setError("Password Tidak Cocok", ikon)
                    }
                }else{
                    email.setError("Tolong Masukan Email Yang Valid")
                }
            }
        }
    }
    private fun cloudSign(){
        binding.btnSI.isEnabled=false
        binding.btnSI.alpha=0.5f
        fireAt.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                task->if(task.isSuccessful){
            startActivity(Intent(this, LoginActivity :: class.java))
            onDestroy()
        }else{
            binding.btnSI.isEnabled=true
            binding.btnSI.alpha=1.0f
            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
        }
        }
    }
}