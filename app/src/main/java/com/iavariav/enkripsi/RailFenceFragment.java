package com.iavariav.enkripsi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;


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

    private RailFenceBasic railFenceBasic;

    public RailFenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rail_fence, container, false);
        initView(view);

        railFenceBasic = new RailFenceBasic();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               encryption(edtPlain.getText().toString().trim(), 3);
            }
        });


        return view;
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
    }

    private void encryption(String plainText, int depth) {
        String ciphertext = "";
        String[] lines = new String[depth];
        for(int i = 0; i < depth; i++){lines[i] = "";}
        int line = 0;
        int direction = 1; //moving positively or negatively
        for(int i=0; i<plainText.length(); i++)
        {
            lines[line] = lines[line]+plainText.charAt(i);
            line = line + direction;
            if(line == 0 || line == depth-1)
            {
                direction = direction*(-1);
            }
        }
        for(String l : lines){ciphertext = ciphertext + l;}
        edtEncrypt.setText(ciphertext);
    }

    private void decryption(String encryptText, int mod){
        char[] plaintext = new char[encryptText.length()];
        String[] lines = splitLines(mod, encryptText.length());

        for(int i = 0; i < mod; i++)
        {
            //System.out.println("#" + i + " : " + lines[i]);
            int startbreak = 0;
            for(int j = 0; j < i; j++)
            {
                startbreak = startbreak+lines[j].length();
            }
            int endbreak = startbreak + lines[i].length();
            lines[i] = encryptText.substring(startbreak, endbreak);
            //System.out.println(startbreak + " to " + endbreak);
            //System.out.println("#" + i + " : " + lines[i]);
        }

        int line = 0;
        int direction = 1; //moving positively or negatively
        int[] pos = new int[lines.length];
        for(int i=0; i<pos.length;i++){pos[i] = 0;}
        for(int i=0; i<encryptText.length(); i++)
        {
            plaintext[i] = lines[line].charAt(pos[line]);
            pos[line]++;
            line = line + direction;
            if(line == 0 || line == mod-1)
            {
                direction = direction*(-1);
            }
        }

//        return new String(plaintext);
        edtEncrypt.setText(new String(plaintext));

    }

    public static String[] splitLines(int key, int length)
    {
        String[] lines = new String[key];
        for(int i = 0; i < key; i++){lines[i] = "";}
        int line = 0;
        int direction = 1; //moving positively or negatively
        for(int i=0; i<length; i++)
        {
            lines[line] = lines[line]+"?";
            line = line + direction;
            if(line == 0 || line == key-1)
            {
                direction = direction*(-1);
            }
        }
        return lines;
    }

}
