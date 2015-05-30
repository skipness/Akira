package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.FavouriteModel;
import model.HistoryModel;

public class Database{

    private static final String TAG = "SQL ---- ";
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private static final int VERSION = 1;
    private static final String DbName = "Akira.db";
    private static final String H_TableName = "History";
    private static final String F_TableName = "Favourite";
    private static final String[] COLUMNS = {"_MNAME"};


    private static class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(Context context) {
            super(context, DbName, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String SQL = "CREATE TABLE IF NOT EXISTS " + H_TableName + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "_MID INTEGER UNIQUE," +
                    "_MNAME TEXT UNIQUE" +
                    ");";

            final String SQL2 = "CREATE TABLE IF NOT EXISTS " + F_TableName + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "_MID INTEGER UNIQUE," +
                    "_MNAME TEXT UNIQUE" +
                    ");";
            db.execSQL(SQL);
            db.execSQL(SQL2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newViewsion) {
            final String SQL = "DROP TABLE " + H_TableName;
            final String SQL2 = "DROP TABLE " + F_TableName;
            db.execSQL(SQL);
            db.execSQL(SQL2);
        }

    }

    public Database(Context context){
        this.context = context;
    }

    public Database open(){
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public long insertHistoryRecord(int id, String movie_name){
        ContentValues values = new ContentValues();
        values.put("_MID",id);
        values.put("_MNAME",movie_name);
        return db.insert(H_TableName, null, values);
    }

    public long insertFavouriteRecord(int id, String movie_name){
        ContentValues values = new ContentValues();
        values.put("_MID", id);
        values.put("_MName", movie_name);
        return db.insert(F_TableName, null, values);
    }

    public List<HistoryModel> getMName(){
        Log.d(TAG, "Getting History Model");
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + H_TableName, null);
        List<HistoryModel> hList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                HistoryModel hm = new HistoryModel();
                hm.setId(cursor.getLong(1));
                hm.setMovie_name(cursor.getString(2));
                hList.add(hm);
            }while (cursor.moveToNext());
        }
        return hList;
    }

    public List<FavouriteModel> getFavouriteList(){
        open();
        Cursor cursor = null;
        List<FavouriteModel> fList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                FavouriteModel fm = new FavouriteModel();
                fm.setId(cursor.getLong(1));
                fm.setMovie_name(cursor.getString(2));
                fList.add(fm);
            }while (cursor.moveToNext());
        }
        return fList;
    }
}
