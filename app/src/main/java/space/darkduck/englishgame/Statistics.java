package space.darkduck.englishgame;

import android.util.Log;

public class Statistics {
    private static int lessonCompleted=0;
    private static int wordslearned=0;
    private static int wordsLeft;
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
}
