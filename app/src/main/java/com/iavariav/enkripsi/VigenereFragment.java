package com.iavariav.enkripsi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class VigenereFragment extends Fragment {

    private EditText edtPlain;
    private EditText edtKey;
    //    private RadioButton encrypt;
//    private RadioButton decrypt;
    private Switch sw;
    private Button resetButton;
    private Button runButton;
    private EditText edtResult;
    public VigenereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vigenere, container, false);
        initView(view);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encryptOrDecryptUsingKeyphraseOnClick(view);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPlain.setText("");
                edtKey.setText("");
                edtResult.setText("");
            }
        });


        return view;
    }


    public void encryptOrDecryptUsingKeyphraseOnClick(View view) {
        // Local variables.
        String shiftedString;

        if (view.getId() == R.id.runButton) {

            // Close the soft keyboard when the user hits the run button.
            // Reference: https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/34972848
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            shiftedString = null;
            String textFromCipher = this.edtPlain.getText().toString();
            String keyphraseFromCipher = this.edtKey.getText().toString();

            if (!checkForEmptyInvalidInput(textFromCipher, keyphraseFromCipher)) {
                // Input parameters are all correct.
                if (sw.isChecked()) {
                    // Decrypt option.
                    shiftedString = this.decryptAlgorithm(textFromCipher, keyphraseFromCipher);
                } else {
                    // Encrypt option.
                    shiftedString = this.encryptAlgorithm(textFromCipher, keyphraseFromCipher);
                }
                this.edtResult.setText(shiftedString.toString());
            }
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (sw.isChecked()) {
                    String result = edtResult.getText().toString();
                    edtPlain.setText(result);
                    edtResult.setText("");
                    sw.setText("Decrypt");
                } else {
                    String result = edtResult.getText().toString();
                    edtPlain.setText(result);
                    edtResult.setText("");
                    sw.setText("Encrypt");
                }
            }
        });

    } // end of encryptOrDecryptUsingKeyphrase method.

    @SuppressLint("NewApi")
    private String encryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char) ((upper + keyphrase.charAt(j) - 130) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char) ((upper + keyphrase.charAt(j) - 130) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of encryptAlgorithm method.

    @SuppressLint("NewApi")
    private String decryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char) ((upper - keyphrase.charAt(j) + 26) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char) ((upper - keyphrase.charAt(j) + 26) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of decryptAlgorithm method.

    private boolean checkForEmptyInvalidInput(String text, String keyphrase) {
        boolean isError = false;

        // Current text has no alphabetical characters. Text must at least one alphabetical character.
        if (!text.matches(".*[a-zA-Z]+.*")) {
            this.edtPlain.setError("Nothing to encode/decode");
            isError = true;
        }

        // Current keyphrase is either null or empty.
        if (null == keyphrase || keyphrase.isEmpty()) {
            this.edtKey.setError("Key required");
            isError = true;
        }

        // Non-alphabetical character(s) in keyphrase. Keyphrase must only contain alphabetical characters.
        boolean valid = this.checkIfKeyphraseValid(keyphrase);
        if (!valid) {
            // Non-alphabetical character(s) in keyphrase.
            this.edtKey.setError("Non-alphabetical character(s) in key");
            isError = true;
        }
        return isError;
    } // end of checkForEmptyInvalidInput.

    @SuppressLint("NewApi")
    private boolean checkIfKeyphraseValid(String keyphrase) {

        boolean valid = true;

        for (int z = 0; z < keyphrase.length(); ++z) {
            char c = keyphrase.charAt(z);
            if (!Character.isAlphabetic(c)) {
                valid = false;
                break;
            }
        }
        return valid;
    } // end of checkIfKeyphraseValid.

    private void initView(View view) {
        edtPlain = view.findViewById(R.id.edtPlain);
        edtKey = view.findViewById(R.id.edtKey);
        sw = view.findViewById(R.id.sw);
        runButton = view.findViewById(R.id.runButton);
        resetButton = view.findViewById(R.id.resetButton);
        edtResult = view.findViewById(R.id.edtResult);
    }

}
