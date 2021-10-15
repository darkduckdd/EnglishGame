package space.darkduck.englishgame.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import space.darkduck.englishgame.OnFragmentListener;
import space.darkduck.englishgame.R;

public class AddFragment extends Fragment {
    private EditText engInput,rusInput;
    private FloatingActionButton saveButton,searchButtonEng,searchButtonRus;
    private OnFragmentListener fragmentSendDataListener;

    private static final int requestPermissionCode=1;
    private int englishCode,russianCode;

    private void init(View view){
        engInput=view.findViewById(R.id.engInput);
        rusInput=view.findViewById(R.id.rusInput);
        saveButton=view.findViewById(R.id.floatingActionSaveButton);
        searchButtonEng=view.findViewById(R.id.floatingActionSearchButtonEng);
        searchButtonRus=view.findViewById(R.id.floatingActionSearchButtonRus);

        englishCode= FirebaseTranslateLanguage.EN;
        russianCode=FirebaseTranslateLanguage.RU;

        engInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) {
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });
        rusInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) {
                            return source;
                        }
                        if (source.toString().matches("[а-яА-Я]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });
        saveButton.setOnClickListener((v)->{
            fragmentSendDataListener.onSendTwoWord(engInput.getText().toString().toLowerCase(),rusInput.getText().toString().toLowerCase());
            engInput.setText("");
            rusInput.setText("");
        });

        searchButtonEng.setOnClickListener((v)->{
            if(engInput.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"enter word ",Toast.LENGTH_LONG).show();
            }else {
                searchEngTranslate(engInput.getText().toString());
            }
        });
        searchButtonRus.setOnClickListener((v)->{
            if(rusInput.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"enter word ",Toast.LENGTH_LONG).show();
            }else {
                searchRusTranslate(rusInput.getText().toString());
            }
        });
    }

    private void searchRusTranslate(String word){
        engInput.setText("Downloading");
        FirebaseTranslatorOptions options=new FirebaseTranslatorOptions.Builder().setSourceLanguage(russianCode).setTargetLanguage(englishCode).build();
        FirebaseTranslator translator= FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions=new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                engInput.setText("Translating");
                translator.translate(word).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        engInput.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"fail translate "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"fail to downloading language modal "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchEngTranslate(String word){
        rusInput.setText("Downloading");
        FirebaseTranslatorOptions options=new FirebaseTranslatorOptions.Builder().setSourceLanguage(englishCode).setTargetLanguage(russianCode).build();
        FirebaseTranslator translator= FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions=new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                rusInput.setText("Translating");
                translator.translate(word).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        rusInput.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"fail translate "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"fail to downloading language modal "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "должен реализовывать интерфейс OnFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add, container, false);
        init(view);
        return view;
    }
}