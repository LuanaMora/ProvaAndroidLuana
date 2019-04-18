package com.android.provaandroidluana;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.provaandroidluana.model.Atendimento;
import com.android.provaandroidluana.model.Empresa;
import com.android.provaandroidluana.util.Dados;
import com.android.provaandroidluana.util.Mensagem;
import com.android.provaandroidluana.util.TipoMensagem;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private EditText etAssunto, etContato, etTelefone, etEmail;
    private TextView tvEmpresa, tvData;
    private Spinner spAtendimento;
    private Button btSalvar, btCancelar;
    private ListView lvAtendimento;
    private ImageButton ibEmpresa;
    private Atendimento atendimento = null;
    private DatePickerDialog datePickerDialog;
    private final int LOVEMPRESA = 1;
    private int day, month, year;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();
        carregaEventos();
        carregaEmpresa();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(MainActivity.this, this, year, month, day);

    }

    private void loadComponents() {
        etAssunto = findViewById(R.id.etAssunto);
        etContato = findViewById(R.id.etContato);
        etEmail = findViewById(R.id.etEmail);
        etTelefone = findViewById(R.id.etTelefone);
        tvEmpresa = findViewById(R.id.tvEmpresa);
        spAtendimento = findViewById(R.id.spAtendimento);
        btSalvar = findViewById(R.id.btSalvar);
        btCancelar = findViewById(R.id.btCancelar);
        lvAtendimento = findViewById(R.id.lvAtendimentos);
        ibEmpresa = findViewById(R.id.ibEmpresa);
        tvData = findViewById(R.id.tvData);

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });


        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // btCancelar.setVisibility(View.VISIBLE);
                try {
                    String recuperaSpinner = spAtendimento.getSelectedItem().toString();
                    atendimento = new Atendimento();
                    atendimento.setAssunto(etAssunto.getText().toString());
                    atendimento.setContato(etContato.getText().toString());
                    atendimento.setEmail(etEmail.getText().toString());
                    atendimento.setTelefone(etTelefone.getText().toString());
                    atendimento.setTipoAtendimento(recuperaSpinner);
                    atendimento.setData(tvData.getText().toString());
                    atendimento.setEmpresa(tvEmpresa.getText().toString());
                    Dados.atendimentoList.add(atendimento);
                    final ArrayAdapter<Atendimento> adapterAtendimento = new ArrayAdapter<Atendimento>(MainActivity.this, R.layout.item_prova, Dados.atendimentoList);
                    lvAtendimento.setAdapter(adapterAtendimento);
                    limpaCampos();
                    Mensagem.ExibirMensagem(MainActivity.this, "Salvo", TipoMensagem.SUCESSO);
                    btSalvar.setText("Salvar");
                } catch(Exception ep){
                    Mensagem.ExibirMensagem(MainActivity.this, "Não foi possível salvar! Tente Novamente", TipoMensagem.ERRO);
                }

            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
                btSalvar.setText("Salvar");
            }
        });


        lvAtendimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                atendimento = (Atendimento) lvAtendimento.getItemAtPosition(position);
                carregarCampos();

            }
        });


        lvAtendimento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertConfirmacao = new AlertDialog.Builder(MainActivity.this);
                alertConfirmacao.setTitle("Confirmação Exclusão");
                alertConfirmacao.setMessage("Deseja Realmente excluir o registro?");
                alertConfirmacao.setNeutralButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dados.atendimentoList.remove(position);
                        limpaCampos();
                        carregarLista();
                    }
                });

                alertConfirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        carregarLista();
                        limpaCampos();
                    }
                });
                alertConfirmacao.show();
                return true;
            }
        });

        lvAtendimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btSalvar.setText("Atualizar");
                atendimento = (Atendimento) lvAtendimento.getItemAtPosition(position);
                carregarCampos();
                Dados.atendimentoList.remove(position);
                carregarLista();
            }
        });

    }


    private void carregarCampos() {
        etAssunto.setText(atendimento.getAssunto());
        etContato.setText(atendimento.getContato());
        etTelefone.setText(atendimento.getTelefone());
        etEmail.setText(atendimento.getEmail());
        tvData.setText(atendimento.getData());
        tvEmpresa.setText(atendimento.getEmpresa());
    }

    private void carregaEventos() {
        ibEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainEmpresaActivity.class);
                intent.putExtra("TIPO", "EMPRESA");
                startActivityForResult(intent, LOVEMPRESA);
            }
        });

    }

    private void carregarLista() {
        ArrayAdapter<Atendimento> atendimentoAdapter =
                new ArrayAdapter<Atendimento>(MainActivity.this, R.layout.item_prova, Dados.atendimentoList);
        lvAtendimento.setAdapter(atendimentoAdapter);
        if (lvAtendimento.getCount() > 0) {
            calculeHeightListView();
        }
    }

    private void calculeHeightListView() {
        int totalHeight = 0;
        ListAdapter adapter = lvAtendimento.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lvAtendimento);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lvAtendimento.getLayoutParams();
        params.height = totalHeight + (lvAtendimento.getDividerHeight() * (adapter.getCount()) + 10);
        lvAtendimento.setLayoutParams(params);
        lvAtendimento.requestLayout();
    }

    private void limpaCampos() {
        etAssunto.setText("");
        etContato.setText("");
        etTelefone.setText("");
        etEmail.setText("");
        tvEmpresa.setText("");
        tvData.setText("");
    }

    private void carregaEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setCodigo(1);
        empresa.setDescricao("Empresa da Luana");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(2);
        empresa.setDescricao("Empresa do Marcelo");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(3);
        empresa.setDescricao("Empresa do Pedro");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(4);
        empresa.setDescricao("Empresa do Eduardo");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(5);
        empresa.setDescricao("Empresa do Norton");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(6);
        empresa.setDescricao("Empresa do Felipe");
        Dados.empresaList.add(empresa);

        empresa = new Empresa();
        empresa.setCodigo(7);
        empresa.setDescricao("Empresa do Matheus");
        Dados.empresaList.add(empresa);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == LOVEMPRESA) {
            Empresa empresa = (Empresa) data.getExtras().get("EMPRESASEL");
            if (empresa != null)
                tvEmpresa.setText(empresa.toString());
        } else {
            Mensagem.ExibirMensagem(MainActivity.this, "Favor selecionar uma pessoa", TipoMensagem.ALERTA);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //this.day = day;
        Mensagem.ExibirMensagem(MainActivity.this, "Data Selecionada (" + day + "/ " + month + "/ " + year + ")", TipoMensagem.ALERTA);
        tvData.setText(day + "/ " + (month + 1) + "/ " + year);
    }
}
