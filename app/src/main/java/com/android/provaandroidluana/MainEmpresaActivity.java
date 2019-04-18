package com.android.provaandroidluana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.provaandroidluana.model.Atendimento;
import com.android.provaandroidluana.model.Empresa;
import com.android.provaandroidluana.util.Dados;

import java.util.List;

public class MainEmpresaActivity extends AppCompatActivity {

    private ListView lvEmpresas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_empresa);

        lvEmpresas = findViewById(R.id.lvEmpresas);
        final ArrayAdapter<Empresa> adapterEmpresa = new ArrayAdapter<Empresa>(MainEmpresaActivity.this, R.layout.item_prova, Dados.empresaList);
        lvEmpresas.setAdapter(adapterEmpresa);

        lvEmpresas.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Empresa empresaSelecionada = adapterEmpresa.getItem(position);
                        Intent output = new Intent();
                        output.putExtra( "EMPRESASEL", empresaSelecionada); //Chave, valor
                        setResult(RESULT_OK, output);
                        finish();
                    }
                });
    }
}
