package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import com.ufrpe.bccsurvivor.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by wallace on 20/11/2017.
 */

public class QuestoesDisciplina extends AppCompatActivity {
    public static String nomeUsuario;
    public static int idUsuario;
    public static String tipo;

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

        ListaDisciplinas l = new ListaDisciplinas(this,disciplinas);

        if(isSmartphone()){
            setContentView(R.layout.questoes_disciplina);
            ListView relacaoDisciplinas = (ListView) findViewById(R.id.listView);
            relacaoDisciplinas.setAdapter(l);

            relacaoDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    GetQuestoes getQuestoes = new GetQuestoes();
                    TextView tv = (TextView) v.findViewById(R.id.itemDisciplina);
                    getQuestoes.execute((String)tv.getText());
                }
            });
        }else{
            setContentView(R.layout.questoes_disciplina_grade);
            GridView relacaoDisciplinas =(GridView) findViewById(R.id.gridView);
            relacaoDisciplinas.setAdapter(l);

            relacaoDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    GetQuestoes getQuestoes = new GetQuestoes();
                    TextView tv = (TextView) v.findViewById(R.id.itemDisciplina);
                    getQuestoes.execute((String)tv.getText());
                }
            });
        }


        Intent i = getIntent();
        nomeUsuario = i.getExtras().getString("nomeUsuario");
        idUsuario = i.getExtras().getInt("idUsuario");
        tipo = i.getExtras().getString("tipo");

    }

    class GetQuestoes extends AsyncTask<String, Void, ArrayList<QuestoesBanco>> {

        ProgressDialog dialog;
        String disciplina;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(QuestoesDisciplina.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected ArrayList<QuestoesBanco> doInBackground(String... strings) {
            disciplina = strings[0];
            ArrayList<QuestoesBanco> questoesBancos;
            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/banco/disciplina_questao/" + URLEncoder.encode(disciplina,"UTF-8");
                URL urlServidor = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setDoInput(true);
                con.connect();
                InputStream is = con.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                JSONObject objeto;
                questoesBancos = new ArrayList<QuestoesBanco>();
                for(int i = 0; i < json.length(); i++){
                    objeto = json.getJSONObject(i);
                    questoesBancos.add(new QuestoesBanco(
                            objeto.getInt("idQuestao"),
                            objeto.getString("perguntaQuestao"),
                            objeto.getString("respostaQuestao"),
                            objeto.getString("disciplinaQuestao"),
                            objeto.getString("assuntoQuestao"),
                            objeto.getInt("autorQuestao")));
                }
                return questoesBancos;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        private String converterStreamToString(final InputStream input) throws IOException {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            final StringBuilder sBuf = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                sBuf.append(line);
            }
            input.close();
            return  sBuf.toString();
        }

        protected void onPostExecute(ArrayList<QuestoesBanco> result) {
            super.onPostExecute(result);
            dialog.dismiss();

            Intent myIntent = new Intent(QuestoesDisciplina.this, MostrarQuestoes.class);
            myIntent.putParcelableArrayListExtra("questoes", result);
            myIntent.putExtra("titulo", disciplina);
            startActivity(myIntent);

        }
    }

    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }
}