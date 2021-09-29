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

public class PlayActivity extends AppCompatActivity implements OnFragmentListener {

    private ProgressBar pbHorizontal;
    private MyDatabaseHelper myDB;
    private LevelOneFragment fragmentLevelOne;
    private LevelTwoFragment fragmentLevelTwo;
    private LevelThreeFragment fragmentLevelThree;
    private Cursor cursorLOne, cursorLTwo, cursorLThree;
    private int currentWordPosition = 0;
    private final int scoreAddPoint = 20;
    private int progressForBar = 0;
    private ArrayList<Integer> listOneIDS = new ArrayList<>();
    private ArrayList<Integer> listTwoIDS = new ArrayList<>();
    private ArrayList<Integer> listThreeIDS = new ArrayList<>();

    private void init() {
        pbHorizontal = findViewById(R.id.progressBar);
        pbHorizontal.setProgress(progressForBar);
        pbHorizontal.setVisibility(View.INVISIBLE);
        fragmentLevelOne = new LevelOneFragment();
        fragmentLevelTwo = new LevelTwoFragment();
        fragmentLevelThree = new LevelThreeFragment();
        cursorLOne = myDB.getWordsForLevelOne();
        cursorLTwo = myDB.getWordsForLevelTwo();
        cursorLThree = myDB.getWordsForLevelThree();
        addIDToList(cursorLOne, listOneIDS);
        addIDToList(cursorLTwo, listTwoIDS);
        addIDToList(cursorLThree, listThreeIDS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        myDB = new MyDatabaseHelper(PlayActivity.this);
        init();
        if (cursorLOne.getCount() != 0) {
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelOne);
        } else if (cursorLTwo.getCount() != 0) {
            pbHorizontal.setVisibility(View.VISIBLE);
            pbHorizontal.setProgress(0);
            changeFragment(fragmentLevelTwo);
        } else if (cursorLThree.getCount() != 0) {
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
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.getColumnId()));
                list.add(id);
            } while (cursor.moveToNext());
        }
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
    public void onSendData(String data) {
        switch (data) {
            case "SuccessLevelOne":
                if (listTwoIDS.size() == 0 && listThreeIDS.size() == 0) {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressForBar=0;
                    pbHorizontal.setProgress(0);
                    changeFragment(fragmentLevelTwo);
                }
                break;
            case "SuccessLevelTwo":
                if (listThreeIDS.size() == 0) {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressForBar=0;
                    pbHorizontal.setProgress(0);
                    changeFragment(fragmentLevelThree);
                }
                break;
            case "SuccessLevelThree":
                pbHorizontal.setProgress(0);
                pbHorizontal.setEnabled(false);
                pbHorizontal.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}