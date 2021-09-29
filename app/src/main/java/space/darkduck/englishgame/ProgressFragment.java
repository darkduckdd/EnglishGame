package space.darkduck.englishgame;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {
    private PlayActivity activity;
    private TextView text;
    private RecyclerView recyclerView;
    private ProgressAdapter progressAdapter;
    private ArrayList<Integer> progressID=new ArrayList<>(),oldProgress=new ArrayList<>(),newProgress=new ArrayList<>();
    private ArrayList<String> words=new ArrayList<>();

    private void init(View view){
        activity = (PlayActivity) getActivity();
        text = view.findViewById(R.id.textView);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressID.addAll(activity.getListOneIDS());
        addWords();
        progressAdapter=new ProgressAdapter(words,oldProgress,newProgress);
        recyclerView.setAdapter(progressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void addWords(){
        for(int num:progressID){
            words.add(activity.getEngWord(num));
            oldProgress.add(num);
            newProgress.add(num+20);
        }
    }

    private void startCountAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 600); //0 is min number, 600 is max number
        animator.setDuration(5000); //Duration is in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        init(view);
        return view;
    }
}