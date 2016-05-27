package com.projeto.zikazero.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projeto.zikazero.Dao.entidade.Publicacao;
import com.projeto.zikazero.NovoFocoActivity;
import com.projeto.zikazero.R;

import java.util.ArrayList;

/**
 * Created by Hiago on 26/05/2016.
 *
 * CO'DIGO BASEADO NA PAGINA:
 * http://www.devmedia.com.br/adapter-customizado-do-android-para-insercao-de-imagens-no-sqlite/32844
 */
public class PostsAdapter extends ArrayAdapter<Publicacao> {

    int id;
    Context contexto;
    ArrayList<Publicacao> publicacoes;

    public PostsAdapter(Context contexto, int id, ArrayList<Publicacao> publicacoes) {
        super(contexto, id, publicacoes);
        this.contexto = contexto;
        this.id = id;
        this.publicacoes = publicacoes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Publicacao publicacao;
        ImageView foto;
        TextView descricao;
        TextView endereco;
        Bitmap raw, rawResize;
        byte[] fotoArray;

        if (view == null){
            //A classe LayoutInflater é responsável por instanciar um XML a sua view correspondente.
            LayoutInflater inflater = LayoutInflater.from(contexto);
            view = inflater.inflate(id, parent, false);
            if (view == null) Log.i("Log", "null");
        }

        foto = (ImageView) view.findViewById(R.id.iv);
        descricao = (TextView) view.findViewById(R.id.tvDescricao);
        endereco = (TextView) view.findViewById(R.id.tvEndereco);

        publicacao = this.publicacoes.get(position);

        descricao.setText(publicacao.getDescricao());
        endereco.setText(publicacao.getEndereco());
        fotoArray = publicacao.getFoto();

        if(fotoArray != null){
            raw  = BitmapFactory.decodeByteArray(fotoArray, 0, fotoArray.length);
            rawResize = NovoFocoActivity.resizeImage(contexto, raw, 200, 300);
            foto.setRotation(-90);
            foto.setImageBitmap(rawResize);
        }

        return view;
    }
}
