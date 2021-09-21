package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    FloatingActionButton addButton, playButton;
    MyDatabaseHelper myDB;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.textViewTest);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener((v) -> {
            LevelOneFragment fragment = new LevelOneFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.containerFL, fragment);
            ft.commit();
        });
        myDB = new MyDatabaseHelper(MainActivity.this);

    }

    @Override
    public void OnSendData(String data) {
        Cursor cursor = myDB.GetRusWord(data);
        if (cursor.moveToFirst()) {
            String str;
            do {
                str = "";
                for (String cn : cursor.getColumnNames()) {
                    str = str.concat(cursor.getString(cursor.getColumnIndex(cn)));
                    text.setText(str);
                }
            } while (cursor.moveToNext());
        }

    }
}