package com.projeto.zikazero;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projeto.zikazero.Dao.RepositorioPublicacoes;
import com.projeto.zikazero.Dao.entidade.Publicacao;
import com.projeto.zikazero.database.ConnectionManager;

import java.util.ArrayList;
import java.util.List;

public class MapaFocosActivity extends AppCompatActivity {

    GoogleMap map;
    private Marker marker;


    private SQLiteDatabase conn;
    private RepositorioPublicacoes publicacoes;
    private Publicacao publicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_focos);

        try {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);
            map.getUiSettings().setTiltGesturesEnabled(true);
            conn = ConnectionManager.getConexao(this);

            publicacoes = new RepositorioPublicacoes(conn);
            if (publicacoes != null)
                Log.i("LOG", "Null publ");
            ArrayList<Publicacao> listaPublicacoes = publicacoes.listaPublicacoes();
            if (listaPublicacoes != null)
                Log.i("LOG", "Null listaPu");

            if (listaPublicacoes != null) {
                for (int i = 0; i < listaPublicacoes.size(); i++) {
                    LatLng latLng = new LatLng(Double.valueOf(listaPublicacoes.get(i).getLatitude()),
                            Double.valueOf(listaPublicacoes.get(i).getLongitude()));

                    Log.i("LOG", "Lista Nao nula: " + listaPublicacoes.get(i).getDescricao());
                    map.addMarker( new MarkerOptions()
                        .title(listaPublicacoes.get(i).getDescricao())
                        .snippet(listaPublicacoes.get(i).getEndereco())
                        .position(latLng));
                    Log.i("LOG", "Adicionando ao mapa " + listaPublicacoes.get(i).getLatitude()+ " | " + listaPublicacoes.get(i).getLongitude());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("LOG", "Excpion lista " + e.toString());
        }
        /*
        try{
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            MarkerOptions k = new MarkerOptions()
                    .position(new LatLng(-3.0041328, -59.9835304))
                    .title("Mapa Hiago")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_media_pause))
                    .draggable(true).snippet("2Casa dos raimundo");
            MarkerOptions k2 = new MarkerOptions()
                    .position(new LatLng(-3.0041301, -59.9835399))
                    .title("2Mapa Hiago")
                    .draggable(true).snippet("2Casa dos raimundo");
            map.addMarker(k);
            map.addMarker(k2);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(true);
            map.getUiSettings().setTiltGesturesEnabled(true);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(-3.0041328, -59.9835304)).zoom(12).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        catch (Exception e){
            e.printStackTrace();
        }*/



 /*       //EVENTS
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.i("LOG", "setOnCameraChangeListener");

                /*if(marker != null) {
                    marker.remove();
                }
                customAddMarker(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), "1: Maracdor Alterado", "reposicionado");

            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("LOG", "setOnMapClickListener");

                /*if (marker != null) {
                    marker.remove();
                }
                customAddMarker(new LatLng(latLng.latitude, latLng.longitude), "2: Maracdor Alterado", "reposicionado");

            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("LOG", "3: Marker: " + marker.getTitle());
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("LOG", "4: Marker: " + marker.getTitle());
            }
        });
    }

    //PARAMETROS: LATITUDE E LONGITUDE, TITULO E DESCRICAO
    public void customAddMarker(LatLng latLng, String title, String snippet){
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).title(title).snippet(snippet).draggable(true);
        Marker marker = map.addMarker(options);

    }
 */
    }
}
