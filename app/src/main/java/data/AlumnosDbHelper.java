package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlumnosDbHelper extends SQLiteOpenHelper {

    public AlumnosDbHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table alumno(nombre text , apellido text ,dni integer primary key unique, legajo integer unique) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists alumno");
        sqLiteDatabase.execSQL("create table alumno(nombre text, apellido text ,dni integer primary key, legajo integer)");
    }
}
