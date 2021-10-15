package br.edu.ifsp.scl.sdm.livros.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.livros.OnLivroClickListener
import br.edu.ifsp.scl.sdm.livros.R
import br.edu.ifsp.scl.sdm.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.scl.sdm.livros.model.Livro

class LivrosRvAdapter(
    private val onLivroClickListener: OnLivroClickListener,
    private val livrosList: MutableList<Livro>
) : RecyclerView.Adapter<LivrosRvAdapter.LivroLayoutHolder>() {

    // View Holder
    inner class LivroLayoutHolder(layoutLivroBinding: LayoutLivroBinding) :
        RecyclerView.ViewHolder(layoutLivroBinding.root), View.OnCreateContextMenuListener {
        val tituloTv: TextView = layoutLivroBinding.textViewTitulo
        val autorTv: TextView = layoutLivroBinding.textViewAutor
        val editoraTv: TextView = layoutLivroBinding.textViewEditora

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        //Menu Contexto RecyclerView
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_main, menu)
        }
    }

    // Chamada pelo layoutManager quando uma nova célula precisa ser criada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroLayoutHolder {
        // Cria uma nova célula a partir da nossa classe de viewBinding
        val layoutLivroBinding =
            LayoutLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Cria um viewHolder e associa a raiz da instância da claase de viewBinding (célula)
        return LivroLayoutHolder(layoutLivroBinding)
    }

    // Chamado pelo LayoutManager para alterar o contéudo de uma célula
    override fun onBindViewHolder(holder: LivroLayoutHolder, position: Int) {
        // Busca o livro
        val livro = livrosList[position]

        // Altera os valores das views do viewHolder
        with(holder) {
            tituloTv.text = livro.titulo
            autorTv.text = livro.autor
            editoraTv.text = livro.editora
        }

        /* Seta o onClickListener da célula que esta associada ao viewHolder
           com uma lambda que chama uma função na MainActivity (Clique Curto) */
        holder.itemView.setOnClickListener {
            onLivroClickListener.onLivroClick(position)
        }

        // Clique Longo
        holder.itemView.setOnLongClickListener{
            posicao = position
            false
        }
    }

    // Retorna a quantidade de item na minha lista
    override fun getItemCount(): Int = livrosList.size

    // Posição a ser recuperada no menu de contexto
    var posicao: Int = -1
}