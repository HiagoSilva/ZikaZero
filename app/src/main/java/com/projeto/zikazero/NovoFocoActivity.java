package com.projeto.zikazero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.projeto.zikazero.Dao.RepositorioPublicacoes;
import com.projeto.zikazero.Dao.entidade.Publicacao;
import com.projeto.zikazero.database.ConnectionManager;
import com.projeto.zikazero.util.TransformarFotoBits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class NovoFocoActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public static final String LOCATION = "location";
    public static final String TYPE = "type";
    public static final String ADDRESS = "address";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    public ImageView imgFoco;
    private EditText descricaoLugar;
    private TextView localizacaoAuto;
    private Button btEnviar;
    private Button btCanelar;

    private Bitmap bitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Boolean fotoOk = false;

    private SQLiteDatabase conn;
    private RepositorioPublicacoes publicacoes;
    private Publicacao publicacao;

    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_foco);

        imgFoco = (ImageView) findViewById(R.id.iv_foto);
        descricaoLugar = (EditText) findViewById(R.id.et_descricao);
        localizacaoAuto = (TextView) findViewById(R.id.tv_localizacao_auto);
        btEnviar = (Button)findViewById(R.id.b_enviar);


        btEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (publicacao == null){
                    inserir();
                    fotoOk = false;
                    finish();
                }
            }
        });

    }

    public void inserir(){

        try {
            //PEGA INSTANCIA PELO GERENCIADOR DE CONEXAO
            conn = ConnectionManager.getConexao(this);

            //TODO ALTERACAO QUE FIZ PARA FUNCIONAR, TENHO QUE ENTENDER DEPOIS
            publicacoes = new RepositorioPublicacoes(conn);

            //INSTANCIANDO UMA PUBLICACAO
            publicacao = new Publicacao();

            //PASSANDO OS VALORES INFORMADOS NA TELA DE NOVO FOCO
            publicacao.setIdUsuario(1l);
            publicacao.setDescricao(descricaoLugar.getText().toString());
            publicacao.setFoto(TransformarFotoBits.fotoByte(imgFoco));
            publicacao.setLatitude(latitude);
            publicacao.setLongitude(longitude);
            publicacao.setEndereco(localizacaoAuto.getText().toString());

            //CHAMANDO O METODO DE INSERIR
            publicacoes.inserirPublicacao(publicacao);

            //MOSTRA UMA MENSAGEM INFORMANDO QUE O BANCO NAO FOI CRIADO
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Publicação resgitrada!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        } catch (SQLException ex){

            //MOSTRA UMA MENSAGEM INFORMANDO O REGISTRO NAO FOI INSERIDO NA TABELA
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro cadastrar registro: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }
    }

    private synchronized void callConnection(){
        Log.i("LOG", "NovoFocoActivity.callConnection()");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //chamada da localizacao automática
        callConnection();
    }

    // LISTERNERS
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("LOG", "NovoFocoActivity.onConnected(" + bundle + ")");

        //BUSCANDO ULTIMA LOCALIZACAO DISPONIVEL
        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String error = "";
        String resultAddress = "";


        //BUSCANDO A LOCALIZAÇÃO PASSANDO A LAT E LONG, SE FUNCIONAR RETORNA 1 ENDERECO
        try {
            list = (ArrayList<Address>) geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
            latitude = String.valueOf(l.getLatitude());
            longitude = String.valueOf(l.getLongitude());
            Log.i("CORD", latitude + " | " + longitude);
        }
        catch (IOException e) {
            e.printStackTrace();
            error = "Sem internet.";
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            error = "Illegal arguments";
        }

        //se a lista contem endereco
        if(list != null && list.size() > 0){
            Address a = list.get(0);
            //arruma o enedereco encontrado em uma string
            for(int i = 0, tam = a.getMaxAddressLineIndex(); i < tam; i++){
                resultAddress += a.getAddressLine(i);
                resultAddress += i < tam - 1 ? ", " : "";
            }
        }
        else{
            Log.i("LOG", l.getLatitude() + " | " + l.getLongitude());
            resultAddress = error + " ";
        }

        Log.i("LOG", resultAddress);
        localizacaoAuto.setText(resultAddress);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "NovoFocoActivity.onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LOG", "NovoFocoActivity.onConnectionFailed(" + connectionResult + ")");
    }

    public void tirarFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                if (bitmap != null){
                    bitmap.recycle();
                }
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                imgFoco.setImageBitmap(resizeImage(this, bitmap, 300, 200));
                Log.i("LOG", "Rotacao: " + imgFoco.getRotation());
                //girarFoto(imgFoco);
                fotoOk = true;
            } catch (FileNotFoundException e){
                fotoOk = false;
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
            }
        }
    }

    public static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight){
        Bitmap novoBmp = null;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;

        //Calcula a escala em percentual do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;

        //Criando uma Matrix para manipulacao da Imagem Bitmap
        Matrix matrix = new Matrix();

        //Definindo a escala da proporcao para a matrix
        matrix.postScale(scalaW, scalaH);

        //criando o novo Bitmap com o novo tamanho
        matrix.postRotate(90);
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
    }

    public void girarFoto(View v){
        imgFoco.setRotation(90);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBackPressed() {
        if (fotoOk == true){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Alerta!");
            alertDialogBuilder.setMessage("Você realmente quer cancelar?");
            alertDialogBuilder.setPositiveButton(" Sim ", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            alertDialogBuilder.setNegativeButton(" Não ", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.show();
        }else{
            //FECHAR TELA
            finish();
        }
    }

    public void cancelar(View view){
        //CHAMA O METODO ONBACKPRESSED
        onBackPressed();
    }

}
