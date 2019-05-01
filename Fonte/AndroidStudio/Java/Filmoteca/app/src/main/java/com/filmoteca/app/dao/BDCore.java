package com.filmoteca.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 *
 * Classe usada para criacao, manipulacao de dados no banco de dados SQLLite local no aplicativo
 * 
 *
 */
public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "filmoteca";
    private static final int VERSAO_BD = 1;


    public BDCore(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table if not exists genero(codigo integer PRIMARY KEY, " +
                   "                                  nome text not null);");
        bd.execSQL("create table if not exists filme(codigo integer PRIMARY KEY, " +
                   "                                 titulo text not null, " +
                   "                                 diretor text null, " +
                   "                                 anoLancamento integer null, " +
                   "                                 genero integer not null, " +
                   "FOREIGN KEY(genero) REFERENCES genero(codigo));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        onCreate(bd);
    }

}
