package com.projeto.zikazero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NovoFocoActivity extends AppCompatActivity {

    private ImageView imgFoco;
    private EditText descricaoLugar;
    private TextView localizacaoAuto;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_foco);

        imgFoco = (ImageView) findViewById(R.id.iv_foto);
        descricaoLugar = (EditText) findViewById(R.id.et_descricao);
        localizacaoAuto = (TextView) findViewById(R.id.tv_localizacao_auto);
    }

    public void tirarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFoco.setImageBitmap(imageBitmap);
        }
    }
}
