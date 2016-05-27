package com.projeto.zikazero.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.projeto.zikazero.Dao.entidade.Publicacao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiago on 23/05/2016.
 */
public class RepositorioPublicacoes {

    private SQLiteDatabase conn;

    //CONSTRUTOR
    public RepositorioPublicacoes(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void inserirPublicacao(Publicacao publicacao){
        ContentValues values = new ContentValues();
        values.put("ID_USUARIO", 1);
        values.put("DESCRICAO", publicacao.getDescricao());
        values.put("FOTO", publicacao.getFoto());
        values.put("LATITUDE", publicacao.getLatitude());
        values.put("LONGITUDE", publicacao.getLongitude());
        values.put("ENDERECO", publicacao.getEndereco());
        conn.insertOrThrow("PUBLICACAO", null, values);
    }

    //METODO PARA LISTAR TODAS AS PUBLICACOES
    public ArrayList<Publicacao> listaPublicacoes(){
        ArrayList<Publicacao> lista = new ArrayList<Publicacao>();

        //REALIZA UMA BUSCA NO BANCO NA TABELA PUBLICACAO
        Cursor cursor = conn.query("PUBLICACAO", null, null, null, null, null, null);

        //VERIFICA SE RETORNOU ALGUM RESULTADO
        if (cursor.getCount() > 0){

            //COLOCAR CURSOR NA PRIMEIRA POSICAO DOS REGISTROS ENCONTRADOS
            cursor.moveToFirst();

            //ENQUANTO HOUVER REGISTROS CONTINUA
            do{
                //1 É O INDEX DE ID_USUARIO NO BANCO DE DADOS
                Publicacao publicacao = new Publicacao();

                publicacao.setDescricao(cursor.getString(2));
                publicacao.setFoto(cursor.getBlob(3));
                publicacao.setLatitude(cursor.getString(4));
                publicacao.setLongitude(cursor.getString(5));
                publicacao.setEndereco(cursor.getString(6));

                //ADD O ENDERECO RETORNADO AO VETOR DE RESULTADOS
                lista.add(publicacao);
            } while (cursor.moveToNext());
        }

        //RETORNAR AS PUBLICACOES
        return lista;
    }

}
