package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Класс для добавление данных в БД
public class AddActivity extends AppCompatActivity {

    private EditText engInput, rusInput;
    private Button saveButton;
    private FloatingActionButton floatingActionButton;
    private DatabaseHelper myDB;
    private WordAdapter wordAdapter;
    private RecyclerView recyclerView;
    private ScrollView scrollView;
    private ArrayList<Integer> listId=new ArrayList<>();
    private ArrayList<String> listEng=new ArrayList<>(),listRus=new ArrayList<>();


    private void init() {
        myDB = new DatabaseHelper(AddActivity.this);
        engInput = findViewById(R.id.engInput);
        rusInput = findViewById(R.id.rusInput);
        saveButton = findViewById(R.id.saveButton);
        scrollView=findViewById(R.id.scrollView);
        floatingActionButton = findViewById(R.id.floatingActionBackButton);
        recyclerView=findViewById(R.id.wordRecyclerView);
        Cursor cursor=myDB.getAllData();
        addDataToLists(cursor);
        wordAdapter=new WordAdapter(AddActivity.this,listId,listEng,listRus);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddActivity.this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        engInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });
        rusInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start,
                                               int end, Spanned dest, int dstart, int dend) {
                        if (source.equals("")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[а-яА-Я]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });

        saveButton.setOnClickListener((v) -> {
            if (engInput.getText().toString().equals("") || rusInput.getText().toString().equals("")) {
                Toast.makeText(AddActivity.this, "Введите слова", Toast.LENGTH_LONG).show();
            } else if (myDB.isHasInDatabase(engInput.getText().toString())) {
                Toast.makeText(AddActivity.this, "Это слова уже есть в базе данных", Toast.LENGTH_LONG).show();
            } else {
                myDB.addDictionary(engInput.getText().toString().trim(),
                        rusInput.getText().toString().trim(), 0);
                addNewWordInRecyclerView();
                scrollView.setScrollY(View.FOCUS_DOWN);
            }
            closeKeyboard();
            engInput.setText("");
            rusInput.setText("");
        });

        floatingActionButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addDataToLists(Cursor cursor){
        cursor.moveToFirst();
        do {
            listId.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.getColumnId())));
            listEng.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnEngWord())));
            listRus.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.getColumnRusWord())));
        }while (cursor.moveToNext());
    }

    private void closeKeyboard(){
        View view =this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void addNewWordInRecyclerView(){
        int newID=myDB.getMaxId();
        listId.add(newID);
        listEng.add(myDB.getEngWord(String.valueOf(newID)));
        listRus.add(myDB.getRusWord(String.valueOf(newID)));
        wordAdapter=new WordAdapter(AddActivity.this,listId,listEng,listRus);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddActivity.this));
    }
}