package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    private TextView text;
    private FloatingActionButton addButton, playButton;
    private MyDatabaseHelper myDB;
    private ArrayList<String> levelEngWords = new ArrayList();
    private ArrayList<String> levelRusWords = new ArrayList();
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private int currentWordPosition = 0;
    private final int ScoreAddPoint=10;
    private final int ScoreRemovePoint=5;
    private int maxWords=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView2);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        myDB = new MyDatabaseHelper(MainActivity.this);
        GetEngWordsAndSaveInArray(myDB, levelEngWords);
        GetRusWordsAndSaveInArray(myDB, levelEngWords, levelRusWords);

        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        playButton.setOnClickListener((v) -> {
            fragmentLevelOne = new LevelOneFragment();
            fragmentLevelTwo=new LevelTwoFragment();
            fragmentLevelThree=new LevelThreeFragment();
            GetProgress();

        });
    }

    public String GetLevelWord() {
        return levelEngWords.get(currentWordPosition);
    }

    public ArrayList<String> GetListRusWords() {
        return levelRusWords;
    }

    public int GetPosition() {
        return currentWordPosition;
    }

    public String GetTranslate() {
        return GetStringFromCursor(myDB.GetRusWord(levelEngWords.get(currentWordPosition)));
    }

    private void GetEngWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> levelWords) {
        Cursor cursor = myDB.GetWordsForLevel();
        if (cursor.moveToFirst()) {
            String str;
            do {
                str = "";
                for (String cn : cursor.getColumnNames()) {
                    levelWords.add(cursor.getString(cursor.getColumnIndex(cn)));
                }
            } while (cursor.moveToNext());
        }
    }

    private String GetStringFromCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            String str;
            do {
                str = "";
                for (String cn : cursor.getColumnNames()) {
                    return str = cursor.getString(cursor.getColumnIndex(cn));
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    private void GetProgress() {
        Random random=new Random();
        currentWordPosition = random.nextInt(maxWords-1);
        int currentWordProgress = myDB.GetProgress(levelEngWords.get(currentWordPosition));
        if (currentWordProgress < 0) {
            myDB.Update(levelEngWords.get(currentWordPosition),0);
        }
        else if(currentWordProgress>0 &currentWordProgress<20){
            ChangeFragment(fragmentLevelOne);
        }
        else if(currentWordProgress>=20 & currentWordProgress<50) {
            //TODO level two
        }else if(currentWordProgress>=50 &currentWordProgress<100){
            //Todo level three
        }
        else if(currentWordProgress>=100){
            //todo
        }
    }


    @Override
    public void OnSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                myDB.Update(GetLevelWord(), myDB.GetProgress(GetLevelWord())+ScoreAddPoint);
                GetProgress();
                //fragmentLevelTwo = new LevelTwoFragment();
                //ChangeFragment(1,fragmentLevelTwo);
                break;
            case "FailLevelOne":
                fragmentLevelOne.SetTranslate(GetStringFromCursor(myDB.GetRusWord(levelEngWords.get(currentWordPosition))));
                break;
            case "SuccessLevelTwo":
                fragmentLevelThree=new LevelThreeFragment();
                ChangeFragment(fragmentLevelThree);
                break;
            case "FailLevelTwo":
                ChangeFragment(fragmentLevelOne);
                break;
            case  "SuccessLevelThree":
                ChangeFragment(fragmentLevelOne);
                break;
            case "FailLevelThree":
                ChangeFragment(fragmentLevelTwo);
                break;
        }
    }

    public  void ChangeFragment( Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }

    private void GetRusWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> engWords, ArrayList<String> rusWords) {
        for (String str : engWords) {
            rusWords.add(GetStringFromCursor(myDB.GetRusWord(str)));
        }
    }
}