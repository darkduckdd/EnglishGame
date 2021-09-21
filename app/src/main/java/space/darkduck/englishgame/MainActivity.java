package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
        myDB = new MyDatabaseHelper(MainActivity.this);
        text=findViewById(R.id.textViewTest);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener((v) -> {
            LevelOneFragment fragment = new LevelOneFragment();
            //Cursor cursor=myDB.GetEngWord(1);
            //text.setText(GetStringFromCursor(cursor));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //myDB.Update("cat",0);
            ft.replace(R.id.containerFL, fragment);
            ft.commit();
        });
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
                    //todo
                }
            } while (cursor.moveToNext());
        }
    }

    private String GetStringFromCursor(Cursor cursor){
        if (cursor.moveToFirst()) {
            String str;
            do {
                str = "";
                for (String cn : cursor.getColumnNames()) {
                    return str = str.concat(cursor.getString(cursor.getColumnIndex(cn)));
                }
            } while (cursor.moveToNext());
        }
        return null;
    }
}