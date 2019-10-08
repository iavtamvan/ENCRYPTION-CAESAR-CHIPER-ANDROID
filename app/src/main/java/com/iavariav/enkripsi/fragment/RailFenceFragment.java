package com.iavariav.enkripsi.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.iavariav.enkripsi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RailFenceFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private ImageView iconPalinText;
    private TextInputLayout inputLayPlain;
    private EditText edtPlain;
    private ImageView iconEncrypt;
    private TextInputLayout inputLayPlain2;
    private EditText edtEncrypt;
    private ImageView iconVerKey;
    private ImageView iconMinus;
    private ImageView iconPlus;
    private EditText edtKey;
    private Switch sw;
    private Button btnSubmit;
    private ImageView userProfilePhoto;
    private TextView loginTitle;
    private Button resetButton;

    private int key = 1;

    public RailFenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rail_fence, container, false);
        initView(view);
        edtKey.setText("" + key);
        edtEncrypt.setEnabled(false);
        iconPlus.setOnClickListener(new View.OnClickListener() {
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
        iconMinus.setOnClickListener(new View.OnClickListener() {
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
                    decryption(edtEncrypt.getText().toString().trim(), Integer.parseInt(edtKey.getText().toString().trim()));
                } else {
                    encryption(edtPlain.getText().toString().trim(), Integer.parseInt(edtKey.getText().toString().trim()));
                }


            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPlain.setText("");
                edtKey.setText("");
                edtEncrypt.setText("");
            }
        });


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw.isChecked()) {
                    edtPlain.setText("");
                    sw.setText("Decrypt");
                } else {
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
            if (key == 26) {
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


    private void initView(View view) {
        relativeLayout = view.findViewById(R.id.relativeLayout);
        iconPalinText = view.findViewById(R.id.icon_palin_text);
        inputLayPlain = view.findViewById(R.id.inputLay_plain);
        edtPlain = view.findViewById(R.id.edt_plain);
        iconEncrypt = view.findViewById(R.id.icon_encrypt);
        inputLayPlain2 = view.findViewById(R.id.inputLay_plain_2);
        edtEncrypt = view.findViewById(R.id.edt_encrypt);
        iconVerKey = view.findViewById(R.id.icon_ver_key);
        iconMinus = view.findViewById(R.id.icon_minus);
        iconPlus = view.findViewById(R.id.icon_plus);
        edtKey = view.findViewById(R.id.edt_key);
        sw = view.findViewById(R.id.sw);
        btnSubmit = view.findViewById(R.id.btn_submit);
        userProfilePhoto = view.findViewById(R.id.user_profile_photo);
        loginTitle = view.findViewById(R.id.login_title);
        resetButton = view.findViewById(R.id.resetButton);
    }

    private void encryption(String plainText, int mod) {
        String ciphertext = ""; // blank
        String[] lines = new String[mod]; // arrayString new object Modulus
        for (int i = 0; i < mod; i++) { // looping for mulai dari 1 dengan sesuai modulus.
            lines[i] = ""; // lines eksekusi sampai dengan modulus
        }
        int line = 0;
        int direction = 1; //moving positively or negatively
        for (int i = 0; i < plainText.length(); i++) {
            lines[line] = lines[line] + plainText.charAt(i); // line + characker dari looping i
            line = line + direction; // hasil dari line
            if (line == 0 || line == mod - 1) { // line modulus - q
                direction = direction * (-1);
            }
        }
        for (String l : lines) {
            ciphertext = ciphertext + l; // ciper menghasilkan text
        }
        edtEncrypt.setText(ciphertext); // mengeluarkan hasil enkripsi.
    }

    private void decryption(String encryptText, int mod) {
        char[] plaintext = new char[encryptText.length()];
        String[] lines = splitLines(mod, encryptText.length());

        for (int i = 0; i < mod; i++) {
            //System.out.println("#" + i + " : " + lines[i]);
            int startbreak = 0;
            for (int j = 0; j < i; j++) {
                startbreak = startbreak + lines[j].length();
            }
            int endbreak = startbreak + lines[i].length();
            lines[i] = encryptText.substring(startbreak, endbreak);
            //System.out.println(startbreak + " to " + endbreak);
            //System.out.println("#" + i + " : " + lines[i]);
        }

        int line = 0;
        int direction = 1; //moving positively or negatively
        int[] pos = new int[lines.length];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = 0;
        }
        for (int i = 0; i < encryptText.length(); i++) {
            plaintext[i] = lines[line].charAt(pos[line]);
            pos[line]++;
            line = line + direction;
            if (line == 0 || line == mod - 1) {
                direction = direction * (-1);
            }
        }
        edtPlain.setText(new String(plaintext));

    }

    public static String[] splitLines(int key, int length) {
        String[] lines = new String[key];
        for (int i = 0; i < key; i++) {
            lines[i] = "";
        }
        int line = 0;
        int direction = 1; //moving positively or negatively
        for (int i = 0; i < length; i++) {
            lines[line] = lines[line] + "?";
            line = line + direction;
            if (line == 0 || line == key - 1) {
                direction = direction * (-1);
            }
        }
        return lines;
    }

}
