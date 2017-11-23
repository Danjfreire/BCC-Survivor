package com.wallace.bcc_survivor_questoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by wallace on 20/11/2017.
 */

public class QuestoesDisciplina extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questoes_disciplina);

        ListaDisciplinas l = new ListaDisciplinas(this);

        GridView gradeQuestoes = (GridView) findViewById(R.id.gridView);
        gradeQuestoes.setAdapter(l);

        gradeQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent myIntent = new Intent(QuestoesDisciplina.this, MostrarQuestoes.class);
                startActivity(myIntent);
            }
        });
    }
}
