package space.darkduck.englishgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String databaseName = "EnglishRussianDictionary.db";
    private static final int databaseVersion = 1;
    private static final String tableName = "Dictionary";
    private static final String columnId = "Id";
    private static final String columnEngWord = "EnglishWord";
    private static final String columnRusWord = "RussianWord";
    private static final String columnProgress = "Progress";
    private  int maxPoint=3;

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

    public Cursor GetWordsForLevel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Progress < ? order by random() limit 10", new String[]{String.valueOf(maxPoint)});
        return cursor;
    }
    public Cursor GetProgress(String word){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Progress from Dictionary where EnglishWord = ?", new String[]{word});
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnEngWord + " TEXT, " +
                columnRusWord + " TEXT, " +
                columnProgress + " INTEGER);";
        db.execSQL(query);

        createOriginDictionary(db,"mather","мама",0);
        createOriginDictionary(db,"father","отец",0);
        createOriginDictionary(db,"time","время",0);
        createOriginDictionary(db,"day","день",0);
        createOriginDictionary(db,"way","путь",0);
        createOriginDictionary(db,"monday","понедельник",0);
        createOriginDictionary(db,"tuesday","вторник",0);
        createOriginDictionary(db,"wednesday","среда",0);
        createOriginDictionary(db,"thursday","четверг",0);
        createOriginDictionary(db,"friday","пятница",0);
        createOriginDictionary(db,"saturday","суббота",0);
        createOriginDictionary(db,"sunday","воскресенье",0);
        createOriginDictionary(db,"water","вода",0);
        createOriginDictionary(db,"say","сказать",0);
        createOriginDictionary(db,"man","мужчина",0);
        createOriginDictionary(db,"woman","женщина",0);
        createOriginDictionary(db,"world","мир",0);
        createOriginDictionary(db,"hello","привет",0);
        createOriginDictionary(db,"life","жизнь",0);
        createOriginDictionary(db,"money","деньги",0);
        createOriginDictionary(db,"eye","глаза",0);
        createOriginDictionary(db,"person","человек",0);
        createOriginDictionary(db,"door","дверь",0);
        createOriginDictionary(db,"body","тело",0);
        createOriginDictionary(db,"country","страна",0);
        createOriginDictionary(db,"hour","час",0);
        createOriginDictionary(db,"car","машина",0);
        createOriginDictionary(db,"home","дом",0);
        createOriginDictionary(db,"night","ночь",0);
        createOriginDictionary(db,"room","комната",0);
    }
    private  void createOriginDictionary(SQLiteDatabase db,String engWord,String rusWord,int progress){
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
