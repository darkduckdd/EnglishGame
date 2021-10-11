package space.darkduck.englishgame;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button addButton,playButton,statisticButton,notify;
    private DatabaseHelper databaseHelper;
    // Идентификатор уведомления
    private static final int notifyId = 101;
    // Идентификатор канала
    private static String channelId = "Game channel";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        playButton = findViewById(R.id.playButton);
        statisticButton=findViewById(R.id.showStatistics);
        notify=findViewById(R.id.buttonNotify);
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

        });
        databaseHelper=new DatabaseHelper(MainActivity.this);
        //databaseHelper.ResetDB();
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

    public  static  void createChannelIfNeeded(NotificationManager manager){

    }
}