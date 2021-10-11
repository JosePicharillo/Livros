package br.edu.ifsp.scl.sdm.livros.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.sdm.livros.R
import br.edu.ifsp.scl.sdm.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.scl.sdm.livros.model.Livro

class LivrosAdapter(
    val contexto: Context,
    val listLivros: MutableList<Livro>
) : ArrayAdapter<Livro>(contexto, R.layout.layout_livro, listLivros) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val livroLayout: View = if (convertView != null) {
            //View já existe
            convertView
        } else {
            //View precisa ser inflada
            val layoutLivroBinding = LayoutLivroBinding.inflate(
                (contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater),
                parent,
                false
            )

            with(layoutLivroBinding) {
                root.tag = LivroLayoutHolder(
                    layoutLivroBinding.textViewTitulo,
                    layoutLivroBinding.textViewAutor,
                    layoutLivroBinding.textViewEditora
                )
            }
//            val livroLayoutHolder = LivroLayoutHolder(
//                layoutLivroBinding.textViewTitulo,
//                layoutLivroBinding.textViewAutor,
//                layoutLivroBinding.textViewEditora
//            )
//
//            layoutLivroBinding.root.tag = livroLayoutHolder
            layoutLivroBinding.root
        }

        //Preencher ou atualizar a View
        val livro: Livro = listLivros[position]
        val holder = livroLayout.tag as LivroLayoutHolder
        holder.titulo.text = livro.titulo
        holder.autor.text = livro.autor
        holder.editora.text = livro.editora

        return livroLayout
    }

    private data class LivroLayoutHolder(
        val titulo: TextView,
        val autor: TextView,
        val editora: TextView
    )


}