package com.projeto.zikazero;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.projeto.zikazero.database.ConnectionManager;
import com.projeto.zikazero.database.DataBase;

public class MainActivity extends AppCompatActivity{

    private Button btNovoFoco;
    private Button btMapaFocos;
    private Button btPosts;
    private Button btEstatistica;
    private Button btInformacoes;
    private Button btNoticias;

    //BANCO DE DADOS
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNovoFoco      = (Button) findViewById(R.id.b_novo_foco);
        btMapaFocos     = (Button) findViewById(R.id.b_mapa_focos);
        btPosts         = (Button) findViewById(R.id.b_posts);
        btEstatistica   = (Button) findViewById(R.id.b_estatistica);
        btInformacoes   = (Button) findViewById(R.id.b_informaoes);
        btNoticias      = (Button) findViewById(R.id.b_noticias);

        try {

            //BUSCA A INSTANCIA JÁ CRIADO PARA O BANCO
            conn = ConnectionManager.getConexao(this);

            //MOSTRA UMA MENSAGEM INFORMANDO QUE O BANCO FOI CRIADO
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Conexão criada com sucesso!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        } catch (SQLException ex){

            //MOSTRA UMA MENSAGEM INFORMANDO QUE O BANCO NAO FOI CRIADO
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco: !" + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        btNovoFoco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, NovoFocoActivity.class);
                startActivity(it);
            }
        });

        btPosts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, PostsActivity.class);
                startActivity(it);
            }
        });

        btMapaFocos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, MapaFocosActivity.class);
                startActivity(it);
            }
        });

        btEstatistica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(it);
            }
        });


    }



}
