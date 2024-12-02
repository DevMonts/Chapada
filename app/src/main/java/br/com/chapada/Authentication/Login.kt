package br.com.chapada.Authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.chapada.MainActivity
import br.com.chapada.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginButton.setOnClickListener { View ->
            val Email = binding.EmailImput.text.toString()
            val Senha = binding.PasswordImput.text.toString()
            if (Email.isEmpty() || Senha.isEmpty()) {
                val snackbar = Snackbar.make(View, "Digite seus dados", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(Email, Senha).addOnCompleteListener { SingIn ->
                    if (SingIn.isSuccessful) {
                        val Logar = Intent(this, MainActivity::class.java)
                        startActivity(Logar)
                    }
                }.addOnFailureListener { Exceptions ->
                    val Errors = when (Exceptions) {
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido."
                        is FirebaseNetworkException -> "Você está sem internet."
                        else -> "Erro"
                    }
                    val snackbar = Snackbar.make(View, Errors, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.NewIdButton.setOnClickListener {
            val GoToNewID = Intent(this, New::class.java)
            startActivity(GoToNewID)

        }
    }

    override fun onStart() {
        super.onStart()
        val Logged = FirebaseAuth.getInstance().currentUser
        if (Logged != null) {
            val Log = Intent(this, MainActivity::class.java)
            startActivity(Log)
        }
    }
}