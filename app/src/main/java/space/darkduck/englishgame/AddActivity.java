package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Класс для добавление данных в БД
public class AddActivity extends AppCompatActivity implements OnFragmentListener {
    private DatabaseHelper myDB;
    private AddActivity activity;

    private void init() {
        myDB = new DatabaseHelper(AddActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        AddFragment addFragment=new AddFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addContainerFL, addFragment);
        ft.commit();
    }

    @Override
    public void onSendData(String data) {

    }

    @Override
    public void onSendTwoWord(String endWord, String rusWord) {
        myDB.addDictionary(endWord,rusWord,0);
    }
}