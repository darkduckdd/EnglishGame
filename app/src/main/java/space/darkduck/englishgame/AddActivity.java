package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Класс для добавление данных в БД
public class AddActivity extends AppCompatActivity implements OnFragmentListener {
    private DatabaseHelper myDB;
    private FloatingActionButton tableShow,backButton;
    private ArrayList<Integer> listID=new ArrayList<>();
    private ArrayList<String> listEngWord=new ArrayList<>(),listRusWord=new ArrayList<>();
    private AddFragment addFragment;
    private ShowFragment showFragment;

    private void init() {
        myDB = new DatabaseHelper(AddActivity.this);
        tableShow=findViewById(R.id.floatingShowTableActionButton);
        backButton=findViewById(R.id.floatingBackActionButton);
        tableShow.setOnClickListener((v)->{
            showFragment=new ShowFragment();
            Cursor cursor=myDB.getAllData();
            addDataToList(cursor);
            showFragment.setDataRecyclerView(listID,listEngWord,listRusWord);
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.addContainerFL,showFragment);
            ft.commit();
            changeShowButton(true);
        });
        backButton.setOnClickListener((v)->{
            Intent intent=new Intent(AddActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addDataToList(Cursor cursor){
        cursor.moveToFirst();
        do {
            listID.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.getColumnId())));
            listEngWord.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnEngWord())));
            listRusWord.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnRusWord())));
        }while (cursor.moveToNext());
    }

    private void changeShowButton(boolean isADD){
        if(isADD){
            tableShow.setImageResource(R.drawable.ic_add);
            tableShow.setOnClickListener((v)->{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.addContainerFL, addFragment);
                ft.commit();
                changeShowButton(false);
            });
        }
        else {
            tableShow.setImageResource(R.drawable.ic_baseline_table_rows_24);
            tableShow.setOnClickListener((v)->{
                Cursor cursor=myDB.getAllData();
                listID.clear();
                listEngWord.clear();
                listRusWord.clear();
                addDataToList(cursor);
                showFragment.setDataRecyclerView(listID,listEngWord,listRusWord);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.addContainerFL,showFragment);
                ft.commit();
                changeShowButton(true);
            });
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        addFragment=new AddFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addContainerFL, addFragment);
        ft.commit();
    }

    @Override
    public void onResultLevel(LevelResult result) {

    }

    @Override
    public void onSendTwoWord(String endWord, String rusWord) {
        myDB.addDictionary(endWord,rusWord,0);
    }
}