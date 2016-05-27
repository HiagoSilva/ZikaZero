package com.projeto.zikazero.database;

/**
 * Created by Hiago on 23/05/2016.
 */
public class ScriptSQL {

    public static String getCreateUsuario(){

        //SCRIPT PARA A CRIAR A TABELA USUARIO
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS USUARIO( ");
        sqlBuilder.append("_id            INTEGER        NOT NULL    PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("NOME           VARCHAR(45)    NOT NULL, ");
        sqlBuilder.append("EMAIL          VARCHAR(100)   NOT NULL, ");
        sqlBuilder.append(" SENHA          VARCHAR(20)    NOT NULL ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreatePublicacao(){

        //SCRIPT PARA A CRIAR A TABELA PUBLICACAO
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS PUBLICACAO( ");
        sqlBuilder.append("_id            INTEGER        NOT NULL    PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("ID_USUARIO     INTEGER        NOT NULL, ");
        sqlBuilder.append("DESCRICAO      VARCHAR(255), ");
        sqlBuilder.append("FOTO           BLOB, ");
        sqlBuilder.append("LATITUDE       VARCHAR(10), ");
        sqlBuilder.append("LONGITUDE      VARCHAR(10), ");
        sqlBuilder.append("ENDERECO       VARCHAR ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }


}
