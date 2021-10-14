package space.darkduck.englishgame.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import space.darkduck.englishgame.OnFragmentListener;
import space.darkduck.englishgame.R;

public class AddFragment extends Fragment {
    private EditText engInput,rusInput;
    private Button saveButton;
    private OnFragmentListener fragmentSendDataListener;

    private void init(View view){
        engInput=view.findViewById(R.id.engInput);
        rusInput=view.findViewById(R.id.rusInput);
        saveButton=view.findViewById(R.id.saveButton);

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