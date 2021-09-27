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
    private ArrayList<String> rusWords = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();

    private void setTextButtons(List<Button> buttonList, String translate) {
        Set<String> strGenerated=new HashSet<>();
        Random r = new Random();
        strGenerated.add(translate);
        while (strGenerated.size() < buttonList.size()) {
            strGenerated.add(rusWords.get(r.nextInt(rusWords.size())));
        }
        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(strGenerated);
        Iterator<String> strIterator = myTreeSet.iterator();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText(strIterator.next());
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
        text = view.findViewById(R.id.textView);
        button1 = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        buttons.addAll(Arrays.asList(button1, button2, button3, button4));
        for (Button btn : buttons) {
            btn.setOnClickListener((v) -> {
                checkClick(btn, activity.getTranslateForLevelTwo());
            });
        }
        activity = (PlayActivity) getActivity();
        text.setText(activity.getLevelTwoWord());
        rusWords.addAll(activity.getListRusWords());
        setTextButtons(buttons, activity.getTranslateForLevelTwo());
        return view;
    }


    void checkClick(Button button, String str) {
        if (button.getText().equals(str)) {
            fragmentSendDataListener.onSendData("SuccessLevelTwo");
            //button.setBackgroundColor(Color.GREEN);
        } else {
            for(Button btn:buttons){
                if(btn.getText().equals(str)){
                    //btn.setBackgroundColor(Color.GREEN);
                }else {
                   // btn.setBackgroundColor(Color.RED);
                }
            }
            fragmentSendDataListener.onSendData("FailLevelTwo");
            activity.changeCurrentLevelTwoWord();
            setWord(activity.getLevelTwoWord(),activity.getTranslateForLevelTwo());

        }
    }

    public void setWord(String str, String translate) {
        text.setText(str);
        setTextButtons(buttons, translate);
    }
}