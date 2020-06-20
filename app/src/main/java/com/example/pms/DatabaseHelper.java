package com.example.pms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String SERIAL_NO = "SerialNo";
    private static final String PASS_NO = "PassNo";
    private static final String ADHAAR_CARD_NO = "AdhaarCardNo";
    private static final String ELECTION_ID = "ElectionId";
    private static final String NAME = "Name";
    private static final String FATHER_NAME = "FatherName";
    private static final String AGE = "Age";
    private static final String SEX = "Sex";
    private static final String PERMANENT_ADDRESS = "PermanentAddress";
    private static final String TEMP_ADDRESS = "TempAddress";
    private static final String MOBILE_NO = "MobileNumber";
    private static final String DP_THUMBNAIL = "DpTumbnail";

    private Context mContext;
    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        if (false) {
            createTable = "CREATE TABLE " + TABLE_NAME +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SERIAL_NO + " TEXT," +
                    PASS_NO + " TEXT," +
                    ADHAAR_CARD_NO + " TEXT," +
                    ELECTION_ID + " TEXT," +
                    NAME + " TEXT," +
                    FATHER_NAME + " TEXT," +
                    AGE + " TEXT," +
                    SEX + " TEXT," +
                    PERMANENT_ADDRESS + " TEXT," +
                    TEMP_ADDRESS + " TEXT," +
                    MOBILE_NO + " TEXT)";
        } else {
            createTable = "CREATE TABLE " + TABLE_NAME +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SERIAL_NO + " TEXT," +
                    PASS_NO + " TEXT," +
                    ADHAAR_CARD_NO + " TEXT," +
                    ELECTION_ID + " TEXT," +
                    NAME + " TEXT," +
                    FATHER_NAME + " TEXT," +
                    AGE + " TEXT," +
                    SEX + " TEXT," +
                    PERMANENT_ADDRESS + " TEXT," +
                    TEMP_ADDRESS + " TEXT," +
                    MOBILE_NO + " TEXT," +
                    DP_THUMBNAIL + " BLOB NOT NULL)";
        }
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addData(String SerialNo, String PassNo, String AdhaarCardNo, String ElectionId,
                           String Name,String FatherName,String Age,String Sex,
                           String PermanentAddress, String TempAddress, String MobileNo,
                           byte[] dpThumbnail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SERIAL_NO, SerialNo);
        contentValues.put(PASS_NO, PassNo);
        contentValues.put(ADHAAR_CARD_NO, AdhaarCardNo);
        contentValues.put(ELECTION_ID, ElectionId);
        contentValues.put(NAME, Name);
        contentValues.put(FATHER_NAME, FatherName);
        contentValues.put(AGE, Age);
        contentValues.put(SEX, Sex);
        contentValues.put(PERMANENT_ADDRESS, PermanentAddress);
        contentValues.put(TEMP_ADDRESS, TempAddress);
        contentValues.put(MOBILE_NO, MobileNo);
        contentValues.put(DP_THUMBNAIL, dpThumbnail);

        Log.d("RAJ", "addData: Adding " + SerialNo + " to " + TABLE_NAME + " md5(blob): " + getMD5(dpThumbnail));
        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static String getMD5(byte[] source) {
        StringBuilder sb = new StringBuilder();
        java.security.MessageDigest md5 = null;
        try {
            md5 = java.security.MessageDigest.getInstance("MD5");
            md5.update(source);
        } catch (NoSuchAlgorithmException e) {
        }
        if (md5 != null) {
            for (byte b : md5.digest()) {
                sb.append(String.format("%02X", b));
            }
        }
        return sb.toString();
    }

    public boolean addData(String SerialNo, String PassNo, String AdhaarCardNo, String ElectionId,
                           String Name,String FatherName,String Age,String Sex,
                           String PermanentAddress, String TempAddress, String MobileNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SERIAL_NO, SerialNo);
        contentValues.put(PASS_NO, PassNo);
        contentValues.put(ADHAAR_CARD_NO, AdhaarCardNo);
        contentValues.put(ELECTION_ID, ElectionId);
        contentValues.put(NAME, Name);
        contentValues.put(FATHER_NAME, FatherName);
        contentValues.put(AGE, Age);
        contentValues.put(SEX, Sex);
        contentValues.put(PERMANENT_ADDRESS, PermanentAddress);
        contentValues.put(TEMP_ADDRESS, TempAddress);
        contentValues.put(MOBILE_NO, MobileNo);

        Log.d(TAG, "addData: Adding " + SerialNo + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getDataCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public ArrayList<HashMap<String,String>> getData() {
        ArrayList<HashMap<String,String>> userList = new ArrayList<HashMap<String,String>>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        Log.d("RAJ>>>>>>>>", "getData: data.count(): "+ data.getCount());
        while(data.moveToNext()) {
            Log.d("RAJ", "getData: getting data for  " + data.getString(1) + " from " + TABLE_NAME);
            HashMap<String,String> userEntry = new HashMap<String,String>();
            userEntry.put(SERIAL_NO, data.getString(1));
            userEntry.put(PASS_NO, data.getString(2));
            userEntry.put(ADHAAR_CARD_NO, data.getString(3));
            userEntry.put(ELECTION_ID, data.getString(4));
            userEntry.put(NAME, data.getString(5));
            userEntry.put(FATHER_NAME, data.getString(6));
            userEntry.put(AGE, data.getString(7));
            userEntry.put(SEX, data.getString(8));
            userEntry.put(PERMANENT_ADDRESS, data.getString(9));
            userEntry.put(TEMP_ADDRESS, data.getString(10));
            userEntry.put(MOBILE_NO, data.getString(11));
            userList.add(userEntry);
        }

        Log.d("RAJ>>>>>>>>", "getData: userList.size(): "+ userList.size());
        for (int i= 0; i < userList.size(); i++) {
            Log.d("RAJ", "GetData for user:");
        }
        Log.d("RAJ<<<<<<<<<<", "getData: userList.size(): "+ userList.size());
        return userList;
    }

    public ArrayList<HashMap<String,String>> getData(String search_text) {
        ArrayList<HashMap<String,String>> userList = new ArrayList<HashMap<String,String>>();
        SQLiteDatabase db = this.getWritableDatabase();

        //Wild card Syntax
        String nameWildCard = "%" + search_text + "%";
        String serialNoWildCard = "%" + search_text + "%";

        //Query
        String query = "SELECT * FROM " + TABLE_NAME + " where " + NAME + " LIKE ? or "+ SERIAL_NO + " LIKE ? ";
        String[] selectionArgs = new String[] {nameWildCard, serialNoWildCard};
        Cursor data = db.rawQuery(query, selectionArgs);
        data.moveToFirst();

        Log.d("RAJ>>>>>>>>", "getData: data.count(): "+ data.getCount());
        while(data.moveToNext()) {
            Log.d("RAJ", "getData: getting data for  " + data.getString(1) + " from " + TABLE_NAME);
            HashMap<String,String> userEntry = new HashMap<String,String>();
            userEntry.put(SERIAL_NO, data.getString(1));
            userEntry.put(PASS_NO, data.getString(2));
            userEntry.put(ADHAAR_CARD_NO, data.getString(3));
            userEntry.put(ELECTION_ID, data.getString(4));
            userEntry.put(NAME, data.getString(5));
            userEntry.put(FATHER_NAME, data.getString(6));
            userEntry.put(AGE, data.getString(7));
            userEntry.put(SEX, data.getString(8));
            userEntry.put(PERMANENT_ADDRESS, data.getString(9));
            userEntry.put(TEMP_ADDRESS, data.getString(10));
            userEntry.put(MOBILE_NO, data.getString(11));
            userList.add(userEntry);
        }

        Log.d("RAJ>>>>>>>>", "getData: userList.size(): "+ userList.size());
        for (int i= 0; i < userList.size(); i++) {
            Log.d("RAJ", "GetData for user:");
        }
        Log.d("RAJ<<<<<<<<<<", "getData: userList.size(): "+ userList.size());
        return userList;
    }

    public HashMap<String,byte[]> getDpThumbnail() {
        HashMap<String, byte[]> dpThumbnail = new HashMap<String, byte[]>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        while (data.moveToNext()) {
            Log.d("RAJ", "getDpThumbnail: for " + data.getString(1) +
                    " md5(blob)" + getMD5(data.getBlob(12)));
            dpThumbnail.put(data.getString(1), data.getBlob(12));
        }
        return dpThumbnail;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + SERIAL_NO + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + SERIAL_NO +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + SERIAL_NO + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + SERIAL_NO + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
























