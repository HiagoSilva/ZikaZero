package com.projeto.zikazero.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projeto.zikazero.Dao.entidade.Usuario;

import java.util.ArrayList;

/**
 * Created by Hiago on 26/05/2016.
 */
public class RepositorioUsuarios {

    private SQLiteDatabase conn;

    //CONSTRUTOR
    public RepositorioUsuarios(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void inserirUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("EMAIL", usuario.getEmail());
        values.put("NOME", usuario.getNome());
        values.put("SENHA", usuario.getSenha());
        conn.insertOrThrow("USUARIO", null, values);
    }
    
}
