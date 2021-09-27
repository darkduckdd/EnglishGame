package space.darkduck.englishgame;

public class Statistics {
    private static int lessonCompleted=0;
    private static int wordslearned=0;
    private static int wordsLeft;
    public  static int getLessonCompleted(){
        return lessonCompleted;
    }
    public static void setLessonCompleted(){
        lessonCompleted++;
    }
    public static int getWordsLearned(){
        return wordslearned;
    }
    public static void setWordsLearned(){
        wordslearned++;
    }
}
