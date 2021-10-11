package br.edu.ifsp.scl.sdm.livros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.edu.ifsp.scl.sdm.livros.databinding.ActivityLivroBinding;
import br.edu.ifsp.scl.sdm.livros.model.Livro;

public class LivroActivity extends AppCompatActivity {

    private ActivityLivroBinding activityLivroBinding;
    private Livro livro;
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLivroBinding = ActivityLivroBinding.inflate(getLayoutInflater());

        setContentView(activityLivroBinding.getRoot());

        activityLivroBinding.salvarBt.setOnClickListener((View view) -> {
            Livro livro = new Livro(
                    activityLivroBinding.tituloEt.getText().toString(),
                    activityLivroBinding.isbnET.getText().toString(),
                    activityLivroBinding.autorET.getText().toString(),
                    activityLivroBinding.editoraET.getText().toString(),
                    Integer.parseInt(activityLivroBinding.edicaoET.getText().toString()),
                    Integer.parseInt(activityLivroBinding.paginasET.getText().toString())
            );

            Intent resultadoIntent = new Intent();
            resultadoIntent.putExtra(MainActivity.EXTRA_LIVRO, livro);

            if (posicao != -1){
                resultadoIntent.putExtra(MainActivity.EXTRA_POSICAO, posicao);
            }

            setResult(RESULT_OK, resultadoIntent);
            finish();
        });

        //Verifica se a ação é uma edição
        livro = getIntent().getParcelableExtra(MainActivity.EXTRA_LIVRO);
        posicao = getIntent().getIntExtra(MainActivity.EXTRA_POSICAO, -1);
        if (livro != null){
            activityLivroBinding.tituloEt.setText(livro.getTitulo());
            activityLivroBinding.autorET.setText(livro.getAutor());
            activityLivroBinding.isbnET.setText(livro.getIsbn());
            activityLivroBinding.editoraET.setText(livro.getEditora());
            activityLivroBinding.edicaoET.setText(String.valueOf(livro.getEdicao()));
            activityLivroBinding.paginasET.setText(String.valueOf(livro.getPaginas()));

            //Não deixar alterar os campos de livros, caso a ação seja de visualizar
            if (posicao == -1){
                for (int i=0; i < activityLivroBinding.getRoot().getChildCount(); i++){
                    activityLivroBinding.getRoot().getChildAt(i).setEnabled(false);
                }
                activityLivroBinding.salvarBt.setVisibility(View.GONE);
            }
        }
    }
}