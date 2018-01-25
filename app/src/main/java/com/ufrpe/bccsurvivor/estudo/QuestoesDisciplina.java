package com.ufrpe.bccsurvivor.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 20/11/2017.
 */

public class QuestoesDisciplina extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        String[] disciplinas = {
                getString(R.string.calculo_ni),
                getString(R.string.calculo_nii),
                getString(R.string.introducao_computacao),
                getString(R.string.intro_prog_i),
                getString(R.string.mat_dis_i),
                getString(R.string.estrutura_dados),
                getString(R.string.algebra_linear),
                getString(R.string.intro_prog),
                getString(R.string.mat_dis_ii),
                getString(R.string.met_cient),
                getString(R.string.cir_dig),
                getString(R.string.est_expl),
                getString(R.string.fis_apl),
                getString(R.string.proj_analise),
                getString(R.string.teori_comp),
                getString(R.string.arqui_org),
                getString(R.string.banco_dados),
                getString(R.string.enge_soft),
                getString(R.string.para_prog),
                getString(R.string.rede_comp),
                getString(R.string.compiladores),
                getString(R.string.int_art),
                getString(R.string.proj_desen),
                getString(R.string.sis_distr),
                getString(R.string.sist_oper)};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questoes_disciplina);

        ListaDisciplinas l = new ListaDisciplinas(this,disciplinas);

        ListView gradeQuestoes = (ListView) findViewById(R.id.listView);
        gradeQuestoes.setAdapter(l);

        gradeQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent myIntent = new Intent(QuestoesDisciplina.this, MostrarQuestoes.class);
                myIntent.putExtra("titulo", ((TextView)v.findViewById(R.id.itemDisciplina)).getText());
                startActivity(myIntent);
            }
        });
    }
}