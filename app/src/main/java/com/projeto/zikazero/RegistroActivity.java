package com.projeto.zikazero;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projeto.zikazero.Dao.RepositorioPublicacoes;
import com.projeto.zikazero.Dao.RepositorioUsuarios;
import com.projeto.zikazero.Dao.entidade.Publicacao;
import com.projeto.zikazero.Dao.entidade.Usuario;
import com.projeto.zikazero.database.ConnectionManager;

public class RegistroActivity extends AppCompatActivity {

    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etConfirmSenha;

    Button btCadastrar;
    Button btCancelar;

    TextView tvPular;

    SQLiteDatabase conn;
    RepositorioUsuarios repUsuario;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etConfirmSenha = (EditText) findViewById(R.id.etConfirmarSenha);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        btCancelar = (Button) findViewById(R.id.btCancelar);
        tvPular = (TextView) findViewById(R.id.tvPular);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        tvPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }

    public void validarCampos(){
        if (etNome.getText().length() == 0){
            etNome.setHint("Informe seu nome");
        }
    }

    public void inserir() {
        /*try {
            //PEGA INSTANCIA PELO GERENCIADOR DE CONEXAO
            conn = ConnectionManager.getConexao(this);

            //TODO ALTERACAO QUE FIZ PARA FUNCIONAR, TENHO QUE ENTENDER DEPOIS
            repUsuario = new RepositorioUsuarios(conn);

            //INSTANCIANDO UMA PUBLICACAO
            usuario = new Usuario();

            //PASSANDO OS VALORES INFORMADOS NA TELA DE NOVO FOCO
            usuario.setNome(etNome.getText().toString());
            usuario.setEmail(etEmail.getText().toString());
            usuario.setSenha(etSenha.getText().toString());
        }*/
    }
}
