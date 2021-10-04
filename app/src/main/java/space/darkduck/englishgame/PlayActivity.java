package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity implements OnFragmentListener {

    private ProgressBar pbHorizontal;
    private DatabaseHelper myDB;
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private ProgressFragment fragmentProgress;
    private Cursor cursorLOne, cursorLTwo, cursorLThree;
    private final int scoreAddPoint = 20;
    private int progressForBar = 0;
    private ArrayList<Integer> listOneIDS = new ArrayList<>(), listTwoIDS = new ArrayList<>(),listThreeIDS = new ArrayList<>(),listProgresses=new ArrayList<>();
    private boolean isCompletedOne=false,isCompletedTwo=false,isCompletedThree=false;

    private void init() {
        pbHorizontal = findViewById(R.id.progressBar);
        pbHorizontal.setProgress(progressForBar);
        pbHorizontal.setVisibility(View.INVISIBLE);
        fragmentLevelOne = new LevelOneFragment();
        fragmentLevelTwo = new LevelTwoFragment();
        fragmentLevelThree = new LevelThreeFragment();
        fragmentProgress=new ProgressFragment();
        cursorLOne = myDB.getWordsForLevelOne();
        cursorLTwo = myDB.getWordsForLevelTwo();
        cursorLThree = myDB.getWordsForLevelThree();
        addIDToList(cursorLOne, listOneIDS);
        addIDToList(cursorLTwo, listTwoIDS);
        addIDToList(cursorLThree, listThreeIDS);
        cursorLOne.close();
        cursorLTwo.close();
        cursorLThree.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        myDB = new DatabaseHelper(PlayActivity.this);
        init();
        if (cursorLOne.getCount() != 0) {
            listProgresses.addAll(getOldProgress(listOneIDS));
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelOne);
        } else if (cursorLTwo.getCount() != 0) {
            listProgresses.addAll(getOldProgress(listTwoIDS));
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelTwo);
        } else if (cursorLThree.getCount() != 0) {
            listProgresses.addAll(getOldProgress(listThreeIDS));
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelThree);
        } else {
            //TODO словать пустой надо дать пересылку на другой активити
        }
    }

    public ArrayList<Integer> getListOneIDS() {
        return listOneIDS;
    }

    public ArrayList<Integer> getListTwoIDS() {
        return listTwoIDS;
    }

    public ArrayList<Integer> getListThreeIDS() {
        return listThreeIDS;
    }

    private void addIDToList(Cursor cursor, ArrayList<Integer> list) {
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.getColumnId()));
                list.add(id);
            } while (cursor.moveToNext());
        }
    }
    private ArrayList<Integer> getOldProgress(ArrayList<Integer> id){
        ArrayList<Integer> progresses=new ArrayList<>();
        for(int num:id){
            progresses.add(getProgress(num));
        }
        return progresses;
    }

    public String getEngWord(int id) {
        return myDB.getEngWord(String.valueOf(id));
    }

    public String getRusWord(int id) {
        return myDB.getRusWord(String.valueOf(id));
    }

    public int getProgress(int id) {
        return myDB.getProgress(String.valueOf(id));
    }

    public void updateWordProgress(int id) {
        myDB.updateProgress(String.valueOf(id), myDB.getProgress(String.valueOf(id)) + scoreAddPoint);
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerFL, fragment);
        ft.commit();
    }

    public void addProgressBar(int value) {
        progressForBar += value;
        pbHorizontal.setProgress(progressForBar);
    }

    @Override
    public void onResultLevel (LevelResult result) {
        switch (result) {
            case LevelOneSuccess:
                progressForBar=0;
                pbHorizontal.setProgress(0);
                pbHorizontal.setEnabled(false);
                pbHorizontal.setVisibility(View.INVISIBLE);
                isCompletedOne=true;
                fragmentProgress.setDataRecyclerView(listOneIDS,listProgresses);
                changeFragment(fragmentProgress);
                break;
            case LevelTwoSuccess:
                progressForBar=0;
                pbHorizontal.setProgress(0);
                pbHorizontal.setEnabled(false);
                pbHorizontal.setVisibility(View.INVISIBLE);
                isCompletedTwo=true;
                fragmentProgress.setDataRecyclerView(listTwoIDS,listProgresses);
                changeFragment(fragmentProgress);
                break;
            case LevelThreeSuccess:
                progressForBar=0;
                pbHorizontal.setProgress(0);
                pbHorizontal.setEnabled(false);
                pbHorizontal.setVisibility(View.INVISIBLE);
                isCompletedThree=true;
                fragmentProgress.setDataRecyclerView(listThreeIDS,listProgresses);
                changeFragment(fragmentProgress);
                Statistics.setLessonCompleted();
                break;
            case NextLevel:
                if (listTwoIDS.size() == 0 && listThreeIDS.size() == 0) {
                    Statistics.setLessonCompleted();
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if(listTwoIDS.size()!=0 && listThreeIDS.size()==0) {
                    if(isCompletedTwo){
                        Statistics.setLessonCompleted();
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        progressForBar = 0;
                        pbHorizontal.setProgress(0);
                        pbHorizontal.setEnabled(true);
                        pbHorizontal.setVisibility(View.VISIBLE);
                        listProgresses.clear();
                        listProgresses.addAll(getOldProgress(listTwoIDS));
                        changeFragment(fragmentLevelTwo);
                    }
                }
                else if(listTwoIDS.size() !=0) {
                    if(isCompletedTwo){
                        Statistics.setLessonCompleted();
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        progressForBar = 0;
                        pbHorizontal.setProgress(0);
                        pbHorizontal.setEnabled(true);
                        pbHorizontal.setVisibility(View.VISIBLE);
                        listProgresses.clear();
                        listProgresses.addAll(getOldProgress(listTwoIDS));
                        changeFragment(fragmentLevelTwo);
                    }
                }else if(listThreeIDS.size()!=0){
                    if(isCompletedThree){
                        Statistics.setLessonCompleted();
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        progressForBar = 0;
                        pbHorizontal.setProgress(0);
                        pbHorizontal.setEnabled(true);
                        pbHorizontal.setVisibility(View.VISIBLE);
                        listProgresses.clear();
                        listProgresses.addAll(getOldProgress(listThreeIDS));
                        changeFragment(fragmentLevelThree);
                    }
                }

                else {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onSendTwoWord(String endWord, String rusWord) {

    }
}