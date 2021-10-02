package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StatisticsActivity extends AppCompatActivity {
private TextView maxWordText,wordLearnedText,wordLeftText,lessonCompletedText;
private DatabaseHelper myDB;
private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        myDB=new DatabaseHelper(StatisticsActivity.this);
        floatingActionButton=findViewById(R.id.floatingActionBackButton);
        maxWordText=findViewById(R.id.maxWord);
        wordLeftText=findViewById(R.id.wordLeft);
        wordLearnedText=findViewById(R.id.wordLearned);
        lessonCompletedText=findViewById(R.id.lessonCompleted);
        maxWordText.setText(maxWordText.getText() +" "+myDB.getMaxId());
        wordLearnedText.setText(wordLearnedText.getText()+" "+ Statistics.getWordsLearned());
        wordLeftText.setText(wordLeftText.getText()+" "+(myDB.getMaxId()-Statistics.getWordsLearned()));
        lessonCompletedText.setText(lessonCompletedText.getText()+" "+Statistics.getLessonCompleted());
        floatingActionButton.setOnClickListener((v)->{
            Intent intent=new Intent(StatisticsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}