package space.darkduck.englishgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
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
    private int minPointsForLevelThree =60;
    private int maxWords=10;

    DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    public static String getColumnId(){
        return columnId;
    }
    public static String getColumnEngWord(){return columnEngWord;}
    public static String getColumnRusWord(){return  columnRusWord;}
    public static String getColumnProgress(){return  columnProgress;}

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id, EnglishWord, RussianWord  from Dictionary",null);
        return cursor;
    }

    public String getRusWord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select RussianWord from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        String rusWord=cursor.getString(cursor.getColumnIndex(columnRusWord));
        cursor.close();
        return rusWord;
    }
    public String getEngWord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select EnglishWord from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        String engWord=cursor.getString(cursor.getColumnIndex(columnEngWord));
        cursor.close();
        return engWord;
    }
    public int getProgress(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Progress from Dictionary where Id = ?", new String[]{id});
        cursor.moveToFirst();
        int number=cursor.getInt(cursor.getColumnIndex(columnProgress));
        cursor.close();
        return number;
    }

    public Cursor getWordsForLevelOne() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPoint),String.valueOf(minPointsForLevelTwo)});
        return cursor;
    }
    public Cursor getWordsForLevelTwo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPointsForLevelTwo),String.valueOf(minPointsForLevelThree)});
        return cursor;
    }
    public Cursor getWordsForLevelThree() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Progress >= ? and Progress< ? order by random() limit "+maxWords, new String[]{String.valueOf(minPointsForLevelThree),String.valueOf(maxPoint)});
        return cursor;
    }

    public int getMaxId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Dictionary where Id= (select MAX(Id) from Dictionary)",null);
        cursor.moveToFirst();
        int number=cursor.getInt(cursor.getColumnIndex(columnId));
        cursor.close();
        return number;
    }

    public  boolean isHasInDatabase(String word){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select EnglishWord from Dictionary where EnglishWord=?",new String[]{word});
        if(cursor.getCount()>0){
            return true;
        }
        return false;
    }

    public void updateProgress(String id, int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnProgress, value);
        db.update(tableName, cv, columnId + "=?", new String[]{id});
    }

    public void addDictionary (String engWord, String rusWord, int prog) {
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

    private  void addStartWord(SQLiteDatabase db, String engWord, String rusWord, int progress){
        ContentValues cv = new ContentValues();
        cv.put(columnEngWord, engWord);
        cv.put(columnRusWord, rusWord);
        cv.put(columnProgress, progress);
        db.insert(tableName, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tableName + " (" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + columnEngWord + " TEXT, " +
                columnRusWord + " TEXT, " +
                columnProgress + " INTEGER);";
        db.execSQL(query);
        addStartWord(db,"mather","мама",0);
        addStartWord(db,"father","отец",0);
        addStartWord(db,"time","время",0);
        addStartWord(db,"day","день",0);
        addStartWord(db,"way","путь",0);
        addStartWord(db,"monday","понедельник",0);
        addStartWord(db,"tuesday","вторник",0);
        addStartWord(db,"wednesday","среда",0);
        addStartWord(db,"thursday","четверг",0);
        addStartWord(db,"friday","пятница",0);
        addStartWord(db,"saturday","суббота",0);
        addStartWord(db,"sunday","воскресенье",0);
        addStartWord(db,"water","вода",0);
        addStartWord(db,"say","сказать",0);
        addStartWord(db,"man","мужчина",0);
        addStartWord(db,"woman","женщина",0);
        addStartWord(db,"world","мир",0);
        addStartWord(db,"hello","привет",0);
        addStartWord(db,"life","жизнь",0);
        addStartWord(db,"money","деньги",0);
        addStartWord(db,"eye","глаза",0);
        addStartWord(db,"person","человек",0);
        addStartWord(db,"door","дверь",0);
        addStartWord(db,"body","тело",0);
        addStartWord(db,"country","страна",0);
        addStartWord(db,"hour","час",0);
        addStartWord(db,"car","машина",0);
        addStartWord(db,"home","дом",0);
        addStartWord(db,"night","ночь",0);
        addStartWord(db,"room","комната",0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
}
