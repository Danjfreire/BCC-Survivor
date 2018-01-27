package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private Player player;

    private Button altA;
    private Button altB;
    private Button altC;
    private Button altD;
    private Button btnPular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        levelControl = new LevelController();
        gameControl = new GameController();

        Intent intent = getIntent();
        player = (Player) intent.getParcelableExtra("player");
        tvDisciplina = (TextView) findViewById(R.id.disciplinajogo);
        levelAtual = player.getFaseAtual();
        gameControl.setNumVidas(player.getNumVidas());
        gameControl.setNumPulos(player.getPulos());
        //gameControl.setScore(player.getScore());

        //if (savedInstanceState != null) {
        //} else {
        //    levelAtual = 1; //seta na primeira fase
        //}

        tvDisciplina = (TextView) findViewById(R.id.disciplinajogo);
        tvDisciplina.setText(levelControl.loadLevel(levelAtual, getApplicationContext()));

        restControl = new RestController();
        restControl.execute();

        loadButtons();

    }

    private void atualizarFase() {
        //fazer alguma animação
        //recarregar questoes

        acertou = 0;
        levelAtual++;
        tvDisciplina.setText(levelControl.loadLevel(levelAtual, getApplicationContext()));
        findViewById(R.id.alternativaAresult).setVisibility(View.INVISIBLE);
        findViewById(R.id.alternativaBresult).setVisibility(View.INVISIBLE);
        findViewById(R.id.alternativaCresult).setVisibility(View.INVISIBLE);
        findViewById(R.id.alternativaDresult).setVisibility(View.INVISIBLE);

        restControl = new RestController();
        restControl.execute();

    }

    private void loadButtons() {
        altA = (Button) findViewById(R.id.alternativaA);
        altB = (Button) findViewById(R.id.alternativaB);
        altC = (Button) findViewById(R.id.alternativaC);
        altD = (Button) findViewById(R.id.alternativaD);
        btnPular = (Button) findViewById(R.id.btnPular);

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

        btnPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pular para proxima questao da disciplina
                if (gameControl.getNumPulos() > 0) {
                    carregarQuestao();
                    gameControl.diminuirPulos(findViewById(R.id.pulos));
                    btnPular.setBackground(getDrawable(R.drawable.btn_grey));
                }else
                    Toast.makeText(GameActivity.this, "Não possui pulos restante", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarResposta(Button alt) {
        if (alt.getText().equals(questaoAtual.getResposta())) {
            acertou++;
            gameControl.aumentarScore(findViewById(R.id.score));
            animacao();
            if (acertou == 3){
                Intent i =new Intent(GameActivity.this,GameOverActivity.class);
                i.putExtra("player",player);
                startActivity(i);
            }
        } else {
            gameControl.diminuirVida(findViewById(R.id.vidas));
            if (gameControl.getNumVidas() == 0) {
                Intent i =new Intent(GameActivity.this,GameOverActivity.class);
                i.putExtra("player",player);
                startActivity(i);
            }
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                atualizarFase();
            }
        }, 2000);

    }

    private void animacao() {
        ImageView resposta;

        if(altA.getText().equals(questaoAtual.getResposta())){
            resposta = (ImageView) findViewById(R.id.alternativaAresult);
            resposta.setBackground(getDrawable(R.drawable.check));
        }
        else if(altB.getText().equals(questaoAtual.getResposta())){
            resposta = (ImageView) findViewById(R.id.alternativaBresult);
            resposta.setBackground(getDrawable(R.drawable.check));
        }
        else if(altC.getText().equals(questaoAtual.getResposta())){
            resposta = (ImageView) findViewById(R.id.alternativaCresult);
            resposta.setBackground(getDrawable(R.drawable.check));
        }
        else{
            resposta = (ImageView) findViewById(R.id.alternativaCresult);
            resposta.setBackground(getDrawable(R.drawable.check));
        }

        findViewById(R.id.alternativaAresult).setVisibility(View.VISIBLE);
        findViewById(R.id.alternativaBresult).setVisibility(View.VISIBLE);
        findViewById(R.id.alternativaCresult).setVisibility(View.VISIBLE);
        findViewById(R.id.alternativaDresult).setVisibility(View.VISIBLE);

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
                url = new URL("http://"+getString(R.string.ip)+":5000/bccsurvivor/disciplina?disciplina=" + tvDisciplina.getText());
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

    @Override
    protected void onStop() {
        Log.v("STOP","ENTROU NO ONSTOP");
        player.setScoreRecorde(5000);
        StateController stateC = new StateController();
        stateC.execute(player);
        super.onStop();
    }

    private class StateController extends AsyncTask<Player, Void, Void>{

        @Override
        protected Void doInBackground(Player... params) {

            OkHttpClient client = new OkHttpClient();

            Gson gson = new GsonBuilder().create();

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(params[0],Player.class));
            Log.v("json",gson.toJson(params[0],Player.class));
            Request request = new Request.Builder()
                    .url("http://"+getString(R.string.ip)+":5000/user/savestate")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.v("Response",response.body().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Response response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
