package br.edu.ifsp.scl.sdm.livros.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Livro(
    val titulo: String,
    val isbn: String,
    val autor: String,
    val editora: String,
    val edicao: Int,
    val paginas: Int
): Parcelable