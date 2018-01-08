package by.bstu.fit.kaa.notesapp.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shiro on 08.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "NOTES";
    public static final String TITLE = "TITLE";
    public static final String IMPORTANCE = "IMPORTANCE";
    public static final String TEXT = "NOTE_TEXT";


    public DBHelper(Context context){
        super(context, "notesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
            + "_id integer primary key autoincrement,"
            + TITLE + " text,"
            + IMPORTANCE + " integer,"
            + TEXT + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
