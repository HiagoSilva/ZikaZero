package com.projeto.zikazero.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hiago on 23/05/2016.
 */
public class ConnectionManager {

    public static SQLiteDatabase getConexao(Context context) throws SQLException {
        //BANCO DE DADOS
        DataBase dataBase;
        SQLiteDatabase conn;

        Boolean status = false;

        dataBase = new DataBase(context);
        conn = dataBase.getWritableDatabase();

        return conn;
    }


}
