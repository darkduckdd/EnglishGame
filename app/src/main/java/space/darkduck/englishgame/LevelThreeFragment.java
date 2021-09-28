package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LevelThreeFragment extends Fragment {

    private OnFragmentListener fragmentSendDataListener;
    private TextView text;
    private EditText edit;
    private Button button;
    private PlayActivity activity;
    private ArrayList<String> listTranslatedWord =new ArrayList<>();
    private int currentPosition;

    private  void init(View view){
        activity=(PlayActivity) getActivity();
        text=view.findViewById(R.id.textView);
        edit=view.findViewById(R.id.editText);
        button=view.findViewById(R.id.button);
        listTranslatedWord.addAll(activity.getTranslatedListWordsLevelThree());
        Random random = new Random();
        currentPosition= random.nextInt(listTranslatedWord.size());
        text.setText(listTranslatedWord.get(currentPosition));
        button.setOnClickListener((v)->{
            Log.d("resultTest",activity.getWord(text.getText().toString()));
            if(edit.getText().toString().equals(activity.getWord(text.getText().toString()))){
                activity.addProgress(10);
                listTranslatedWord.remove(text.getText().toString());
                if(listTranslatedWord.size()==0){
                    fragmentSendDataListener.onSendData("SuccessLevelThree");
                }
               else if(listTranslatedWord.size()==1){
                   currentPosition=0;
                    text.setText(listTranslatedWord.get(currentPosition));
                    edit.setText("");
                }
               else {
                    currentPosition= random.nextInt(listTranslatedWord.size());
                    text.setText(listTranslatedWord.get(currentPosition));
                    edit.setText("");
                }
            }else {
                currentPosition=random.nextInt(listTranslatedWord.size());
                text.setText(listTranslatedWord.get(currentPosition));
                edit.setText("");
                fragmentSendDataListener.onSendData("FailLevelThree");
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
        View view = inflater.inflate(R.layout.fragment_level_three, container, false);
        init(view);
        return view;
    }
}