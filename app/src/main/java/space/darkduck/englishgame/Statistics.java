package space.darkduck.englishgame;

import android.text.format.DateUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Statistics {
    private static int lessonCompleted=0;
    private static int wordslearned=0;
    private static Calendar calendar;
    public  static int getLessonCompleted(){
        return lessonCompleted;
    }
    public static void setLessonCompleted() {
        lessonCompleted++;
    }
    public static int getWordsLearned(){
        return wordslearned;
    }
    public static void setWordsLearned(){
        wordslearned++;
    }
    public  static  void getYear(){
        calendar=Calendar.getInstance();
        Log.d("TESTAD", calendar.get(Calendar.YEAR)+":y");
        Log.d("TESTAD", "work");
    }
}
