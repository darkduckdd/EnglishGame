package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
private TextView maxWordText,wordLearnedText,wordLeftText,lessonCompletedText;
private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        myDB=new DatabaseHelper(StatisticsActivity.this);
        maxWordText=findViewById(R.id.maxWord);
        wordLeftText=findViewById(R.id.wordLeft);
        wordLearnedText=findViewById(R.id.wordLearned);
        lessonCompletedText=findViewById(R.id.lessonCompleted);
        maxWordText.setText(maxWordText.getText() +" "+myDB.getMaxId());
        wordLearnedText.setText(wordLearnedText.getText()+" "+ Statistics.getWordsLearned());
        wordLeftText.setText(wordLeftText.getText()+" "+(myDB.getMaxId()-Statistics.getWordsLearned()));
        lessonCompletedText.setText(lessonCompletedText.getText()+" "+Statistics.getLessonCompleted());
    }
}