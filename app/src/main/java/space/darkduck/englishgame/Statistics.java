package space.darkduck.englishgame;

import android.util.Log;

public class Statistics {
    private static int lessonCompleted=0;
    private static int wordslearned=0;
    private static int wordsLeft;
    private static int minId=1;
    private static int maxId=10;
    public  static int getLessonCompleted(){
        return lessonCompleted;
    }
    public static void setLessonCompleted(){
        lessonCompleted++;
        Log.d("Statics", String.valueOf(lessonCompleted));
    }
    public static int getWordsLearned(){
        return wordslearned;
    }
    public static void setWordsLearned(){
        wordslearned++;
        Log.d("Statics", String.valueOf(wordslearned));
    }
    public static int getMinId(){return  minId;}
    public  static  void setMinId(){minId++;}
    public static int getMaxId(){return  maxId;}
    public  static  void setMaxId(){maxId++;}
}
