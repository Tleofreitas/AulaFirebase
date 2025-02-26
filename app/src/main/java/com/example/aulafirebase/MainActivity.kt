package com.example.aulafirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aulafirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }
    private val bancoDados by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onStart() {
        super.onStart()
        // verificarUsuarioLogado()
    }
    private fun verificarUsuarioLogado() {

        // Deslogar usuário
        // autenticacao.signOut()

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
            // cadastrarUsuario()
            // logarUsuario()
            // salvarDados()

            atualizarRemoverDados()
        }
    }

    private fun atualizarRemoverDados() {
        val dados = mapOf(
            "nome" to "Thiago",
            "idade" to "29"
        )

        val referenciaUsuario = bancoDados.collection("usuarios")
            .document("1")

        // Substituir
        // referenciaUsuario.set(dados)

        // Atualizar
        /*
        referenciaUsuario.update("nome", "Thiago Freitas")
            .addOnSuccessListener { exibirMensagem("Usuario Atualizado com sucesso") }
            .addOnFailureListener{ exception ->
                exibirMensagem("Erro ao atualizar usuário")
            }
         */

        // Deletar
        /*
        referenciaUsuario.delete()
            .addOnSuccessListener { exibirMensagem("Usuario Deletado com sucesso") }
            .addOnFailureListener{ exception ->
                exibirMensagem("Erro ao Deletar usuário")
            }
         */
    }

    private fun salvarDados() {

        val dados = mapOf(
            "nome" to "Thiago",
            "idade" to "30"
        )

        bancoDados.collection("usuarios")
            .document("1")
            .set(dados)
            .addOnSuccessListener { exibirMensagem("Usuario salvo com sucesso") }
            .addOnFailureListener{ exception ->
                exibirMensagem("Erro ao salvar usuário")
            }
    }

    private fun logarUsuario() {
        val email = "thiagoleonardo1995@live.com"
        // 6 caracteres e precisa ser forte
        val senha = "Tleo1607"

        autenticacao.signInWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener { authResult ->
            binding.textResultado.text = "Sucesso ao logar usuário"
            startActivity(
                Intent(this, PrincipalActivity::class.java)
            )
        }.addOnFailureListener { exception ->
            binding.textResultado.text = "Falha ao logar usuário ${exception.message}"
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