package space.darkduck.englishgame;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.ActivityNavigator;

public class NotificationReceiver extends BroadcastReceiver {
    public static String channelId="darkduck";
    private static int notifyId=101;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 =new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle("EnglishGame")
                .setContentText("Пришло время обучаться")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notifyId,builder.build());
    }
}
