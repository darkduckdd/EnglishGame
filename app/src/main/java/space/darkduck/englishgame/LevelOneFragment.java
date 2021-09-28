package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LevelOneFragment extends Fragment {

    private TextView textView;
    private OnFragmentListener fragmentSendDataListener;
    private PlayActivity activity;
    private ArrayList<String> listWord = new ArrayList();
    private ArrayList<String> listTranslateWord=new ArrayList();
    private Button rightButton, mistakeButton,nextButton;
    private int currentPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_one, container, false);
        init(view);

        rightButton.setOnClickListener((v) -> {
            activity.addProgress(10);
            listWord.remove(textView.getText());
            if(listWord.size()==0){
                fragmentSendDataListener.onSendData("SuccessLevelOne");
            }else if(listWord.size()==1){
               currentPosition=0;
               textView.setText(listWord.get(currentPosition));
            }
            else {
                Random random = new Random();
                currentPosition= random.nextInt(listWord.size());
                textView.setText(listWord.get(currentPosition));
            }
        });

        mistakeButton.setOnClickListener((v) -> {
            textView.setText(listTranslateWord.get(currentPosition));
            activeNextButton(true,View.VISIBLE,View.INVISIBLE);
        });
        nextButton.setOnClickListener((v) -> {
            Random random = new Random();
            currentPosition= random.nextInt(listWord.size());
            textView.setText(listWord.get(currentPosition));
           activeNextButton(false,View.INVISIBLE,View.VISIBLE);
        });
        textView.setText(listWord.get(currentPosition));
        return view;
    }

    private void init(View view){
        textView = view.findViewById(R.id.text);
        mistakeButton = view.findViewById(R.id.mistakeButton);
        rightButton = view.findViewById(R.id.rightButton);
        nextButton=view.findViewById(R.id.nextButton);
        activity=(PlayActivity) getActivity();
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);
        //listWord.addAll(activity.getListWordLevelOne());
        //listTranslateWord= activity.getTranslatedListWordLevelOne();
        Random random = new Random();
        currentPosition= random.nextInt(listWord.size());
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
    private void activeNextButton(boolean isActive,int visibleNext,int visibleOther){
        rightButton.setEnabled(!isActive);
        rightButton.setVisibility(visibleOther);
        mistakeButton.setEnabled(!isActive);
        mistakeButton.setVisibility(visibleOther);
        nextButton.setVisibility(visibleNext);
        nextButton.setEnabled(isActive);
    }
}