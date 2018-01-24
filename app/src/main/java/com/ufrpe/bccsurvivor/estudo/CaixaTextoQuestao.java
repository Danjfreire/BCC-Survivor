package com.ufrpe.bccsurvivor.estudo;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 25/11/2017.
 */

public class CaixaTextoQuestao extends DialogFragment implements EditText.OnEditorActionListener{
    private EditText editText;

    public interface EditNameDialogListener {
        void receberConteudo(String texto, int id);
    }

    public static CaixaTextoQuestao newInstace(String titulo,int id){
        CaixaTextoQuestao c = new CaixaTextoQuestao();
        Bundle argumentos = new Bundle();
        argumentos.putString("titulo", titulo);
        argumentos.putInt("id",id);
        c.setArguments(argumentos);
        return c;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.caixa_texto_questao, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv =(TextView) view.findViewById(R.id.tituloCaixa);
        Button button = (Button) view.findViewById(R.id.botaoCaixa);
        editText = (EditText) view.findViewById(R.id.textoCaixa);
        editText.setOnEditorActionListener(this);
        tv.setText(getArguments().getString("titulo"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CriarPergunta listener = (CriarPergunta) getActivity();
                listener.receberConteudo(editText.getText().toString(), getArguments().getInt("id"));
                dismiss();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
