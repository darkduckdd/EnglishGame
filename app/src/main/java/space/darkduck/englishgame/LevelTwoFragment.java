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
    private MainActivity activity;
    private ArrayList<String> rusWords = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();

    private void SetTextButtons(List<Button> buttonList, int index) {
        Set<Integer> generated = new HashSet<Integer>();
        Random r = new Random();
        generated.add(index);
        while (generated.size() < buttonList.size()) {
            generated.add(r.nextInt(rusWords.size()));
        }
        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(generated);
        Iterator<Integer> integerIterator = myTreeSet.iterator();
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setText(rusWords.get(integerIterator.next()));
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
                CheckClick(btn, activity.GetTranslate());
            });
        }
        activity = (MainActivity) getActivity();
        text.setText(activity.GetLevelTwoWord());
        rusWords.addAll(activity.GetListRusWords());
        SetTextButtons(buttons, activity.GetPosition());
        return view;
    }


    void CheckClick(Button button, String str) {
        if (button.getText().equals(str)) {
            fragmentSendDataListener.OnSendData("SuccessLevelTwo");
        } else {
            fragmentSendDataListener.OnSendData("FailLevelTwo");
        }
    }
}