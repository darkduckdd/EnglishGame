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

    MyDatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }
    void addDictionary(String engWord, String rusWord, int prog) {
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

    public Cursor GetRusWord(String word){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select RussianWord from Dictionary where EnglishWord = ?",new String[]{word});
        return cursor;
    }

    public Cursor GetEngWord(String word){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select EnglishWord from Dictionary where RussianWord = ?",new String[]{word});
        return cursor;
    }

    public Cursor GetEngWord(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select EnglishWord from Dictionary where Id = ?",new String[]{String.valueOf(id)});
        return cursor;
    }
     public void Update(String word,int value ){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(columnProgress,value);
         db.update(tableName, cv, columnEngWord + "=?", new String[] { word });
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnEngWord + " TEXT, " +
                columnRusWord + " TEXT, " +
                columnProgress + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }



}
