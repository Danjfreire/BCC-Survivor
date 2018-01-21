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
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvDisciplina;
    private LevelController levelControl;
    private GameController gameControl;
    private RestController restControl;
    private int levelAtual;
    private List<QuestaoJogo> questoes;
    private int acertou;
    private QuestaoJogo questaoAtual;
    private static Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            tvDisciplina = (TextView) findViewById(R.id.disciplinajogo);
            tvDisciplina.setText(savedInstanceState.getString("disciplina"));
        } else {
            levelAtual = 1; //seta na primeira fase
        }

        levelControl = new LevelController();
        gameControl = new GameController();

        tvDisciplina = (TextView) findViewById(R.id.disciplinajogo);
        tvDisciplina.setText(levelControl.loadLevel(levelAtual, getApplicationContext()));

        restControl = new RestController();
        restControl.execute();

        loadButtons();

    }

    private void atualizarFase(){
        //fazer alguma animação
        //recarregar questoes
        acertou = 0;
        levelAtual++;
        tvDisciplina.setText(levelControl.loadLevel(levelAtual,getApplicationContext()));
    }

    private void loadButtons() {
        final Button altA = (Button) findViewById(R.id.alternativaA);
        final Button altB = (Button) findViewById(R.id.alternativaB);
        final Button altC = (Button) findViewById(R.id.alternativaC);
        final Button altD = (Button) findViewById(R.id.alternativaD);

        altA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarResposta(altA);
            }
        });

        altB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarResposta(altB);
            }
        });

        altC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarResposta(altC);
            }
        });

        altD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarResposta(altD);
            }
        });
    }

    private void verificarResposta(Button alt) {
        if (alt.getText().equals(questaoAtual.getResposta())) {
            acertou++;
            gameControl.aumentarScore(findViewById(R.id.score));
            if (acertou == 1) //mudar para 3
                atualizarFase();
        } else {
            gameControl.diminuirVida(findViewById(R.id.vidas));
            if (gameControl.getNumVidas() == 0) {
                //perdeu, criar tela de derrota
            }
        }
    }

    private void inserirQuestoes(List<QuestaoJogo> questoes) {
        this.questoes = new ArrayList<>(questoes);
        carregarQuestao();
    }

    private void carregarQuestao() {

        int random = this.random.nextInt(this.questoes.size());
        QuestaoJogo questao = questoes.get(random);
        questaoAtual = questao;

        ArrayList<String> alternativas = new ArrayList<>();
        alternativas.add(questao.getAlternativa1());
        alternativas.add(questao.getAlternativa2());
        alternativas.add(questao.getAlternativa3());
        alternativas.add(questao.getResposta());

        TextView tv = (TextView) findViewById(R.id.questao);
        tv.setText(questao.getTexto());

        for (int i = 0; i < 4; i++) {
            random = this.random.nextInt(alternativas.size());

            if (i == 0) {
                tv = (TextView) findViewById(R.id.alternativaA);
                tv.setText(alternativas.get(random));
            } else if (i == 1) {
                tv = (TextView) findViewById(R.id.alternativaB);
                tv.setText(alternativas.get(random));
            } else if (i == 2) {
                tv = (TextView) findViewById(R.id.alternativaC);
                tv.setText(alternativas.get(random));
            } else if (i == 3) {
                tv = (TextView) findViewById(R.id.alternativaD);
                tv.setText(alternativas.get(random));
            }
            alternativas.remove(random);
        }
        questoes.remove(questao);

    }

    private class RestController extends AsyncTask<String, Void, List<QuestaoJogo>> {

        @Override
        protected List<QuestaoJogo> doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL("http://10.0.2.2:5000/bccsurvivor/disciplina?disciplina=" + tvDisciplina.getText());
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Gson gson = new GsonBuilder().create();
                List<QuestaoJogo> questoes = new ArrayList<>();
                questoes = Arrays.asList(gson.fromJson(reader, QuestaoJogo[].class));
                return questoes;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<QuestaoJogo> questoes) {

            inserirQuestoes(questoes);
        }
    }


}
