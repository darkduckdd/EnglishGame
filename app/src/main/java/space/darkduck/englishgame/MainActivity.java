package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    FloatingActionButton addButton, playButton;
    MyDatabaseHelper myDB;
    ArrayList<String> levelWords = new ArrayList();
    LevelOneFragment fragmentLevelOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        myDB = new MyDatabaseHelper(MainActivity.this);
        GetWordsAndSaveInArray(myDB, levelWords);

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
        return levelWords.get(0);
    }

    private void GetWordsAndSaveInArray(MyDatabaseHelper myDB, ArrayList<String> levelWords) {
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
            case "Success":
                myDB.Update(levelWords.get(0), 1);
                LevelTwoFragment fragment = new LevelTwoFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.containerFL, fragment);
                ft.commit();
                break;
            case "Fail":
                fragmentLevelOne.SetTranslate(GetStringFromCursor(myDB.GetRusWord(levelWords.get(0))));
                break;
        }
    }
}