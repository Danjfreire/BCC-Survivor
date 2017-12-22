package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

public class PlayerInfoActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        if(savedInstanceState != null){
            Intent i = getIntent();
            Player p = (Player) i.getParcelableExtra("player");
            tv = (TextView) findViewById(R.id.tv_player);
            tv.setText(p.getNickname());
        }
    }
}
