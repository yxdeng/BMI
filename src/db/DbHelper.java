/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yxdeng
 */
public class DbHelper extends SQLiteOpenHelper implements DbConstant {

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        throw new UnsupportedOperationException("Not supported yet.");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + android.provider.BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT NOT NULL,"
                + BMI_TIME + " TEXT NOT NULL,"
                + BMI_RESULT + " REAL  NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
//        throw new UnsupportedOperationException("Not supported yet.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addResult(Person p) {
//        this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConstant.USER_NAME, p.getUsername());
        values.put(DbConstant.BMI_TIME, p.getTime());
        values.put(DbConstant.BMI_RESULT, p.getResult());
        getReadableDatabase().insertOrThrow(TABLE_NAME, null, values);

    }

    public Person getResult(String username) {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                new String[]{DbConstant.USER_NAME, DbConstant.BMI_TIME, DbConstant.BMI_RESULT,android.provider.BaseColumns._ID},
                DbConstant.USER_NAME +"=\""+username+"\"",
                null, null, null,
                android.provider.BaseColumns._ID + " DESC");
        Person p = null;
        if (cursor.moveToNext()) {
            p = new Person();
            p.setId(cursor.getInt(3));
            p.setResult(cursor.getDouble(2));
            p.setTime(cursor.getString(1));
            p.setUsername(cursor.getString(0));
        }
        cursor.close();
        return p;
    }

    public List getAllUserResult(String username) {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                new String[]{DbConstant.USER_NAME, DbConstant.BMI_TIME, DbConstant.BMI_RESULT,android.provider.BaseColumns._ID},
                DbConstant.USER_NAME +"=\""+username+"\"",
                null, null, null,
                android.provider.BaseColumns._ID + " DESC");
        List personlist = new ArrayList();
        while (cursor.moveToNext()) {
            Person p = new Person(cursor.getString(0), cursor.getString(1), cursor.getDouble(2));
            p.setId(cursor.getInt(3));
            personlist.add(p);
        }
        cursor.close();
        return personlist;
    }
}
