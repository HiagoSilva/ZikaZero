package com.projeto.zikazero.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hiago on 23/05/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    static final int VERSAO = 2;

    public DataBase(Context context){
        /*
        * CADA ALTECAO REALIZAD NO BANCO DEVREÁ SER MUDADO O ULTIMO PARAMETRO DA CHAMADA
        * DO SUPER(); SE ESTIVER 1 PODE MUDAR PARA 2, POR EXEMPLO
        * */

        //CRIAÇAO DO BASE DE DADOS
        super(context, "ZikaDB", null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SE AS TABELAS AINDA NAO FORAM CRIADAS, CRIA
        db.execSQL(ScriptSQL.getCreateUsuario());
        db.execSQL(ScriptSQL.getCreatePublicacao());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
