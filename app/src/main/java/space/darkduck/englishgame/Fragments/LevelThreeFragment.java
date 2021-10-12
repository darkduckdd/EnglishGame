package space.darkduck.englishgame.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

import space.darkduck.englishgame.DatabaseHelper;
import space.darkduck.englishgame.LevelResult;
import space.darkduck.englishgame.OnFragmentListener;
import space.darkduck.englishgame.PlayActivity;
import space.darkduck.englishgame.R;
import space.darkduck.englishgame.Statistics;

public class LevelThreeFragment extends Fragment {

    private OnFragmentListener fragmentSendDataListener;
    private TextView text;
    private EditText edit;
    private Button button;
    private PlayActivity activity;
    private ArrayList<Integer> listID = new ArrayList<>();
    private int currentPosition;
    private int progressValue;

    private void init(View view) {
        activity = (PlayActivity) getActivity();
        text = view.findViewById(R.id.wordLearned);
        edit = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
        listID.addAll(activity.getListThreeIDS());
        Random random = new Random();
        currentPosition = random.nextInt(listID.size());
        text.setText(activity.getRusWord(listID.get(currentPosition)));
        button.setOnClickListener((v) -> {
            checkClick();
        });
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
        View view = inflater.inflate(R.layout.fragment_level_three, container, false);
        init(view);
        return view;
    }

    private void checkClick() {
        if (edit.getText().toString().equals(activity.getEngWord(listID.get(currentPosition)))) {
            activity.addProgressBar(progressValue);
            activity.updateWordProgress(listID.get(currentPosition));
            if (activity.getProgress(listID.get(currentPosition)) >= 100) {
                listID.remove(currentPosition);
                Statistics.setWordsLearned();
                if (listID.size() == 0) {
                    fragmentSendDataListener.onResultLevel(LevelResult.LevelThreeSuccess);
                } else if (listID.size() == 1) {
                    currentPosition = 0;
                    text.setText(activity.getRusWord(listID.get(currentPosition)));
                    edit.setText("");
                } else {
                    Random random = new Random();
                    currentPosition = random.nextInt(listID.size());
                    text.setText(activity.getRusWord(listID.get(currentPosition)));
                    edit.setText("");
                }
            }
            else {
                Random random = new Random();
                currentPosition = random.nextInt(listID.size());
                text.setText(activity.getRusWord(listID.get(currentPosition)));
                edit.setText("");
            }
        } else {
            Random random = new Random();
            currentPosition = random.nextInt(listID.size());
            text.setText(activity.getRusWord(listID.get(currentPosition)));
            edit.setText("");
        }
    }
}