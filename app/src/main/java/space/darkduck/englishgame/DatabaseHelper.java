package space.darkduck.englishgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String databaseName = "EnglishRussianDictionary.db";
    private static final int databaseVersion = 3;
    private static final String wordTableName = "Dictionary";
    private static final String wordColumnId = "Id";
    private static final String columnEngWord = "EnglishWord";
    private static final String columnRusWord = "RussianWord";
    private static final String columnProgress = "Progress";
    private int maxPoint = 100;
    private int minPoint = 0;
    private int minPointsForLevelTwo = 20;
    private int minPointsForLevelThree = 60;
    private static int maxWords = 10;
    private static final String lessonTableName = "Statistics";
    private static final String lessonId = "LessonId";
    private static final String columnLessonCount = "LessonCount";
    private static final String columnWordCompleted = "WordCompleted";

    DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    public static String getWordColumnId() {
        return wordColumnId;
    }

    public static String getColumnEngWord() {
        return columnEngWord;
    }

    public static String getColumnRusWord() {
        return columnRusWord;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id, EnglishWord, RussianWord  from Dictionary", null);
        return cursor;
    }

    public String getRusWord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select RussianWord from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        String rusWord = cursor.getString(cursor.getColumnIndex(columnRusWord));
        cursor.close();
        return rusWord;
    }

    public String getEngWord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        String engWord = cursor.getString(cursor.getColumnIndex(columnEngWord));
        cursor.close();
        return engWord;
    }

    public int getProgress(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Progress from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        int number = cursor.getInt(cursor.getColumnIndex(columnProgress));
        cursor.close();
        return number;
    }

    public Cursor getWordsForLevelOne() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit " + maxWords, new String[]{String.valueOf(minPoint), String.valueOf(minPointsForLevelTwo)});
        return cursor;
    }

    public Cursor getWordsForLevelTwo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit " + maxWords, new String[]{String.valueOf(minPointsForLevelTwo), String.valueOf(minPointsForLevelThree)});
        return cursor;
    }

    public Cursor getWordsForLevelThree() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit " + maxWords, new String[]{String.valueOf(minPointsForLevelThree), String.valueOf(maxPoint)});
        return cursor;
    }

    public int getMaxId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Id= (select MAX(Id) from Dictionary)", null);
        cursor.moveToFirst();
        int number = cursor.getInt(cursor.getColumnIndex(wordColumnId));
        cursor.close();
        return number;
    }

    public boolean isHasInDatabase(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where EnglishWord=?", new String[]{word});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void updateProgress(String id, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnProgress, value);
        db.update(wordTableName, cv, wordColumnId + "=?", new String[]{id});
    }

    public void addDictionary(String engWord, String rusWord, int prog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(columnEngWord, engWord);
        cv.put(columnRusWord, rusWord);
        cv.put(columnProgress, prog);
        long result = db.insert(wordTableName, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void addStartWord(SQLiteDatabase db, String engWord, String rusWord, int progress) {
        ContentValues cv = new ContentValues();
        cv.put(columnEngWord, engWord);
        cv.put(columnRusWord, rusWord);
        cv.put(columnProgress, progress);
        db.insert(wordTableName, null, cv);
    }

    public static int getWordLimit() {
        return maxWords;
    }

    public static void setWordLimit(int value) {
        maxWords = value;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + wordTableName + " (" + wordColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnEngWord + " TEXT, " +
                columnRusWord + " TEXT, " +
                columnProgress + " INTEGER);";
        db.execSQL(query);
        FileWorker fileWorker = new FileWorker(context);
        ArrayList<String> eWord = fileWorker.readEngFile();
        ArrayList<String> rWord = fileWorker.readRusFile();
        for (int i = 0; i < eWord.size(); i++) {
            addStartWord(db, eWord.get(i), rWord.get(i), 0);
        }
        String queryLesson = "Create table " + lessonTableName + " (" + lessonId + " Integer PRIMARY KEY AUTOINCREMENT, "
                + columnLessonCount + " Integer, " + columnWordCompleted + " Integer);";
        db.execSQL(queryLesson);
        initialLessonTable(db);
    }

    private void initialLessonTable(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(columnLessonCount, 0);
        cv.put(columnWordCompleted,0);
        db.insert(lessonTableName, null, cv);
    }
    public int getLessonCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select LessonCount from Statistics where LessonId= 1", null);
        cursor.moveToFirst();
        int number = cursor.getInt(cursor.getColumnIndex(columnLessonCount));
        cursor.close();
        return number;
    }

    public void increaseLessonCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnLessonCount,getLessonCount()+1);
        db.update(lessonTableName, cv, lessonId + "=?", new String[]{"1"});
    }
    public int getWordCompleted(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select WordCompleted from Statistics where LessonId= 1", null);
        cursor.moveToFirst();
        int number = cursor.getInt(cursor.getColumnIndex(columnWordCompleted));
        cursor.close();
        return number;
    }
    public void increaseWordCompleted(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnWordCompleted,getWordCompleted()+1);
        db.update(lessonTableName, cv, lessonId + "=?", new String[]{"1"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + wordTableName);
        db.execSQL("Drop table if exists " + lessonTableName);
        onCreate(db);
    }
}