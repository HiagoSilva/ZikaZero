package com.projeto.zikazero;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.projeto.zikazero.Dao.RepositorioPublicacoes;
import com.projeto.zikazero.Dao.entidade.Publicacao;
import com.projeto.zikazero.database.ConnectionManager;
import com.projeto.zikazero.util.PostsAdapter;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    SQLiteDatabase conn;

    private ListView lstPublicacaoes;
    private PostsAdapter postsAdapter;
    ArrayList<Publicacao> publicacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        lstPublicacaoes = (ListView) findViewById(R.id.lstPublicacaoes);

        try{
            //PEGAR A INSTANCIA DO GERENCIADOR DE CONEXAO
            conn = ConnectionManager.getConexao(this);

            //INSTANCIAR UM A CLASE REPOSITORIO
            RepositorioPublicacoes publicacoes = new RepositorioPublicacoes(conn);

            //RETORNAA UMA LISTA DE PUBLICACOES CADASTRADAS NO BANCO
            this.publicacoes = publicacoes.listaPublicacoes();

            postsAdapter = new PostsAdapter(getApplication(), R.layout.post_adapter, this.publicacoes);
            lstPublicacaoes.setAdapter(postsAdapter);

        } catch (SQLException ex){
            //MOSTRA UMA MENSAGEM INFORMANDO QUE O BANCO NAO FOI CRIADO
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao carregar alista de Posts: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }
}
