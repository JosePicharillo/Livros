package br.edu.ifsp.scl.sdm.livros

/**
 *  Interface que será implementada na Activity para tratar eventos de clique e
 *  será usada no Adapter para tratar eventos de clique nas células da RecyclerView.
 */
interface OnLivroClickListener {

    fun onLivroClick(posicao: Int)

}