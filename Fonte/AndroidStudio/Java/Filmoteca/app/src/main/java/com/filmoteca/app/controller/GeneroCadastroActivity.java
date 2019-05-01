package com.filmoteca.app.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.filmoteca.app.R;
import com.filmoteca.app.Utils.Utils;
import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Genero;
import com.filmoteca.app.dao.GeneroBD;

import java.util.List;

public class GeneroCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private Bundle bundle;
    private TextView tvCodigo;
    private EditText edNome;

    private Button btAcao;
    private Button btCancelar;

    private Genero genero;
    private GeneroBD generoBD;
    private String operacao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero_cadastro);

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
        getMenuInflater().inflate(R.menu.menu_cadastro_genero, menu);
        return true;
    }

    /*
        Metodo de menu que mostra o item para rotina de saida do aplicativo
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_del_genero) {
            deletar();
            return true;
        }

        if (id == R.id.action_voltar) {
            voltar();
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

    private void deletar() {
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.StyleDialogs));

        alertDialogBuilder.setTitle(getString(R.string.alerta_title));

        alertDialogBuilder.setMessage(getString(R.string.alerta_excluir));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.hint_sim), new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(DialogInterface dialog, int id) {
                generoBD = new GeneroBD(context);

                if (generoBD.delete(genero) > 0) {
                    dialog.dismiss();
                    Toast.makeText(context, R.string.alerta_sucesso, Toast.LENGTH_LONG).show();
                    voltar();
                } else Toast.makeText(context, R.string.alerta_error, Toast.LENGTH_LONG).show();
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

    private int alterar() {
        int retorno = -1;
        generoBD = new GeneroBD(context);

        if (!isValidate()) {
            if (genero == null) {
                genero = new Genero();
            }

            genero.setNome(edNome.getText().toString());

            retorno = generoBD.update(genero);
        }

        return retorno;
    }

    private long incluir() {
        long retorno = -1;
        generoBD = new GeneroBD(context);

        if (!isValidate()) {
            if (genero == null) {
                genero = new Genero();
            }

            genero.setNome(edNome.getText().toString());

            retorno = generoBD.insert(genero);
        }

        return retorno;
    }

    private boolean isValidate() {
        boolean cancel = false;
        View focusView = null;

        String nome = edNome.getText().toString();

        if (nome.isEmpty() || nome.trim().length() == 0) {
            edNome.setError(getString(R.string.error_nome));
            focusView = edNome;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        }

        return cancel;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btCancelar :
                voltar();
                break;
            case R.id.btAcao :
                if (operacao.equals("A")) {
                    if (alterar() > 0) {
                        Toast.makeText(context, R.string.alerta_sucesso, Toast.LENGTH_LONG).show();
                        btAcao.setEnabled(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                btAcao.setBackground(getDrawable(R.drawable.buttonshapedesativado));
                            }
                        }
                    }
                }

                if (operacao.equals("I")) {
                    if (incluir() > 0) {
                        Toast.makeText(context, R.string.alerta_sucesso, Toast.LENGTH_LONG).show();
                        btAcao.setEnabled(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                btAcao.setBackground(getDrawable(R.drawable.buttonshapedesativado));
                            }
                        }
                    } else {
                        Toast.makeText(context, R.string.alerta_error, Toast.LENGTH_LONG).show();
                        btAcao.setEnabled(true);
                    }
                }

                break;
        }
    }

    private void init() {

        if (Utils.isConnected(context)) {
            bundle = getIntent().getExtras();

            // Valida a operação A - Alteração ou I - Inclusão
            if (bundle != null) {
                operacao = "A";
                genero = bundle.getParcelable("genero");
            } else {
                operacao = "I";
            }

            initViews();
            initUser();
        } else {
            errorAlert(getString(R.string.error_network));
        }

    }

    private void initViews() {
        Resources resources = getResources();

        tvCodigo = findViewById(R.id.tvCodigo);
        edNome = findViewById(R.id.edNome);

        btAcao = findViewById(R.id.btAcao);
        btCancelar = findViewById(R.id.btCancelar);

        if (operacao.equals("A")) {
            btAcao.setText(getString(R.string.action_alterar));
            btAcao.setHint(R.string.action_alterar);
            btAcao.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_update,0,0,0);
        }

        if (operacao.equals("I")) {
            btAcao.setText(getString(R.string.action_incluir));
            btAcao.setHint(R.string.action_incluir);
            btAcao.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);
        }

        btAcao.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }

    private void initUser() {
        try {

            if (operacao.equals("A")) {
                tvCodigo.setText(String.format("%06d",genero.getCodigo()));
                edNome.setText(genero.getNome());
            }

            if (operacao.equals("I")) {
                generoBD = new GeneroBD(context);

                List<AbstractBean> listSearch = generoBD.search();

                if (listSearch.size() > 0)
                    tvCodigo.setText(String.format("%06d",(((Genero)listSearch.get((listSearch.size()-1))).getCodigo()+1)));
                else tvCodigo.setText(String.format("%06d",1));
            }
        } catch (Exception e) {
            errorAlert(e.getMessage());
        }
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
        startActivity(new Intent(context, ListGeneroActivity.class));
        finish();
    }

}
