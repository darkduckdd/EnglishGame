package space.darkduck.englishgame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LevelThreeFragment extends Fragment {

    private OnFragmentListener fragmentSendDataListener;
    TextView text;
    EditText edit;
    Button button;
    private  MainActivity activity;

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
        activity=(MainActivity) getActivity();
        text=view.findViewById(R.id.textView);
        edit=view.findViewById(R.id.editText);
        button=view.findViewById(R.id.button);
        text.setText(activity.GetLevelOneWord());
        button.setOnClickListener((v)->{
            if(edit.getText().toString().equals(activity.GetTranslate())){
                fragmentSendDataListener.OnSendData("SuccessLevelThree");
            }else {
                fragmentSendDataListener.OnSendData("FailLevelThree");
            }
        });
        return view;
    }
}