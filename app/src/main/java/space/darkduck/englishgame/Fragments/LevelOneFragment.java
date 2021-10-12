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
import java.util.Random;

import space.darkduck.englishgame.DatabaseHelper;
import space.darkduck.englishgame.LevelResult;
import space.darkduck.englishgame.OnFragmentListener;
import space.darkduck.englishgame.PlayActivity;
import space.darkduck.englishgame.R;

public class LevelOneFragment extends Fragment {

    private TextView textView;
    private OnFragmentListener fragmentSendDataListener;
    private PlayActivity activity;
    private ArrayList<Integer> listID = new ArrayList();
    private Button rightButton, mistakeButton,nextButton;
    private int currentPosition;
    private int progressValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_one, container, false);
        init(view);
        textView.setText(activity.getEngWord(listID.get(currentPosition)));
        rightButton.setOnClickListener((v) -> {
            activity.addProgressBar(progressValue);
            activity.updateWordProgress(listID.get(currentPosition));
            if(activity.getProgress(listID.get(currentPosition))>=20) {
                listID.remove(currentPosition);
                if (listID.size() == 0) {
                    fragmentSendDataListener.onResultLevel(LevelResult.LevelOneSuccess);
                } else if (listID.size() == 1) {
                    currentPosition = 0;
                    textView.setText(activity.getEngWord(listID.get(currentPosition)));
                } else {
                    Random random = new Random();
                    currentPosition = random.nextInt(listID.size());
                    textView.setText(activity.getEngWord(listID.get(currentPosition)));
                }
            }else {
                Random random = new Random();
                currentPosition = random.nextInt(listID.size());
                textView.setText(activity.getEngWord(listID.get(currentPosition)));
            }
        });

        mistakeButton.setOnClickListener((v) -> {
           textView.setText(activity.getRusWord(listID.get(currentPosition)));
            activeNextButton(true,View.VISIBLE,View.INVISIBLE);
        });
        nextButton.setOnClickListener((v) -> {
            Random random = new Random();
            currentPosition= random.nextInt(listID.size());
            textView.setText(activity.getEngWord(listID.get(currentPosition)));
           activeNextButton(false,View.INVISIBLE,View.VISIBLE);
        });
        return view;
    }

    private void init(View view){
        textView = view.findViewById(R.id.text);
        mistakeButton = view.findViewById(R.id.mistakeButton);
        rightButton = view.findViewById(R.id.rightButton);
        nextButton=view.findViewById(R.id.nextButton);
        activity=(PlayActivity) getActivity();
        listID.addAll(activity.getListOneIDS());
        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);
        Random random = new Random();
        currentPosition= random.nextInt(listID.size());
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

    private void activeNextButton(boolean isActive,int visibleNext,int visibleOther){
        rightButton.setEnabled(!isActive);
        rightButton.setVisibility(visibleOther);
        mistakeButton.setEnabled(!isActive);
        mistakeButton.setVisibility(visibleOther);
        nextButton.setVisibility(visibleNext);
        nextButton.setEnabled(isActive);
    }
}