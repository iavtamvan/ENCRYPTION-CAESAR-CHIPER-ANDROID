package com.iavariav.enkripsi.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.iavariav.enkripsi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasarChiperFragment extends Fragment {
    private EditText edtPlain;
    private EditText edtEncrypt;
    //    private TextView tvHasil;
    private Button btnSubmit;
    private ImageView ivPlus;
    private ImageView ivMinus;

    String newString, inputString, text;
    //    int key;
    private EditText edtKey;
    private Switch sw;
    private int key = 0;

    public CasarChiperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_casar_chiper, container, false);
        initView(view);


        edtKey.setText("" + key);
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtKey.getText().toString().isEmpty()) {
                    resetKey();
                    tambahKey();
                } else {
                    tambahKey();
                }
            }
        });
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtKey.getText().toString().isEmpty()) {
                    resetKey();
                    kurangKey();
                } else {
                    kurangKey();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw.isChecked()) {
                    Reverse_Caesar_cipher_coding_method(Integer.parseInt(edtKey.getText().toString().trim()));
                } else {
                    Caesar_cipher_coding_method(Integer.parseInt(edtKey.getText().toString().trim()));
                }
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw.isChecked()){
                    edtPlain.setText("");
                    sw.setText("Decrypt");
                }else{
                    edtEncrypt.setText("");
                    sw.setText("Encrypt");
                }
            }
        });

        return view;
    }

    private void resetKey() {
        key = 0;
        edtKey.setText("" + key);
    }

    private void tambahKey() {
        try {
            key = Integer.parseInt(edtKey.getText().toString());
            if (key == 26){
                edtKey.setError("maximal 26");
            } else {
                key = key + 1;
                edtKey.setText("" + key);
            }

        } catch (NumberFormatException nfe) {
            resetKey();
            tambahKey();
        }
    }

    private void kurangKey() {
        try {
            key = Integer.parseInt(edtKey.getText().toString());
            if (key == 0) {
                Toast.makeText(getActivity(), "Key tidak bisa kurang dari 0", Toast.LENGTH_SHORT).show();
            } else {
                key = key - 1;
                edtKey.setText("" + key);
            }
        } catch (NumberFormatException nfe) {
            resetKey();
            kurangKey();
        }
    }

    private void Caesar_cipher_coding_method(int shift) {
        Editable msg = edtPlain.getText();
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + shift);
            if (c > 'z' && c > 'Z')
                s += (char) (msg.charAt(x) - (26 - shift));
            else
                s += (char) (msg.charAt(x) + shift);


        }
        edtEncrypt.setText(s);
    }

    private void Reverse_Caesar_cipher_coding_method(int shift) {
        int shifMinus = shift * -1;
        Editable msg = edtEncrypt.getText();
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + shifMinus);
            if (c > 'z' && c > 'Z')
                s += (char) (msg.charAt(x) - (26 - shifMinus));
            else
                s += (char) (msg.charAt(x) + shifMinus);


        }
        edtPlain.setText(s);
    }

    private void initView(View view) {
        edtPlain = view.findViewById(R.id.edt_plain);
        edtEncrypt = view.findViewById(R.id.edt_encrypt);
//        tvHasil view.= findViewById(R.id.tv_hasil);
        btnSubmit = view.findViewById(R.id.btn_submit);
        edtKey = view.findViewById(R.id.edt_key);
        sw = view.findViewById(R.id.sw);
        ivPlus = view.findViewById(R.id.icon_plus);
        ivMinus = view.findViewById(R.id.icon_minus);
    }

}
