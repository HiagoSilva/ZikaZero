package com.projeto.zikazero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etSenha;
    private TextView tvEsqueciSenha;
    private Button bLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario       = (EditText) findViewById(R.id.etNome);
        etSenha         = (EditText) findViewById(R.id.etSenha);
        tvEsqueciSenha  = (TextView) findViewById(R.id.tv_esqueci_minha_senha);
        bLogar          = (Button) findViewById(R.id.btCadastrar);

    }
}
