package space.darkduck.englishgame.Fragments;

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

import space.darkduck.englishgame.DatabaseHelper;
import space.darkduck.englishgame.LevelResult;
import space.darkduck.englishgame.OnFragmentListener;
import space.darkduck.englishgame.PlayActivity;
import space.darkduck.englishgame.R;

public class LevelTwoFragment extends Fragment {
    private OnFragmentListener fragmentSendDataListener;
    private Button button1, button2, button3, button4;
    private TextView text;
    private PlayActivity activity;
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> listID = new ArrayList<>();
    private ArrayList<Integer> listIDCopy = new ArrayList<>();
    private int currentPosition;
    private int progressValue;

    private void setTextButtons(List<Button> buttonList) {
        Set<String> strGenerated = new HashSet<>();
        Random r = new Random();
        strGenerated.add(activity.getRusWord(listID.get(currentPosition)));
        while (strGenerated.size() < buttonList.size()) {
            strGenerated.add(activity.getRusWord(listIDCopy.get(r.nextInt(listIDCopy.size()))));
        }
        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(strGenerated);
        Iterator<String> strIterator = myTreeSet.iterator();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText(strIterator.next());
        }
    }

    void checkClick(Button button) {
        if (button.getText().equals(activity.getRusWord(listID.get(currentPosition)))) {
            activity.addProgressBar(progressValue);
            activity.updateWordProgress(listID.get(currentPosition));
            if (activity.getProgress(listID.get(currentPosition)) >= 60) {
                listID.remove(currentPosition);
                if (listID.size() == 0) {
                    fragmentSendDataListener.onResultLevel(LevelResult.LevelTwoSuccess);
                } else if (listID.size() == 1) {
                    currentPosition = 0;
                    text.setText(activity.getEngWord(listID.get(currentPosition)));
                    setTextButtons(buttons);
                } else {
                    Random random = new Random();
                    currentPosition = random.nextInt(listID.size());
                    text.setText(activity.getEngWord(listID.get(currentPosition)));
                    setTextButtons(buttons);
                }
            } else {
                Random random = new Random();
                currentPosition = random.nextInt(listID.size());
                text.setText(activity.getEngWord(listID.get(currentPosition)));
                setTextButtons(buttons);
            }
        } else {
            Random random = new Random();
            currentPosition = random.nextInt(listID.size());
            text.setText(activity.getEngWord(listID.get(currentPosition)));
            setTextButtons(buttons);
        }
    }

    private void init(View view) {
        text = view.findViewById(R.id.wordLearned);
        button1 = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        buttons.addAll(Arrays.asList(button1, button2, button3, button4));
        activity = (PlayActivity) getActivity();
        listID.addAll(activity.getListTwoIDS());
        listIDCopy.addAll(listID);
        Random random = new Random();
        currentPosition = random.nextInt(listID.size());
        text.setText(activity.getEngWord(listID.get(currentPosition)));
        setTextButtons(buttons);
        for (Button btn : buttons) {
            btn.setOnClickListener((v) -> {
                checkClick(btn);
            });
        }
        progressValue=100/ DatabaseHelper.getWordLimit();
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