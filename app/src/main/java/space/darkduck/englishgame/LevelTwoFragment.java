package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class LevelTwoFragment extends Fragment {
    private OnFragmentListener fragmentSendDataListener;
    private Button button1, button2, button3, button4;
    private TextView text;
    private PlayActivity activity;
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<String> listWord = new ArrayList();
    private ArrayList<String> listTranslateWord=new ArrayList();
    private  int currentPosition;

    private void setTextButtons(List<Button> buttonList, String translate) {
        Set<String> strGenerated=new HashSet<>();
        Random r = new Random();
        strGenerated.add(translate);
        while (strGenerated.size() < buttonList.size()) {
            strGenerated.add(listTranslateWord.get(r.nextInt(listTranslateWord.size())));
        }
        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(strGenerated);
        Iterator<String> strIterator = myTreeSet.iterator();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText(strIterator.next());
        }
    }

    void checkClick(Button button) {
       /* if (button.getText().equals(activity.getTranslateWord(text.getText().toString()))) {
            activity.addProgress(10);
            listWord.remove(text.getText());
            if(listWord.size()==0){
                fragmentSendDataListener.onSendData("SuccessLevelTwo");
            }else if(listWord.size()==1){
                currentPosition=0;
                text.setText(listWord.get(currentPosition));
                setTextButtons(buttons,activity.getTranslateWord(text.getText().toString()));
            }
            else {
                Random random = new Random();
                currentPosition= random.nextInt(listWord.size());
                text.setText(listWord.get(currentPosition));
                setTextButtons(buttons,activity.getTranslateWord(text.getText().toString()));
            }
        } else {
            //fragmentSendDataListener.onSendData("FailLevelTwo");
            Random random = new Random();
            currentPosition= random.nextInt(listWord.size());
            text.setText(listWord.get(currentPosition));
            setTextButtons(buttons,activity.getTranslateWord(text.getText().toString()));
        }*/
    }

    private void init(View view){
        text = view.findViewById(R.id.textView);
        button1 = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        buttons.addAll(Arrays.asList(button1, button2, button3, button4));

        activity = (PlayActivity) getActivity();
       /* listWord.addAll(activity.getListWordsLevelTwo());
        listTranslateWord.addAll(activity.getTranslatedListWordsLevelTwo());
        Random random = new Random();
        currentPosition= random.nextInt(listWord.size());
        text.setText(listWord.get(currentPosition));
        setTextButtons(buttons,activity.getTranslateWord(text.getText().toString()));*/
        for (Button btn : buttons) {
            btn.setOnClickListener((v) -> {
                checkClick(btn);
            });
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
        View view = inflater.inflate(R.layout.fragment_level_two, container, false);
        init(view);
        return view;
    }

}