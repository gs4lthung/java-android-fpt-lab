package com.example.pe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "group_management";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GROUPS = "groups";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String TABLE_GROUP_USERS = "group_users";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String COLUMN_USER_GROUP_ID = "user_group_id";

    private static final String COLUMN_USER_GROUP_NAME = "user_group_name";

    private static DatabaseHandler instance;

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGroupsTable = "CREATE TABLE " + TABLE_GROUPS + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT NOT NULL);";
        db.execSQL(createGroupsTable);

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_USER_NAME + " TEXT NOT NULL, " +
                COLUMN_USER_PHONE + " TEXT NOT NULL);";
        db.execSQL(createUsersTable);

        String createGroupUsersTable = "CREATE TABLE " + TABLE_GROUP_USERS + " (" +
                COLUMN_GROUP_ID + " TEXT NOT NULL, " +
                COLUMN_USER_GROUP_ID + " TEXT NOT NULL, " +
                COLUMN_USER_GROUP_NAME + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_GROUP_ID + ") REFERENCES " + TABLE_GROUPS + "(" + COLUMN_ID + "), " +
                "FOREIGN KEY(" + COLUMN_USER_GROUP_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), " +
                "PRIMARY KEY(" + COLUMN_GROUP_ID + ", " + COLUMN_USER_GROUP_ID + "))";
        db.execSQL(createGroupUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        onCreate(db);
    }

    public ArrayList<GroupModel> getAllGroups() {
        ArrayList<GroupModel> groups = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROUPS, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                String id = idIndex >= 0 ? cursor.getString(idIndex) : "";
                String title = titleIndex >= 0 ? cursor.getString(titleIndex) : "";
                GroupModel group = new GroupModel(id, title);
                groups.add(group);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return groups;
    }

    public ArrayList<UserModel> getUsersInGroup(String groupId) {
        ArrayList<UserModel> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT u." + COLUMN_USER_ID + ", u." + COLUMN_USER_NAME + ", u." + COLUMN_USER_PHONE +
                " FROM " + TABLE_USERS + " u INNER JOIN " + TABLE_GROUP_USERS + " gu ON u." + COLUMN_USER_ID + " = gu." + COLUMN_USER_GROUP_ID +
                " WHERE gu." + COLUMN_GROUP_ID + " = ?", new String[]{groupId});

        if (cursor.moveToFirst()) {
            do {
                int userIdIndex = cursor.getColumnIndex(COLUMN_USER_ID);
                int userNameIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
                int userPhoneIndex = cursor.getColumnIndex(COLUMN_USER_PHONE);
                String userId = userIdIndex >= 0 ? cursor.getString(userIdIndex) : "";
                String userName = userNameIndex >= 0 ? cursor.getString(userNameIndex) : "";
                String userPhone = userPhoneIndex >= 0 ? cursor.getString(userPhoneIndex) : "";
                UserModel user = new UserModel(userId, userName, userPhone);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public void addGroup(GroupModel group) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_GROUPS + " (" + COLUMN_ID + ", " + COLUMN_TITLE + ") VALUES (?, ?)";
        db.execSQL(sql, new Object[]{group.getId(), group.getTitle()});
        db.close();
    }

    public void addUserToGroup(String groupId, UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_GROUP_USERS + " (" + COLUMN_GROUP_ID + ", " + COLUMN_USER_GROUP_ID + ", " + COLUMN_USER_GROUP_NAME + ") VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{groupId, user.getId(), user.getName()});
        db.close();
    }

    public void addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_USERS + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_PHONE + ") VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{user.getId(), user.getName(), user.getPhone()});
        db.close();
    }

    public void deleteGroup(String groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_GROUPS + " WHERE " + COLUMN_ID + " = ?", new String[]{groupId});
        db.execSQL("DELETE FROM " + TABLE_GROUP_USERS + " WHERE " + COLUMN_GROUP_ID + " = ?", new String[]{groupId});
        db.close();
    }

    public void deleteUser(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});
        db.execSQL("DELETE FROM " + TABLE_GROUP_USERS + " WHERE " + COLUMN_USER_GROUP_ID + " = ?", new String[]{userId});
        db.close();
    }

    public void updateGroup(GroupModel group) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_GROUPS + " SET " + COLUMN_TITLE + " = ? WHERE " + COLUMN_ID + " = ?";
        db.execSQL(sql, new Object[]{group.getTitle(), group.getId()});
        db.close();
    }

    public void updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_USERS + " SET " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_PHONE + " = ? WHERE " + COLUMN_USER_ID + " = ?";
        db.execSQL(sql, new Object[]{user.getName(), user.getPhone(), user.getId()});
        db.close();
    }

    public boolean isGroupExists(String groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_GROUPS + " WHERE " + COLUMN_ID + " = ?", new String[]{groupId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean isUserExists(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_GROUPS);
        db.execSQL("DELETE FROM " + TABLE_USERS);
        db.execSQL("DELETE FROM " + TABLE_GROUP_USERS);
        db.close();

    }

    public void closeDatabase() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
                String userPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE));
                users.add(new UserModel(userId, userName, userPhone));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }


}
