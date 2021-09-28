package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements OnFragmentListener {

    private ProgressBar pbHorizontal;
    private MyDatabaseHelper myDB;
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private int currentWordPosition = 0;
    private final int scoreAddPoint = 20;
    private int progressForBar = 0;
    private ArrayList<String> engWordsLevelOne = new ArrayList();
    private ArrayList<String> engWordsLevelTwo = new ArrayList();
    private ArrayList<String> engWordsLevelThree = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        myDB = new MyDatabaseHelper(PlayActivity.this);
        pbHorizontal = findViewById(R.id.progressBar);
        pbHorizontal.setProgress(progressForBar);
        pbHorizontal.setVisibility(View.INVISIBLE);

        fragmentLevelOne = new LevelOneFragment();
        fragmentLevelTwo = new LevelTwoFragment();
        fragmentLevelThree = new LevelThreeFragment();
        Cursor cursor=myDB.getLevelWords();
        Log.d("Cursa","Count:"+cursor.getCount());
        cursor.moveToFirst();
        do {
            String strings=cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.getColumnEngWord()));
            Log.d("Cursa","ID: "+strings);
        }while (cursor.moveToNext());
       /* if (engWordsLevelOne.size() > 0) {
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelOne);
        } else if (engWordsLevelTwo.size() > 0) {
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelTwo);
        } else if (engWordsLevelThree.size() > 0) {
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelThree);
        } else {
            //TODO словать пустой надо дать пересылку на другой активити
        }*/
    }



    public void addProgress(int value){
        progressForBar += value;
        pbHorizontal.setProgress(progressForBar);
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

    public void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }


    @Override
    public void onSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                updateProgress(myDB,engWordsLevelOne,scoreAddPoint);
                if(engWordsLevelTwo.size()==0){
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    pbHorizontal.setProgress(0);
                    changeFragment(fragmentLevelTwo);
                }
                break;
            case "SuccessLevelTwo":
                updateProgress(myDB,engWordsLevelTwo,scoreAddPoint*2);
                if(engWordsLevelThree.size()==0){
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    pbHorizontal.setProgress(0);
                    changeFragment(fragmentLevelThree);
                }
                break;
            case "SuccessLevelThree":
                updateProgress(myDB,engWordsLevelThree,scoreAddPoint*2);
                pbHorizontal.setProgress(0);
                pbHorizontal.setEnabled(false);
                pbHorizontal.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case "FailLevelThree":
                break;
        }
    }

    private  void updateProgress(MyDatabaseHelper db,ArrayList<String> list,int value){
        for(String str:list){
            db.update(str, db.getProgress(str) +value);
        }
    }
}