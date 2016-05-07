package com.projeto.zikazero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{

    private Button btNovoFoco;
    private Button btMapaFocos;
    private Button btPosts;
    private Button btEstatistica;
    private Button btInformacoes;
    private Button btNoticias;

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

        btNovoFoco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, NovoFocoActivity.class);
                startActivity(it);
            }
        });
    }



}
