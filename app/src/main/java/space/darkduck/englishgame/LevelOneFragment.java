package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LevelOneFragment extends Fragment {

    TextView textView;
    private OnFragmentListener fragmentSendDataListener;
    public void SetText(String text){
        if(textView!=null){
            textView.setText(text);
        }
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
        View view = inflater.inflate(R.layout.fragment_level_one, container, false);
        Button rightButton, mistakeButton;
         textView = view.findViewById(R.id.text);

        rightButton = view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener((v) -> {
            fragmentSendDataListener.OnSendData("cat");
            textView.setText("Work");
        });
        mistakeButton = view.findViewById(R.id.mistakeButton);
        mistakeButton.setOnClickListener((v) -> {
            fragmentSendDataListener.OnSendData("dog");
            textView.setText("dontWork");
        });
        return view;
    }
}