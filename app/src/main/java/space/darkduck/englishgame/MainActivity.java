package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    private FloatingActionButton addButton, playButton;
    private MyDatabaseHelper myDB;
    private ArrayList<String> levelEngWords = new ArrayList();
    private ArrayList<String> levelRusWords = new ArrayList();
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private int currentWordPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerFL, fragmentLevelOne);
            ft.commit();
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
        //TODO
    }

    @Override
    public void OnSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                fragmentLevelTwo = new LevelTwoFragment();
                ChangeFragment(1,fragmentLevelTwo);
                break;
            case "FailLevelOne":
                fragmentLevelOne.SetTranslate(GetStringFromCursor(myDB.GetRusWord(levelEngWords.get(currentWordPosition))));
                break;
            case "SuccessLevelTwo":
                fragmentLevelThree=new LevelThreeFragment();
                ChangeFragment(2,fragmentLevelThree);
                break;
            case "FailLevelTwo":
                ChangeFragment(0,fragmentLevelOne);
                break;
            case  "SuccessLevelThree":
                ChangeFragment(3,fragmentLevelOne);
                CheckEnd();
                break;
            case "FailLevelThree":
                ChangeFragment(1,fragmentLevelTwo);
                break;
        }
    }

    public  void ChangeFragment(int value, Fragment fragment){
        myDB.Update(levelEngWords.get(currentWordPosition), value);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }

    private void GetRusWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> engWords, ArrayList<String> rusWords) {
        for (String str : engWords) {
            rusWords.add(GetStringFromCursor(myDB.GetRusWord(str)));
        }
    }
    private void CheckEnd(){
       /* currentWordPosition++;
        if(currentWordPosition>=levelEngWords.size()){
            currentWordPosition=0;
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }*/
    }
}