package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import space.darkduck.englishgame.Adapters.WordAdapter;

public class ShowFragment extends Fragment {
    private AddActivity activity;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private OnFragmentListener fragmentListener;
    private ArrayList<String> listEng=new ArrayList<>(),listRus=new ArrayList<>();
    private ArrayList<Integer> listID=new ArrayList<>();

    private  void  init(View view){
        activity=(AddActivity) getActivity();
        recyclerView=view.findViewById(R.id.wordRecyclerView);
        wordAdapter=new WordAdapter(listID,listEng,listRus);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setDataRecyclerView(ArrayList<Integer> IDs,ArrayList<String> engWords,ArrayList<String> rusWords){
        listID.clear();
        listEng.clear();
        listRus.clear();
        listID.addAll(IDs);
        listEng.addAll(engWords);
        listRus.addAll(rusWords);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentListener= (OnFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "должен реализовывать интерфейс OnFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_show, container, false);
        init(view);
        return view;
    }
}