package br.com.chapada.Autenticação

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.chapada.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Cadastro : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private val Authentication = FirebaseAuth.getInstance()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BotaoCadastrar.setOnClickListener { View ->
            val Email = binding.ImputEmail.text.toString()
            val Senha = binding.ImputSenha.text.toString()
            if (Email.isEmpty() || Senha.isEmpty()) {
                val snackbar = Snackbar.make(View, "Digite seus dados", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                Authentication.createUserWithEmailAndPassword(Email, Senha)
                    .addOnCompleteListener { sucesso ->
                        if (sucesso.isSuccessful) {
                            val snackbar =
                                Snackbar.make(View, "Cadastrado com sucesso", Snackbar.LENGTH_SHORT)
                            snackbar.show()
                            binding.ImputEmail.setText("")
                            binding.ImputSenha.setText("")
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