package com.projeto.zikazero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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

    private Bitmap bitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Boolean fotoOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_foco);

        imgFoco = (ImageView) findViewById(R.id.iv_foto);
        descricaoLugar = (EditText) findViewById(R.id.et_descricao);
        localizacaoAuto = (TextView) findViewById(R.id.tv_localizacao_auto);

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

        //buscando ultima localizacao disponivel
        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String error = "";
        String resultAddress = "";


        try {
            list = (ArrayList<Address>) geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
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
                imgFoco.setImageBitmap(resizeImage(this, bitmap, 500, 428));
                girarFoto(imgFoco);
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

    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal, float newWidth, float newHeight){
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
        matrix.postScale(scalaW, scalaW);

        //criando o novo Bitmap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
    }

    public void girarFoto(View v){
        imgFoco.setRotation(90);
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
