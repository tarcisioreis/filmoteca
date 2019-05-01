package com.filmoteca.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.filmoteca.app.R;
import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Filme;
import com.filmoteca.app.beans.Genero;
import com.filmoteca.app.constantes.Constantes;
import com.filmoteca.app.dao.GeneroBD;

import org.w3c.dom.Text;

import java.util.List;

/**
 *
 * 
 * Classe para montar dados na listagem de generos
 * 
 *    
 */
public class FilmeAdapter extends BaseAdapter {

    private Context context;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private List<Filme> list = null;
    private List<AbstractBean> listGenero = null;
    private LayoutInflater inflater;
    private GeneroBD generoBD;
    private Genero genero;

    public FilmeAdapter(Context c, List<Filme> l){
        list = l;
        context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void errorAlert(String message) {
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.StyleDialogs));

        alertDialogBuilder.setTitle(context.getString(R.string.alerta_title));
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(context.getString(R.string.hint_fechar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            ViewHolder holder;

            if (view == null) {
                view = inflater.inflate(R.layout.filme, null);
            }

            holder = new ViewHolder();
            holder.tvCodigo = view.findViewById(R.id.tvCodigo);
            holder.tvTitulo = view.findViewById(R.id.tvTitulo);
            holder.tvGenero = view.findViewById(R.id.tvGenero);

            holder.tvCodigo.setText(String.format("%06d",list.get(position).getCodigo()));
            holder.tvTitulo.setText(list.get(position).getTitulo());

            generoBD = new GeneroBD(context);

            listGenero = generoBD.search(list.get(position).getIdGenero());

            if (listGenero.size() > 0) {
                genero = (Genero) listGenero.get(0);
            } else {
                genero = new Genero();

                genero.setNome("Sem Gênero");
            }

            holder.tvGenero.setText(genero.getNome());

            if (position % 2 == 0) {
                view.setBackgroundResource(R.drawable.alterselector1);
            } else {
                view.setBackgroundResource(R.drawable.alterselector2);
            }
        } catch (Exception e) {
            Log.d(Constantes.TAG, e.getMessage());
        }

        return view;
    }

    // INNER CLASS
    private static class ViewHolder{
        TextView tvCodigo;
        TextView tvTitulo;
        TextView tvGenero;
    }

}
