package space.darkduck.englishgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button addButton,playButton,statisticButton;
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private MyDatabaseHelper myDB;
    private ArrayList<String> engList=new ArrayList<>(),rusList=new ArrayList<>();
    private ArrayList<Integer> progressList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDatabaseHelper(MainActivity.this);
        Cursor cursor= myDB.getAllWords();
        sort(cursor);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        statisticButton=findViewById(R.id.showStatistics);
        drawerLayout=findViewById(R.id.drawerLayout);
        recyclerView=findViewById(R.id.recyclerView);
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener((v)->{
            wordAdapter=new WordAdapter(MainActivity.this,engList,rusList,progressList);
            recyclerView.setAdapter(wordAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            recyclerView.setHasFixedSize(false);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        playButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
            startActivity(intent);
        });
        statisticButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
    }

    private void sort(Cursor cursor){
        cursor.moveToFirst();
        do {
            engList.add(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.getColumnEngWord())));
            rusList.add(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.getColumnRusWord())));
            progressList.add(cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.getColumnProgress())));
        }while (cursor.moveToNext());
    }
}