package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {
    private OnFragmentListener fragmentSendDataListener;
    private PlayActivity activity;
    private TextView text;
    private RecyclerView recyclerView;
    private Button nextLevelButton;
    private ProgressAdapter progressAdapter;
    private ArrayList<Integer> progressID=new ArrayList<>(),oldProgress=new ArrayList<>(),newProgress=new ArrayList<>();
    private ArrayList<String> words=new ArrayList<>();

    public void setDataRecyclerView(ArrayList<Integer> IDs,ArrayList<Integer> oldProgresses){
        progressID.clear();
        words.clear();
        oldProgress.clear();
        newProgress.clear();
        progressID.addAll(IDs);
        oldProgress.addAll(oldProgresses);
    }
    private void init(View view){
        activity = (PlayActivity) getActivity();
        text = view.findViewById(R.id.textView);
        recyclerView=view.findViewById(R.id.recyclerView);
        nextLevelButton=view.findViewById(R.id.nextLevelButton);
        addWordToList();
        progressAdapter=new ProgressAdapter(words,oldProgress,newProgress);
        recyclerView.setAdapter(progressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nextLevelButton.setOnClickListener((v)->{
            fragmentSendDataListener.onSendData("NextLevel");
        });
    }

    private void addWordToList(){
        for(int num:progressID){
            words.add(activity.getEngWord(num));
            newProgress.add(activity.getProgress(num));
        }
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