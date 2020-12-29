package com.shelter.review.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.shelter.review.provider.MyOpenHelper.TABLE_JOB;
import static com.shelter.review.provider.MyOpenHelper.TABLE_USER;

/**
 * @author: Shelter
 * Create time: 2020/12/29, 18:15.
 */
public class MyProvider extends ContentProvider {
    private MyOpenHelper openHelper;
    private SQLiteDatabase db;
    private static final UriMatcher URI_MATCHER;
    private static final String AUTHORITY = "com.example.shelter";
    private static final int MATCHER_CODE_1 = 1;
    private static final int MATCHER_CODE_2 = 2;


    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, TABLE_USER, MATCHER_CODE_1);
        URI_MATCHER.addURI(AUTHORITY, TABLE_JOB, MATCHER_CODE_2);
    }


    @Override
    public boolean onCreate() {
        openHelper = new MyOpenHelper(getContext());
        db = openHelper.getWritableDatabase();

        db.execSQL("delete from " + TABLE_USER);
        db.execSQL("insert into " + TABLE_USER + " values(1, 'Shelter')");
        db.execSQL("insert into " + TABLE_USER + " values(2, 'Panny')");

        db.execSQL("delete from " + TABLE_JOB);
        db.execSQL("insert into " + TABLE_JOB + " values(1, 'Android')");
        db.execSQL("insert into " + TABLE_JOB + " values(2, 'IOS')");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        db.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case MATCHER_CODE_1:
                tableName = TABLE_USER;
                break;
            case MATCHER_CODE_2:
                tableName = TABLE_JOB;
                break;
            default:
                break;
        }
        return tableName;
    }
}