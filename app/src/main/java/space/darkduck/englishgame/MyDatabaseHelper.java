package space.darkduck.englishgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String databaseName = "EnglishRussianDictionary.db";
    private static final int databaseVersion = 1;
    private static final String tableName = "Dictionary";
    private static final String columnId = "Id";
    private static final String columnEngWord = "EnglishWord";
    private static final String columnRusWord = "RussianWord";
    private static final String columnProgress = "Progress";
    private  int maxPoint=100;
    private int minPoint=0;
    private int minPointsForLevelTwo=20;
    private int minPointsForLevelThree =50;
    private int maxWords=10;

    MyDatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    void addDictionary (String engWord, String rusWord, int prog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(columnEngWord, engWord);
        cv.put(columnRusWord, rusWord);
        cv.put(columnProgress, prog);
        long result = db.insert(tableName, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor GetRusWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select RussianWord from Dictionary where EnglishWord = ?", new String[]{word});
        return cursor;
    }

    public Cursor GetEngWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where RussianWord = ?", new String[]{word});
        return cursor;
    }

    public void Update(String word, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnProgress, value);
        db.update(tableName, cv, columnEngWord + "=? or "+columnRusWord+"=?", new String[]{word});
    }

    public Cursor GetWordsForLevelOne() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPoint),String.valueOf(minPointsForLevelTwo)});
        return cursor;
    }
    public Cursor GetWordsForLevelTwo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPointsForLevelTwo),String.valueOf(minPointsForLevelThree)});
        return cursor;
    }
    public Cursor GetWordsForLevelThree() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPointsForLevelThree),String.valueOf(maxPoint)});
        return cursor;
    }
    public int GetProgress(String word){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Progress from Dictionary where EnglishWord = ?", new String[]{word});
        cursor.moveToFirst();
        int number=cursor.getInt(cursor.getColumnIndex(columnProgress));
        cursor.close();
        return number;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnEngWord + " TEXT, " +
                columnRusWord + " TEXT, " +
                columnProgress + " INTEGER);";
        db.execSQL(query);

        addStartWordToDatabase(db,"mather","мама",0);
        addStartWordToDatabase(db,"father","отец",0);
        addStartWordToDatabase(db,"time","время",0);
        addStartWordToDatabase(db,"day","день",0);
        addStartWordToDatabase(db,"way","путь",0);
        addStartWordToDatabase(db,"monday","понедельник",0);
        addStartWordToDatabase(db,"tuesday","вторник",0);
        addStartWordToDatabase(db,"wednesday","среда",0);
        addStartWordToDatabase(db,"thursday","четверг",0);
        addStartWordToDatabase(db,"friday","пятница",0);
        addStartWordToDatabase(db,"saturday","суббота",0);
        addStartWordToDatabase(db,"sunday","воскресенье",0);
        addStartWordToDatabase(db,"water","вода",0);
        addStartWordToDatabase(db,"say","сказать",0);
        addStartWordToDatabase(db,"man","мужчина",0);
        addStartWordToDatabase(db,"woman","женщина",0);
        addStartWordToDatabase(db,"world","мир",0);
        addStartWordToDatabase(db,"hello","привет",0);
        addStartWordToDatabase(db,"life","жизнь",0);
        addStartWordToDatabase(db,"money","деньги",0);
        addStartWordToDatabase(db,"eye","глаза",0);
        addStartWordToDatabase(db,"person","человек",0);
        addStartWordToDatabase(db,"door","дверь",0);
        addStartWordToDatabase(db,"body","тело",0);
        addStartWordToDatabase(db,"country","страна",0);
        addStartWordToDatabase(db,"hour","час",0);
        addStartWordToDatabase(db,"car","машина",0);
        addStartWordToDatabase(db,"home","дом",0);
        addStartWordToDatabase(db,"night","ночь",0);
        addStartWordToDatabase(db,"room","комната",0);
    }
    private  void addStartWordToDatabase(SQLiteDatabase db, String engWord, String rusWord, int progress){
        ContentValues cv = new ContentValues();
        cv.put(columnEngWord, engWord);
        cv.put(columnRusWord, rusWord);
        cv.put(columnProgress, progress);
        db.insert(tableName, null, cv);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
