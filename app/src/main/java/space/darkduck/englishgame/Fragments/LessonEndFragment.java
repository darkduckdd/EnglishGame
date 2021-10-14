package space.darkduck.englishgame.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import space.darkduck.englishgame.PlayActivity;
import space.darkduck.englishgame.R;

public class LessonEndFragment extends Fragment {
    private TextView textView;
    private boolean isDatabaseEmpty=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_lesson_end, container, false);
        Button menuButton=view.findViewById(R.id.menuButton);
        textView=view.findViewById(R.id.endTextView);
        if(isDatabaseEmpty){
            textView.setText("Вы прошли все слова введите 10 слов");
        }
        else {
            textView.setText("Урок пройден");
        }
        menuButton.setOnClickListener((v)->{
            PlayActivity activity=(PlayActivity) getActivity();
            activity.goToMenu();
        });
        return view;
    }

    public void setEmptyTextView(){
        isDatabaseEmpty=true;
    }
}