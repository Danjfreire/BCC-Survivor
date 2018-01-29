package com.ufrpe.bccsurvivor.jogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ufrpe.bccsurvivor.R;

import java.util.List;

/**
 * Created by Daniel on 25/11/2017.
 */

public class AdapterRanking extends ArrayAdapter<Player> {


    public AdapterRanking(Context context, int resource, List<Player>players) {
        super(context, resource,players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view = convertView;
        ViewHolderRanking holder;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.item_ranking, null);
            holder = new ViewHolderRanking(view);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolderRanking)view.getTag();
        }

        Player player = getItem(position);
        holder.name.setText(player.getNickname());
        holder.score.setText(String.valueOf(player.getScoreRecorde()));

//        if (player != null) {
//            TextView name = (TextView) view.findViewById(R.id.playername);
//            TextView score = (TextView) view.findViewById(R.id.playerscore);
//
//            if (name != null) {
//                name.setText(player.getNickname());
//            }
//
//            if (score != null) {
//                score.setText(String.valueOf(player.getScore()));
//            }
//
//        }

        return view;
    }
}

