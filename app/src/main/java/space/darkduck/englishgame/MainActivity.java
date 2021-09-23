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
    private ArrayList<String> levelEngWordsLevelOne = new ArrayList();
    private ArrayList<String> levelEngWordsLevelTwo = new ArrayList();
    private ArrayList<String> levelEngWordsLevelThree = new ArrayList();
    private ArrayList<String> levelRusWords = new ArrayList();
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private int currentWordPosition = 0;
    private final int ScoreAddPoint=10;
    private final int ScoreRemovePoint=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView2);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        myDB = new MyDatabaseHelper(MainActivity.this);
        GetEngWordsAndSaveInArray(myDB, levelEngWordsLevelOne,1);
        GetEngWordsAndSaveInArray(myDB, levelEngWordsLevelTwo,2);
        GetEngWordsAndSaveInArray(myDB, levelEngWordsLevelThree,3);
        GetRusWordsAndSaveInArray(myDB, levelEngWordsLevelTwo, levelRusWords);

        for(String str:levelEngWordsLevelTwo){
            Log.d("WORDS",str);
        }
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

    public String GetLevelOneWord() {
        return levelEngWordsLevelOne.get(currentWordPosition);
    }
    public String GetLevelTwoWord() {
        return levelEngWordsLevelTwo.get(currentWordPosition);
    }
    public String GetLevelThreeWord() {
        return levelEngWordsLevelThree.get(currentWordPosition);
    }

    public ArrayList<String> GetListRusWords() {
        return levelRusWords;
    }

    public int GetPosition() {
        return currentWordPosition;
    }

    public String GetTranslate() {
        return GetStringFromCursor(myDB.GetRusWord(levelEngWordsLevelTwo.get(currentWordPosition)));
    }

    private void GetEngWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> levelWords,int level) {
        Cursor cursor;
        switch (level) {
            case 1:
                cursor = myDB.GetWordsForLevelOne();
                break;
            case 2:
                cursor = myDB.GetWordsForLevelTwo();
                break;
            case 3:
                cursor = myDB.GetWordsForLevelThree();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }
        if (cursor == null) {
            levelWords.add("Empty");
        } else {

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
    }

    private void GetRusWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> engWords, ArrayList<String> rusWords) {
        for (String str : engWords) {
            rusWords.add(GetStringFromCursor(myDB.GetRusWord(str)));
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
        int currentWordProgress = myDB.GetProgress(levelEngWordsLevelOne.get(currentWordPosition));
        if (currentWordProgress < 0) {
            myDB.Update(levelEngWordsLevelOne.get(currentWordPosition),0);
            ChangeFragment(fragmentLevelOne);
        }
        else if(currentWordProgress>=0 &currentWordProgress<20){
            ChangeFragment(fragmentLevelOne);
        }
        else if(currentWordProgress>=20 & currentWordProgress<50) {
            ChangeFragment(fragmentLevelTwo);
        }else if(currentWordProgress>=50 &currentWordProgress<100){
            ChangeFragment(fragmentLevelThree);
        }
        else if(currentWordProgress>=100){
            //todo
        }
    }

    public  void ChangeFragment( Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }

    @Override
    public void OnSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                myDB.Update(GetLevelOneWord(), myDB.GetProgress(GetLevelOneWord())+ScoreAddPoint);
                levelEngWordsLevelOne.remove(GetLevelOneWord());
                if(levelEngWordsLevelOne.size()==0){
                    if(levelEngWordsLevelTwo.size()==0){
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                    }else {
                        currentWordPosition=0;
                        ChangeFragment(fragmentLevelTwo);
                    }
                }
                else {
                    Random random = new Random();
                    currentWordPosition = random.nextInt(levelEngWordsLevelOne.size());
                    fragmentLevelOne.SetWord(levelEngWordsLevelOne.get(currentWordPosition));
                }

                break;
            case "FailLevelOne":
                fragmentLevelOne.SetTranslate(GetStringFromCursor(myDB.GetRusWord(levelEngWordsLevelOne.get(currentWordPosition))));
                break;
            case "SuccessLevelTwo":
                myDB.Update(GetLevelTwoWord(), myDB.GetProgress(GetLevelTwoWord())+ScoreAddPoint);
                levelEngWordsLevelTwo.remove(GetLevelTwoWord());
                if(levelEngWordsLevelTwo.size()==0){
                    if(levelEngWordsLevelThree.size()==0){
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                    }else {
                        currentWordPosition=0;
                        ChangeFragment(fragmentLevelThree);
                    }
                }
                else {
                    Random random = new Random();
                    currentWordPosition = random.nextInt(levelEngWordsLevelTwo.size());
                    //fragmentLevelTwo.SetWord(levelEngWordsLevelTwo.get(currentWordPosition));
                }
                break;
            case "FailLevelTwo":
                myDB.Update(GetLevelOneWord(), myDB.GetProgress(GetLevelOneWord())-ScoreRemovePoint);
                break;
            case  "SuccessLevelThree":
                myDB.Update(GetLevelOneWord(), myDB.GetProgress(GetLevelOneWord())+ScoreAddPoint);
                break;
            case "FailLevelThree":
                ChangeFragment(fragmentLevelTwo);
                myDB.Update(GetLevelOneWord(), myDB.GetProgress(GetLevelOneWord())-ScoreRemovePoint);
                break;
        }
    }
}