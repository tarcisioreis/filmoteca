package com.filmoteca.app.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.filmoteca.app.MainActivity;
import com.filmoteca.app.R;
import com.filmoteca.app.Utils.Utils;
import com.filmoteca.app.adapter.FilmeAdapter;
import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Filme;
import com.filmoteca.app.constantes.Constantes;
import com.filmoteca.app.dao.FilmeBD;

import java.util.ArrayList;
import java.util.List;

public class ListFilmeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context context = this;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private ListView listView;
    private List<Filme> list;
    private FilmeAdapter adapter;

    private TextView tvMsgRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
    }

    protected void errorAlert(String message) {
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.StyleDialogs));

        alertDialogBuilder.setTitle(getString(R.string.alerta_title));
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.hint_fechar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*
        Metodo de menu que habilita saida do aplicativo na barra onde esta o nome do aplicativo
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_filme, menu);
        return true;
    }

    /*
        Metodo de menu que mostra o item para rotina de saida do aplicativo
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_voltar) {
            voltar();
            return true;
        }

        if (id == R.id.action_inc_filme) {
            incluir();
            return true;
        }

        if (id == R.id.action_sair) {
            sair();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            voltar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Metodo atribuido ao botão do celular Voltar, ao acionar irá solicitar confirmação ou
        não do aplicativo
    */
    public void onBackPressed() {
        voltar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(context, FilmeCadastroActivity.class);

        it.putExtra("filme", (Parcelable) list.get(position));

        startActivity(it);
        finish();
    }

    private void incluir() {
        Intent it = new Intent(context, FilmeCadastroActivity.class);

        startActivity(it);
        finish();
    }

    private void sair() {
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.StyleDialogs));

        alertDialogBuilder.setTitle(getString(R.string.alerta_title));

        alertDialogBuilder.setMessage(getString(R.string.alerta_sair));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.hint_sim), new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int id) {
                finishAndRemoveTask();
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.hint_nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void voltar() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private void init() {
        if (Utils.isConnected(context)) {
            initViews();
            initUser();
        } else {
            errorAlert(getString(R.string.error_network));
        }
    }

    private void initViews() {
        tvMsgRegistro = findViewById(R.id.tvMsgRegistro);
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(this);
        listView.setAdapter(null);

        tvMsgRegistro.setVisibility(View.GONE);
    }

    private void initUser() {
        try {
            list = new ArrayList<Filme>();

            getListFilmes();
        } catch (Exception e) {
            errorAlert(e.getMessage());
        }
    }


    private void getListFilmes() {

        try {
            Filme filme = null;
            FilmeBD filmeBD = new FilmeBD(context);
            List<AbstractBean> listSearch = filmeBD.search();

            for(int i = 0; i < listSearch.size(); i++) {
                filme = new Filme();

                filme = (Filme) listSearch.get(i);

                list.add(filme);

                Log.d(Constantes.TAG, filme.getCodigo() + " > " + filme.getTitulo());
            }

            if (list.size() > 0) {
                adapter = new FilmeAdapter(context, list);
                listView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                tvMsgRegistro.setVisibility(View.GONE);
            } else {
                tvMsgRegistro.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
