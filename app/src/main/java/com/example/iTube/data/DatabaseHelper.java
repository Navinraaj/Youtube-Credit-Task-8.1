package com.example.iTube.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.iTube.model.User;
import com.example.iTube.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main Database Helper class used for SQLite operations
 * such as creating a table, upgrading the database and providing
 * necessary operations like insert, fetch, and fetch all records.
 *
 * @author Navin
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // onCreate is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL command to create a new table
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.FULL_NAME + " TEXT , " + Util.USERNAME + " TEXT , " + Util.PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    // onUpgrade is called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // SQL command to drop the existing table and create a new one
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + Util.TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    // Method to insert a new user into the database
    public long insertUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.USERNAME, user.getUsername());
            contentValues.put(Util.PASSWORD, user.getPassword());
            contentValues.put(Util.FULL_NAME,user.getFull_name());
            long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;
        } catch (Exception e) {
            throw e;
        }
    }

    // Method to check if a user exists in the database
    public boolean fetchUser(String username, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=? and " + Util.PASSWORD + "=?",
                    new String[]{username, password}, null, null, null);
            int numberOfRows = cursor.getCount();
            db.close();

            // If there are any rows returned, the user exists
            if (numberOfRows > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            Log.d("reached", e.getMessage());
            return false;
        }
    }

    // Method to fetch a single user object based on username and password
    public User fetchUserObject(String username, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            User user = new User();
            String selectAll = " SELECT * FROM " + Util.TABLE_NAME + " where " + Util.USERNAME + " = '" + username +
                    "' and " + Util.PASSWORD + " = '" + password + "'";
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor.moveToFirst()) {
                do {
                    user.setUser_id(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setFull_name(cursor.getString(3));
                } while (cursor.moveToNext());
            } else {
                return new User();
            }
            return user;
        } catch (Exception e) {
            Log.d("reached", e.getMessage());
            return new User();
        }
    }

    // Method to fetch a single user object based on the user_id
    public User fetchUserObject(Integer user_id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            User user = new User();
            String selectAll = " SELECT * FROM " + Util.TABLE_NAME + " where " + Util.USER_ID + " = '" + user_id + "'";
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor.moveToFirst()) {
                do {
                    user.setUser_id(cursor.getInt(0));
                    user.setUsername(cursor.getString(2));
                    user.setPassword(cursor.getString(3));
                    user.setFull_name(cursor.getString(1));
                } while (cursor.moveToNext());
            } else {
                return new User();
            }
            return user;
        } catch (Exception e) {
            Log.d("reached", e.getMessage());
            return new User();
        }
    }

    // Method to fetch all users in the database
    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUser_id(cursor.getInt(0));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setFull_name(cursor.getString(1));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }
}
