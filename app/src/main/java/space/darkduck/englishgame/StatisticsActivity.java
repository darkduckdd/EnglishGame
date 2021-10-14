package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StatisticsActivity extends AppCompatActivity {
    private TextView maxWordText, wordLearnedText, wordLeftText, lessonCompletedText;
    private DatabaseHelper myDB;
    private FloatingActionButton floatingActionButton;
    public static final String preferencesStatistics = "statisticsPreferences";
    public static final String preferencesLessonCount = "lessonCount";
    public static final String preferencesWordLearned = "wordLearned";
    int lessonCount,wordLearned,maxWord,leftWord;

    private void init() {
        myDB = new DatabaseHelper(StatisticsActivity.this);
        floatingActionButton = findViewById(R.id.floatingActionBackButton);
        maxWordText = findViewById(R.id.maxWord);
        wordLeftText = findViewById(R.id.wordLeft);
        wordLearnedText = findViewById(R.id.wordLearned);
        lessonCompletedText = findViewById(R.id.lessonCompleted);
        floatingActionButton.setOnClickListener((v) -> {
            Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        init();
    }
}