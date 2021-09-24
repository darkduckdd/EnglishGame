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

    private FloatingActionButton addButton, playButton;
    private MyDatabaseHelper myDB;
    private ArrayList<String> engWordsLevelOne = new ArrayList();
    private ArrayList<String> engWordsLevelTwo = new ArrayList();
    private ArrayList<String> engWordsLevelThree = new ArrayList();
    private ArrayList<String> rusWordsLevelTwo = new ArrayList();
    private  ArrayList<String> levelRusWordsThree=new ArrayList<>();
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
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        myDB = new MyDatabaseHelper(MainActivity.this);
        getEngWordsAndSaveInArray(myDB, engWordsLevelOne,1);
        getEngWordsAndSaveInArray(myDB, engWordsLevelTwo,2);
        getEngWordsAndSaveInArray(myDB, engWordsLevelThree,3);
        getRusWordsAndSaveInArray(myDB, engWordsLevelTwo, rusWordsLevelTwo);
        getRusWordsAndSaveInArray(myDB,engWordsLevelThree,levelRusWordsThree);
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

    public String getTranslate() {
        return getStringFromCursor(myDB.getRusWord(engWordsLevelTwo.get(currentWordPosition)));
    }
    public String getTranslateForLevelThree(){
        return getStringFromCursor(myDB.getRusWord(engWordsLevelThree.get(currentWordPosition)));
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
    public void changeCurrentWord(){
        Random random = new Random();
        currentWordPosition = random.nextInt(engWordsLevelOne.size());
    }

    @Override
    public void onSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                myDB.update(getLevelOneWord(), myDB.getProgress(getLevelOneWord())+ scoreAddPoint);
                int progress=myDB.getProgress(getLevelOneWord());
                if(progress>=pointToLevelTwo){
                    engWordsLevelOne.remove(getLevelOneWord());
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
                myDB.update(getLevelThreeWord(), myDB.getProgress(getLevelThreeWord())+ scoreAddPoint);
                int progressLevelThree=myDB.getProgress(getLevelThreeWord());
                if(progressLevelThree>=pointToEnd){
                    engWordsLevelThree.remove(getLevelThreeWord());
                    if (engWordsLevelThree.size()>=1) {
                        Random random = new Random();
                        currentWordPosition = random.nextInt(engWordsLevelThree.size());
                        fragmentLevelThree.setWord(engWordsLevelThree.get(currentWordPosition), getTranslateForLevelThree());
                    }
                    else if(engWordsLevelThree.size()==0){
                        if(engWordsLevelThree.size()==0){
                            Intent intent = new Intent(MainActivity.this, AddActivity.class);
                            startActivity(intent);
                        }else {
                            currentWordPosition=0;
                            changeFragment(fragmentLevelOne);
                        }
                    }
                }
                else {
                    Random random = new Random();
                    currentWordPosition = random.nextInt(engWordsLevelThree.size());
                    fragmentLevelThree.setWord(engWordsLevelThree.get(currentWordPosition), getTranslateForLevelThree());
                }
                break;
            case "FailLevelThree":

                break;
        }
    }


}