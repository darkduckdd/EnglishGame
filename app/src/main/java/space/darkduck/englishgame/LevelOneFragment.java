package space.darkduck.englishgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LevelOneFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_level_one,container,false);
        Button rightButton,mistakeButton;
        TextView textView=view.findViewById(R.id.text);

        rightButton=view.findViewById(R.id.rightButton);
        rightButton.setOnClickListener((v)->{
            //TODO
            textView.setText("Work");
        });
        mistakeButton=view.findViewById(R.id.mistakeButton);
        mistakeButton.setOnClickListener((v)->{
            //TODO
            textView.setText("dontWork");
        });
        return view ;
    }
}