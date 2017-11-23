package com.wallace.bcc_survivor_questoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botaoQuestoes = (Button) findViewById(R.id.botaoQuestoes);
        botaoQuestoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, QuestoesDisciplina.class);
                startActivity(myIntent);
            }
        });
    }
}
