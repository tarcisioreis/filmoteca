package com.filmoteca.app.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.filmoteca.app.R;
import com.filmoteca.app.Utils.Utils;
import com.filmoteca.app.beans.AbstractBean;
import com.filmoteca.app.beans.Filme;
import com.filmoteca.app.beans.Genero;
import com.filmoteca.app.dao.FilmeBD;
import com.filmoteca.app.dao.GeneroBD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilmeCadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context = this;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private Bundle bundle;
    private TextView tvCodigo;
    private EditText edTitulo;
    private EditText edDiretor;
    private EditText edAnoLancamento;
    private Spinner spGenero;

    private Button btAcao;
    private Button btCancelar;

    private Filme filme;
    private FilmeBD filmeBD;
    private String operacao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_cadastro);

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
        getMenuInflater().inflate(R.menu.menu_cadastro_filme, menu);
        return true;
    }

    /*
        Metodo de menu que mostra o item para rotina de saida do aplicativo
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_del_filme) {
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
                filmeBD = new FilmeBD(context);

                if (filmeBD.delete(filme) > 0) {
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
        filmeBD = new FilmeBD(context);

        if (!isValidate()) {
            if (filme == null) {
                filme = new Filme();
            }

            filme.setTitulo(edTitulo.getText().toString());
            filme.setDiretor(edDiretor.getText().toString());
            filme.setAnoLancamnento(Integer.parseInt(edAnoLancamento.getText().toString()));
            filme.setGenero(Integer.parseInt(Long.toString(spGenero.getId())));

            retorno = filmeBD.update(filme);
        }

        return retorno;
    }

    private long incluir() {
        long retorno = -1;
        filmeBD = new FilmeBD(context);

        if (!isValidate()) {
            if (filme == null) {
                filme = new Filme();
            }

            filme.setTitulo(edTitulo.getText().toString());
            filme.setDiretor(edDiretor.getText().toString());
            filme.setAnoLancamnento(Integer.parseInt(edAnoLancamento.getText().toString()));
            filme.setGenero(Integer.parseInt(Long.toString(spGenero.getSelectedItemId())));

            retorno = filmeBD.insert(filme);
        }

        return retorno;
    }

    private boolean isValidate() {
        boolean cancel = false;
        View focusView = null;

        String titulo = edTitulo.getText().toString();
        float genero = spGenero.getId();

        if (titulo.isEmpty() || titulo.trim().length() == 0) {
            edTitulo.setError(getString(R.string.error_titulo));
            focusView = edTitulo;
            cancel = true;
        }

        if (!cancel) {
            if (genero == 0f || spGenero.getTag().toString().equals("Selecione um Gênero")) {
                spGenero.setFocusable(true);
                spGenero.setFocusableInTouchMode(true);
                spGenero.requestFocus();
                ((TextView) spGenero.getSelectedView()).setError(getString(R.string.error_genero));

                focusView = spGenero;
                cancel = true;
            }
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
                filme = bundle.getParcelable("filme");
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
        edTitulo = findViewById(R.id.edTitulo);

        edDiretor = findViewById(R.id.edDiretor);
        edAnoLancamento = findViewById(R.id.edAnoLancamento);
        spGenero = findViewById(R.id.spGenero);

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
            final GeneroBD generoBD = new GeneroBD(context);

            List<String> list = new ArrayList<String>();
            final List<AbstractBean> listGenero = generoBD.search();

            if (operacao.equals("A")) {
                tvCodigo.setText(String.format("%06d",filme.getCodigo()));
                edTitulo.setText(filme.getTitulo());
                edDiretor.setText(filme.getDiretor());
                edAnoLancamento.setText(String.format("%04d",filme.getAnoLancamnento()));

                if (listGenero.size() > 0) {
                    for(int i = 0; i < listGenero.size(); i++) {
                        Genero genero = new Genero();
                        genero = (Genero) listGenero.get(i);

                        if (genero.getCodigo() == filme.getIdGenero()) {
                            spGenero.setId(genero.getCodigo());
                            spGenero.setTag(genero.getNome());

                            spGenero.setSelected(true);
                            list.add(genero.getNome());
                        }
                    }
                    for(int i = 0; i < listGenero.size(); i++) {
                        Genero genero = new Genero();
                        genero = (Genero) listGenero.get(i);

                        if (genero.getCodigo() != filme.getIdGenero()) {
                            spGenero.setId(genero.getCodigo());
                            spGenero.setTag(genero.getNome());

                            spGenero.setSelected(true);
                            list.add(genero.getNome());
                        }
                    }
                }

                spGenero.setId(0);
                spGenero.setTag(new String("Selecione um Gênero"));

                list.add(new String("Selecione um Gênero"));
            }

            if (operacao.equals("I")) {

                filmeBD = new FilmeBD(context);

                List<AbstractBean> listSearch = filmeBD.search();

                if (listSearch.size() > 0)
                    tvCodigo.setText(String.format("%06d",(((Filme)listSearch.get((listSearch.size()-1))).getCodigo()+1)));
                else tvCodigo.setText(String.format("%06d",1));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    edAnoLancamento.setText(Integer.toString(LocalDate.now().getYear()));
                }

                spGenero.setId(0);
                spGenero.setTag(new String("Selecione um Gênero"));

                list.add(new String("Selecione um Gênero"));

                if (listGenero.size() > 0) {
                    for(int i = 0; i < listGenero.size(); i++) {
                        Genero genero = new Genero();
                        genero = (Genero) listGenero.get(i);

                        spGenero.setId(genero.getCodigo());
                        spGenero.setTag(genero.getNome());

                        list.add(genero.getNome());
                    }
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spGenero.setAdapter(dataAdapter);

            spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spGenero.getSelectedItem().toString().equals("Selecione um Gênero")) {
                        spGenero.setFocusable(true);
                        spGenero.setFocusableInTouchMode(true);
                        spGenero.requestFocus();
                        ((TextView) spGenero.getSelectedView()).setError(getString(R.string.error_genero));
                    } else {
                        try {
                            GeneroBD generoBD = new GeneroBD(context);
                            List<AbstractBean> listGenero = generoBD.searchByNome(parent.getItemAtPosition(position).toString());

                            if (listGenero.size() > 0) {
                                spGenero.setId(((Genero)listGenero.get(0)).getCodigo());
                                spGenero.setTag(((Genero)listGenero.get(0)).getNome());
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, R.string.alerta_error, Toast.LENGTH_LONG).show();                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });

            if (listGenero.size() == 0) {
                throw new Exception(getString(R.string.error_list_genero));
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
        startActivity(new Intent(context, ListFilmeActivity.class));
        finish();
    }

}
