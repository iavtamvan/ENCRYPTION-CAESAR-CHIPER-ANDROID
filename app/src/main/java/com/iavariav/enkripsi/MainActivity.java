package com.iavariav.enkripsi;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtPlain;
    private EditText edtEncrypt;
    private TextView tvHasil;
    private Button btnSubmit;

    String newString, inputString;
    int key;
    private EditText edtKey;
    private Switch sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

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
    }


    private void Caesar_cipher_coding_method(int shift) {
        Editable msg = edtPlain.getText();
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + shift);
            if (c > 'z')
                s += (char) (msg.charAt(x) - (26 - shift));
            else
                s += (char) (msg.charAt(x) + shift);


        }
        edtEncrypt.setText(s);
    }

    private void Reverse_Caesar_cipher_coding_method(int shift) {
        int iav = shift * -1;
        Editable msg = edtEncrypt.getText();
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + iav);
            if (c > 'z')
                s += (char) (msg.charAt(x) - (26 - iav));
            else
                s += (char) (msg.charAt(x) + iav);


        }
        edtPlain.setText(s);
    }

    private void initView() {
        edtPlain = findViewById(R.id.edt_plain);
        edtEncrypt = findViewById(R.id.edt_encrypt);
        tvHasil = findViewById(R.id.tv_hasil);
        btnSubmit = findViewById(R.id.btn_submit);
        edtKey = findViewById(R.id.edt_key);
        sw = findViewById(R.id.sw);
    }
}
