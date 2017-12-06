package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bccsurvivor.R;

public class GameActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState!=null){
            tv = (TextView)findViewById(R.id.disciplinajogo);
            tv.setText(savedInstanceState.getString("disciplina"));
        }

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this,"selecionou alternativa A",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
