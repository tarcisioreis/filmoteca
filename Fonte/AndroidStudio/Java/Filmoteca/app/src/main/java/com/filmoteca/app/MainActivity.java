package com.filmoteca.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.filmoteca.app.Utils.Utils;
import com.filmoteca.app.config.FireBaseConfig;
import com.filmoteca.app.controller.ListFilmeActivity;
import com.filmoteca.app.controller.ListGeneroActivity;
import com.filmoteca.app.service.FireBaseService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Variaveis usadas para mostrar alertas de saida do aplicativo */
    private Context context = this;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private Button btGenero;
    private Button btFilme;

/*
    Metodo Principal que inicia a criação da tela e variaveis que receberam objetos da tela criada
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*
    Metodo de menu que mostra o item para rotina de saida do aplicativo
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sair) {
            sair();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Metodo atribuido ao botão do celular Voltar, ao acionar irá solicitar confirmação ou
        não do aplicativo
    */
    public void onBackPressed() {
        sair();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btGenero :

                if (Utils.isConnected(context)) {
                    startActivity(new Intent(context, ListGeneroActivity.class));
                    finish();
                } else {
                    errorAlert(getString(R.string.error_network));
                }

                break;
            case R.id.btFilme :

                if (Utils.isConnected(context)) {
                    startActivity(new Intent(context, ListFilmeActivity.class));
                    finish();
                } else {
                    errorAlert(getString(R.string.error_network));
                }

                break;
        }
    }

    /*
        Metodo usado para sair e encerrar o aplicativo
    */
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

    private void init() {
        btGenero = findViewById(R.id.btGenero);
        btFilme = findViewById(R.id.btFilme);

        btGenero.setOnClickListener(this);
        btFilme.setOnClickListener(this);
    }

}
