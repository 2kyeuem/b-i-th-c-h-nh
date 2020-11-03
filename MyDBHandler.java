package com.example.mobilegroup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDBHandler extends SQLiteOpenHelper {
    Context dbContext;
    SQLiteDatabase db;
    public static String DATABASE_NAME = "MobileGroup.sqlite";
    String packagename="com.example.mobilegroup";
    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        dbContext = context;
    }
    public void execsql(String sql){
        db=SQLiteDatabase.openDatabase(getDatabasePath(),null,SQLiteDatabase.OPEN_READWRITE);
        db.execSQL(sql);
        db.close();
    }
    public Cursor getCursor(String sql){
        db=SQLiteDatabase.openDatabase(getDatabasePath(),null,SQLiteDatabase.OPEN_READWRITE);
        Cursor c=db.rawQuery(sql,null);
        return c;
    }
    private String getDatabasePath() {
        return "/data/data/"+packagename+"/databases/" + DATABASE_NAME;
    }

    public void copyDatabaseFromAsset() {
        try {
            File f = new File(getDatabasePath());
            if (f.exists()) {
                this.close();
            }
            else {
                this.getReadableDatabase();
                InputStream myInput = dbContext.getAssets().open(DATABASE_NAME);
                String outFileName = getDatabasePath();
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (Exception ex) {
            Log.e("LOI", ex.toString());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
