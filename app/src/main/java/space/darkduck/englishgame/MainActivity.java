package space.darkduck.englishgame;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button addButton,playButton,statisticButton,notify;
    private DatabaseHelper databaseHelper;
    // Идентификатор уведомления
    private static final int notifyId = 1;
    // Идентификатор канала
    private static String channelId = "Game channel";
    private NotificationManager notificationManager;

    private void init(){
        databaseHelper=new DatabaseHelper(MainActivity.this);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        statisticButton=findViewById(R.id.showStatistics);
        notify=findViewById(R.id.buttonNotify);
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
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String wordCount=preferences.getString("wordCount","10");
        String time=preferences.getString("setTime","12:00");
        DatabaseHelper.setWordLimit(Integer.parseInt(wordCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        notificationManager=(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notify.setOnClickListener((v)->{
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(getApplicationContext(),channelId)
                    .setAutoCancel(false)
                    .setSmallIcon(R.drawable.ic_add)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setContentTitle("Заголовок")
                    .setContentText("Test")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            notificationManager.notify(notifyId,notificationBuilder.build());
        });
    }
}