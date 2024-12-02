package br.com.chapada.Authentication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.chapada.databinding.ActivityNewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class New : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding
    private val Authentication = FirebaseAuth.getInstance()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BotaoCadastrar.setOnClickListener { View ->
            val Email = binding.EmailImput.text.toString()
            val Password = binding.PasswordImput.text.toString()
            if (Email.isEmpty() || Password.isEmpty()) {
                val snackbar = Snackbar.make(View, "Digite seus dados", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                Authentication.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener { sucesso ->
                        if (sucesso.isSuccessful) {
                            val snackbar =
                                Snackbar.make(View, "Cadastrado com sucesso", Snackbar.LENGTH_SHORT)
                            snackbar.show()
                            binding.EmailImput.setText("")
                            binding.PasswordImput.setText("")
                        }
                    }.addOnFailureListener { Exceptions ->
                        val OutrosErros = when (Exceptions) {
                            is FirebaseAuthWeakPasswordException -> "Digite uma senha com mais de 5 digitos."
                            is FirebaseAuthInvalidCredentialsException -> "Digite um email válido."
                            is FirebaseAuthUserCollisionException -> """
                            Email já cadastrado.
                            Volte para a tela de login.""".trimIndent()

                            is FirebaseNetworkException -> "Você está sem internet."
                            else -> "Erro ao cadastrar."
                        }
                        val snackbar = Snackbar.make(View, OutrosErros, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }
}