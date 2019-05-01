package com.filmoteca.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Filme;
import com.filmoteca.app.beans.Genero;
import com.filmoteca.app.dao.iDao.iBD;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 * Classe DAO para manipulacao de dados em tabela de banco de dados local
 * 
 * 
 */
public class FilmeBD implements iBD {

    private Context context;
    private SQLiteDatabase bd;

    public FilmeBD(Context context) {
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

        valores.put("titulo",        ((Filme) bean).getTitulo());
        valores.put("diretor",       ((Filme) bean).getDiretor());
        valores.put("anoLancamento", ((Filme) bean).getAnoLancamnento());
        valores.put("genero",        ((Filme) bean).getIdGenero());

        return(bd.update("filme", valores, "codigo = " + String.valueOf(((Filme) bean).getCodigo()), null));
    }

    @Override
    public long insert(AbstractBean bean) {
        ContentValues valores = new ContentValues();

        valores.put("titulo",        ((Filme) bean).getTitulo());
        valores.put("diretor",       ((Filme) bean).getDiretor());
        valores.put("anoLancamento", ((Filme) bean).getAnoLancamnento());
        valores.put("genero",        ((Filme) bean).getIdGenero());

        return(bd.insert("filme", null, valores));
    }

    @Override
    public int delete(AbstractBean bean) {
        return(bd.delete("filme", "codigo = " + String.valueOf(((Filme) bean).getCodigo()), null));
    }

    @Override
    public int delete() {
        return(bd.delete("filme", null, null));
    }

    @Override
    public List<AbstractBean> search() throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "titulo", "diretor", "anoLancamento", "genero"};

        Cursor cursor = bd.query("filme", colunas, null, null, null, null, null);

        GeneroBD generoBD = new GeneroBD(getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Filme f = new Filme();

                f.setCodigo(cursor.getInt(0));
                f.setTitulo(cursor.getString(1));
                f.setDiretor(cursor.getString(2));
                f.setAnoLancamnento(cursor.getInt(3));
                f.setGenero(cursor.getInt(4));

                list.add(f);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    @Override
    public List<AbstractBean> search(int id) throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "titulo", "diretor", "anoLancamento", "genero"};

        Cursor cursor = bd.query("filme", colunas, "codigo = " + String.valueOf(id), null, null, null, null);

        GeneroBD generoBD = new GeneroBD(getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Filme f = new Filme();

                f.setCodigo(cursor.getInt(0));
                f.setTitulo(cursor.getString(1));
                f.setDiretor(cursor.getString(2));
                f.setAnoLancamnento(cursor.getInt(3));
                f.setGenero(cursor.getInt(4));

                list.add(f);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public List<AbstractBean> searchByGenero(int idGenero) throws Exception {
        List<AbstractBean> list = new ArrayList<AbstractBean>();
        String[] colunas = new String[]{"codigo", "titulo", "diretor", "anoLancamento", "genero"};

        Cursor cursor = bd.query("filme", colunas, "genero = " + String.valueOf(idGenero), null, null, null, null);

        GeneroBD generoBD = new GeneroBD(getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Filme f = new Filme();

                f.setCodigo(cursor.getInt(0));
                f.setTitulo(cursor.getString(1));
                f.setDiretor(cursor.getString(2));
                f.setAnoLancamnento(cursor.getInt(3));
                f.setGenero(cursor.getInt(4));

                list.add(f);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

}
