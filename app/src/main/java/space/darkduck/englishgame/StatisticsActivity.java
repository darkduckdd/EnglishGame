package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
private TextView textView,textView2,textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView2=findViewById(R.id.textView3);
        textView.setText(Statistics.getLessonCompleted()+"/");
        textView2.setText(Statistics.getWordsLearned()+"/");
    }
}