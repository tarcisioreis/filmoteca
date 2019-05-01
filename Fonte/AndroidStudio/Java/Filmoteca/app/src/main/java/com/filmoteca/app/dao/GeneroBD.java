package com.filmoteca.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Filme;
import com.filmoteca.app.beans.Genero;
import com.filmoteca.app.dao.iDao.iBD;
import com.filmoteca.app.dao.FilmeBD;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 * Classe DAO para manipulacao de dados em tabela de banco de dados local
 * 
 * 
 */
public class GeneroBD implements iBD {

    private Context context;
    private SQLiteDatabase bd;

    private FilmeBD filmeBD;

    public GeneroBD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();

        this.setContext(context);
    }

    protected Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int update(AbstractBean bean) {
        ContentValues valores = new ContentValues();

        valores.put("nome", ((Genero) bean).getNome());

        return(bd.update("genero", valores, "codigo = " + String.valueOf(((Genero) bean).getCodigo()), null));
    }

    @Override
    public long insert(AbstractBean bean) {
        ContentValues valores = new ContentValues();

        valores.put("nome", ((Genero) bean).getNome());

        return(bd.insert("genero", null, valores));
    }

    @Override
    public int delete(AbstractBean bean) {
        try {
            Genero genero = ((Genero) bean);

            filmeBD = new FilmeBD(getContext());
            if (filmeBD.searchByGenero(genero.getCodigo()).size() > 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }

        return(bd.delete("genero", "codigo = " + String.valueOf(((Genero) bean).getCodigo()), null));
    }

    @Override
    public int delete() {
        return(bd.delete("genero", null, null));
    }

    @Override
    public List<AbstractBean> search() throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "nome"};

        Cursor cursor = bd.query("genero", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Genero g = new Genero(cursor.getInt(0), cursor.getString(1));

                list.add(g);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    @Override
    public List<AbstractBean> search(int id) throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "nome"};

        Cursor cursor = bd.query("genero", colunas, "codigo = " + String.valueOf(id), null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Genero g = new Genero(cursor.getInt(0), cursor.getString(1));

                list.add(g);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public List<AbstractBean> searchByNome(String nome) throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "nome"};

        Cursor cursor = bd.query("genero", colunas, "nome = '" + nome + "'", null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Genero g = new Genero(cursor.getInt(0), cursor.getString(1));

                list.add(g);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

}
