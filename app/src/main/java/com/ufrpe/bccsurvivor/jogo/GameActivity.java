package com.ufrpe.bccsurvivor.jogo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrpe.bccsurvivor.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView tv;
    private LevelController levelControl;
    private RestController restControl;
    private int levelAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            tv = (TextView) findViewById(R.id.disciplinajogo);
            tv.setText(savedInstanceState.getString("disciplina"));
        }else{
            levelAtual = 1; //seta na primeira fase
        }

        tv = (TextView) findViewById(R.id.disciplinajogo);
        levelControl = new LevelController();
        restControl = new RestController();


        tv.setText(levelControl.loadLevel(levelAtual,getApplicationContext()));

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "selecionou alternativa A", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RestController extends AsyncTask<String, Void, List<QuestaoJogo>> {

        @Override
        protected List<QuestaoJogo> doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Gson gson = new GsonBuilder().create();
                List<QuestaoJogo> crimes = new ArrayList<>();
                crimes = Arrays.asList(gson.fromJson(reader, QuestaoJogo[].class));
                return crimes;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<QuestaoJogo> crimes) {

        }
    }

}
