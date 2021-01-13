package com.example.assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Pass_Reset_Applications.db";
    public static final String DATA_TABLE = "Application_Data";
    public static final String APP_ID = "app_id";
    public static final String NAME = "name";
    public static final String ROLL_NO = "rollno";
    public static final String PROGRAM = "program";
    public static final String DEPARTMENT = "dept";
    public static final String EMAIL = "email";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";
    public static final String DATE = "date";
    public static final String APP_STATUS = "app_status";
    public static final String CO_APPROV = "co_approval";
    public static final String CO_COMMENT = "co_comment";
    public static final String AD_APPROV = "ad_approval";
    public static final String AD_COMMENT = "ad_comment";
    public static final String APP_TABLE = "Applicants";
    public static final String APP_UNAME = "app_uname";
    public static final String APP_PASS = "app_pass";
    public static final String CO_TABLE = "Coordinator";
    public static final String CO_UNAME = "co_uname";
    public static final String CO_PASS = "co_pass";
    public static final String ADMIN_TABLE = "Admin";
    public static final String ADMIN_UNAME = "admin_uname";
    public static final String ADMIN_PASS = "admin_pass";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+APP_TABLE+" ("+APP_UNAME+" TEXT PRIMARY KEY, "+APP_PASS+" TEXT);";
        db.execSQL(query);
        db.execSQL("INSERT INTO "+APP_TABLE+" ("+APP_UNAME+","+APP_PASS+") VALUES(\"user1\",\"pass1\");");
        query = "CREATE TABLE "+CO_TABLE+" ("+CO_UNAME+" TEXT PRIMARY KEY, "+CO_PASS+" TEXT);";
        db.execSQL(query);
        db.execSQL("INSERT INTO "+CO_TABLE+" ("+CO_UNAME+","+CO_PASS+") VALUES(\"coordinator\",\"copass1\");");
        query = "CREATE TABLE "+ADMIN_TABLE+" ("+ADMIN_UNAME+" TEXT PRIMARY KEY, "+ADMIN_PASS+" TEXT);";
        db.execSQL(query);
        db.execSQL("INSERT INTO "+ADMIN_TABLE+" ("+ADMIN_UNAME+","+ADMIN_PASS+") VALUES(\"admin\",\"adminpass1\");");
        query = "CREATE TABLE "+DATA_TABLE+" ("+APP_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+APP_UNAME+" TEXT, "+
                NAME+" TEXT, "+ROLL_NO+" TEXT, "+PROGRAM+" TEXT, "+DEPARTMENT+" TEXT, "+EMAIL+" TEXT, "+SUBJECT+" TEXT, "+
                BODY+" TEXT, "+DATE+" TEXT, "+APP_STATUS+" TEXT DEFAULT \"Pending\", "+CO_APPROV+" TEXT DEFAULT \"Pending\", "+
                CO_COMMENT+" TEXT, "+AD_APPROV+" TEXT DEFAULT \"Pending\", "+AD_COMMENT+" TEXT, " +
                "FOREIGN KEY("+APP_UNAME+") REFERENCES "+APP_TABLE+"("+APP_UNAME+"));";
        db.execSQL(query);
        Log.i("DB","tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+DATA_TABLE+";";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+APP_TABLE+";";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+CO_TABLE+";";
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+ADMIN_TABLE+";";
        db.execSQL(query);
        this.onCreate(db);
    }

    public boolean insert_app_data(String app_uname,String name,String rollno,String program,String dept,
                                   String email,String subject,String body,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_UNAME,app_uname);
        values.put(NAME,name);
        values.put(ROLL_NO,rollno);
        values.put(PROGRAM,program);
        values.put(DEPARTMENT,dept);
        values.put(EMAIL,email);
        values.put(SUBJECT,subject);
        values.put(BODY,body);
        values.put(DATE,date);

        long result = db.insert(DATA_TABLE,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getUserData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+DATA_TABLE+" WHERE "+APP_UNAME+ "=\""+username+"\";",null);
        return data;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+DATA_TABLE,null);
        return data;
    }
    public Cursor getAdminData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+DATA_TABLE+" WHERE "+CO_APPROV+"=\"Approve\";",null);
        return data;
    }

    public Cursor getLastRecord(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+DATA_TABLE+" ORDER BY "+APP_ID+" DESC LIMIT 1;",null);
        return data;
    }

    public Cursor findApplicant(String uname,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+APP_TABLE+" WHERE "+
                APP_UNAME+"=\""+uname+"\" AND "+APP_PASS+"=\""+pass+"\";",null);
        return data;
    }
    public Cursor findCoordinator(String uname,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+CO_TABLE+" WHERE "+
                CO_UNAME+"=\""+uname+"\" AND "+CO_PASS+"=\""+pass+"\";",null);
        return data;
    }
    public Cursor findAdmin(String uname,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ADMIN_TABLE+" WHERE "+
                ADMIN_UNAME+"=\""+uname+"\" AND "+ADMIN_PASS+"=\""+pass+"\";",null);
        return data;
    }
    public void editCoColumns(int appid,String status,String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+DATA_TABLE+" SET "+CO_APPROV+"=\""+status+"\", "+
                CO_COMMENT+"=\""+comment+"\" WHERE "+APP_ID+"="+appid+";";
        db.execSQL(query);
    }
    public void editAdColumns(int appid,String status,String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+DATA_TABLE+" SET "+AD_APPROV+"=\""+status+"\", "+
                AD_COMMENT+"=\""+comment+"\" WHERE "+APP_ID+"="+appid+";";
        db.execSQL(query);
    }
    public void setAppStatus(int appid, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+DATA_TABLE+" SET "+APP_STATUS+"=\""+status+"\" WHERE "+APP_ID+"="+appid+";";
        db.execSQL(query);
    }
}
