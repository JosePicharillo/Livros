package br.edu.ifsp.scl.sdm.livros

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.livros.adapter.LivrosAdapter
import br.edu.ifsp.scl.sdm.livros.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.livros.model.Livro

class MainActivity : AppCompatActivity() {

    companion object Extras {
        const val EXTRA_LIVRO = "EXTRA_LIVRO"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
    private val livrosAdapter: LivrosAdapter by lazy {
        LivrosAdapter(this, livrosList)
    }

    // Activity Result Launchers
    private lateinit var livroActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarLivroActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        //Inicializa lista de livros
        inicializarLivrosList()

        activityMainBinding.livrosLv.adapter = livrosAdapter

        livroActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
                if (resultado.resultCode == RESULT_OK) {
                    resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                        livrosList.add(this)
                        livrosAdapter.notifyDataSetChanged()
                    }
                }
            }

        editarLivroActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
                if (resultado.resultCode == RESULT_OK) {
                    val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO, -1)
                    resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                        if (posicao != -1 && posicao != null) {
                            livrosList[posicao] = this
                            livrosAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        activityMainBinding.livrosLv.setOnItemClickListener { _, _, posicao, _ ->
            val livro = livrosList[posicao]
            val consultarLivroIntent = Intent(this, LivroActivity::class.java)
            consultarLivroIntent.putExtra(EXTRA_LIVRO, livro)
            startActivity(consultarLivroIntent)
        }

        // Associa ListView com o menu de contexto
        registerForContextMenu(activityMainBinding.livrosLv)

        //Tratando evento de clique no Fab
        activityMainBinding.adicionarLivroFab.setOnClickListener {
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
        }
    }

    //Exibir Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //Selecionar opções do menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sairMi -> {
            finish()
            true
        }
        else -> {
            false
        }
    }

    //Usar Menu de Contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    //Selecionar opções do menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when (item.itemId) {
            R.id.editarLivroMi -> {
                //Editar livro
                val livro = livrosList[posicao]
                val editarLivroIntent = Intent(this, LivroActivity::class.java)
                editarLivroIntent.putExtra(EXTRA_LIVRO, livro)
                editarLivroIntent.putExtra(EXTRA_POSICAO, posicao)
                editarLivroActivityResultLauncher.launch(editarLivroIntent)
                true
            }
            R.id.removerLivroMi -> {
                //Remover livro
                livrosList.removeAt(posicao)
                livrosAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun inicializarLivrosList() {
        for (indice in 1..5) {
            livrosList.add(
                Livro(
                    "Título $indice",
                    "ISBN $indice",
                    "Autor $indice",
                    "Editora $indice",
                    indice,
                    indice
                )
            )
        }
    }
}