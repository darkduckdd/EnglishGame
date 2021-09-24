package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    private TextView text;
    private FloatingActionButton addButton, playButton;
    private MyDatabaseHelper myDB;
    private ArrayList<String> engWordsLevelOne = new ArrayList();
    private ArrayList<String> engWordsLevelTwo = new ArrayList();
    private ArrayList<String> engWordsLevelThree = new ArrayList();
    private ArrayList<String> rusWordsLevelTwo = new ArrayList();
    private  ArrayList<String> levelRusWords=new ArrayList<>();
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private int currentWordPosition = 0;
    private final int scoreAddPoint =20;
    private final int scoreRemovePoint =10;
    private final  int pointToLevelTwo=20;
    private final  int pointToLevelThree=60;
    private final  int pointToEnd=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView2);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        myDB = new MyDatabaseHelper(MainActivity.this);
        getEngWordsAndSaveInArray(myDB, engWordsLevelOne,1);
        getEngWordsAndSaveInArray(myDB, engWordsLevelTwo,2);
        getEngWordsAndSaveInArray(myDB, engWordsLevelThree,3);
        getRusWordsAndSaveInArray(myDB, engWordsLevelTwo, rusWordsLevelTwo);
        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        playButton.setOnClickListener((v) -> {
            fragmentLevelOne = new LevelOneFragment();
            fragmentLevelTwo=new LevelTwoFragment();
            fragmentLevelThree=new LevelThreeFragment();
            if(engWordsLevelOne.size()>0){
                changeFragment(fragmentLevelOne);
            }else if(engWordsLevelTwo.size()>0){
                changeFragment(fragmentLevelTwo);
            }else  if(engWordsLevelThree.size()>0){
                changeFragment(fragmentLevelThree);
            }
            else{
                //TODO словать пустой надо дать пересылку на другой активити
            }
        });
    }

    public String getLevelOneWord() {
        return engWordsLevelOne.get(currentWordPosition);
    }
    public String getLevelTwoWord() {
        return engWordsLevelTwo.get(currentWordPosition);
    }
    public String getLevelThreeWord() {
        return engWordsLevelThree.get(currentWordPosition);
    }

    public ArrayList<String> getListRusWords() {
        return rusWordsLevelTwo;
    }

    public int getPosition() {
        return currentWordPosition;
    }

    public String getTranslate() {
        return getStringFromCursor(myDB.getRusWord(engWordsLevelTwo.get(currentWordPosition)));
    }
    public String getTranslateForLevelThree(){
        return getStringFromCursor(myDB.getRusWord(engWordsLevelTwo.get(currentWordPosition)));
    }

    private void getEngWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> levelWords, int level) {
        Cursor cursor;
        switch (level) {
            case 1:
                cursor = myDB.getWordsForLevelOne();
                break;
            case 2:
                cursor = myDB.getWordsForLevelTwo();
                break;
            case 3:
                cursor = myDB.getWordsForLevelThree();
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

    private void getRusWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> engWords, ArrayList<String> rusWords) {
        for (String str : engWords) {
            rusWords.add(getStringFromCursor(myDB.getRusWord(str)));
        }
    }

    private String getStringFromCursor(Cursor cursor) {
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

    public  void changeFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }

    @Override
    public void onSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
               // Log.d("Adil", GetLevelOneWord());
                myDB.update(getLevelOneWord(), myDB.getProgress(getLevelOneWord())+ scoreAddPoint);
                int progress=myDB.getProgress(getLevelOneWord());
                //Log.d("Adil", "progress= "+ progress);
                if(progress>=pointToLevelTwo){
                    engWordsLevelOne.remove(getLevelOneWord());
                    //Log.d("Adil","size= "+levelEngWordsLevelOne.size());
                    //Log.d("Adil","position= "+currentWordPosition);
                    //изменить текст view fragment
                    if (engWordsLevelOne.size()>=1) {
                        Random random = new Random();
                        currentWordPosition = random.nextInt(engWordsLevelOne.size());
                        fragmentLevelOne.setWord(engWordsLevelOne.get(currentWordPosition));
                    }
                    else if(engWordsLevelOne.size()==0){
                        if(engWordsLevelTwo.size()==0){
                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            startActivity(intent);
                        }else {
                            currentWordPosition=0;
                            changeFragment(fragmentLevelTwo);
                        }
                    }
                }
                else {
                    Random random = new Random();
                    currentWordPosition = random.nextInt(engWordsLevelOne.size());
                    fragmentLevelOne.setWord(engWordsLevelOne.get(currentWordPosition));
                }
                break;
            case "FailLevelOne":
                fragmentLevelOne.setTranslate(getStringFromCursor(myDB.getRusWord(engWordsLevelOne.get(currentWordPosition))));
                break;
            case "SuccessLevelTwo":
                myDB.update(getLevelTwoWord(), myDB.getProgress(getLevelTwoWord())+ scoreAddPoint);
                int progressLevelTwo=myDB.getProgress(getLevelTwoWord());
                //Log.d("Adil", "progress= "+ progress);
                if(progressLevelTwo>=pointToLevelThree){
                    engWordsLevelTwo.remove(getLevelTwoWord());
                    //Log.d("Adil","size= "+levelEngWordsLevelOne.size());
                    //Log.d("Adil","position= "+currentWordPosition);
                    if (engWordsLevelTwo.size()>=1) {
                        Random random = new Random();
                        currentWordPosition = random.nextInt(engWordsLevelTwo.size());
                        fragmentLevelTwo.setWord(engWordsLevelTwo.get(currentWordPosition), getTranslate());
                    }
                    else if(engWordsLevelTwo.size()==0){
                        if(engWordsLevelThree.size()==0){
                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            startActivity(intent);
                        }else {
                            currentWordPosition=0;
                            changeFragment(fragmentLevelThree);
                        }
                    }
                }
                else {
                    Random random = new Random();
                    currentWordPosition = random.nextInt(engWordsLevelTwo.size());
                    fragmentLevelTwo.setWord(engWordsLevelTwo.get(currentWordPosition), getTranslate());
                }
                break;
            case "FailLevelTwo":
               // myDB.Update(GetLevelOneWord(), myDB.GetProgress(GetLevelOneWord())-ScoreRemovePoint);
                break;
            case  "SuccessLevelThree":
                myDB.update(getLevelOneWord(), myDB.getProgress(getLevelOneWord())+ scoreAddPoint);
                break;
            case "FailLevelThree":
                //ChangeFragment(fragmentLevelTwo);
                myDB.update(getLevelOneWord(), myDB.getProgress(getLevelOneWord())- scoreRemovePoint);
                break;
        }
    }
}