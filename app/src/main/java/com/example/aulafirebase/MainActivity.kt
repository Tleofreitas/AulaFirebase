package com.example.aulafirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aulafirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }
    private fun verificarUsuarioLogado() {
        val usuario = autenticacao.currentUser
        val id = usuario?.uid

        if (usuario != null ) {
            exibirMensagem("Usuario está logado com id: $id")
            startActivity(
                Intent(this, PrincipalActivity::class.java)
            )
        } else {
            exibirMensagem("Não tem usuario logado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(binding.root)
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        binding.btnExecutar.setOnClickListener {
            cadastrarUsuario()
        }
    }

    private fun cadastrarUsuario() {
        val email = "thiagoleonardo1995@live.com"
        // 6 caracteres e precisa ser forte
        val senha = "Tleo1607"

        autenticacao.createUserWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener { authResult ->

            val usuario =  authResult.user?.email
            val id =  authResult.user?.uid

            exibirMensagem("Sucesso ao cadastrar usuario $id - $email")
            binding.textResultado.text = "Sucesso $id - $email"
        }.addOnFailureListener { exception ->
            val mensagemErro = exception.message
            binding.textResultado.text = "Erro $mensagemErro"
        }
    }

    private fun exibirMensagem(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show()
    }
}