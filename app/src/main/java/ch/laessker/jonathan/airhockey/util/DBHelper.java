package ch.laessker.jonathan.airhockey.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ch.laessker.jonathan.airhockey.AirHockeyDatenbank;

/**
 * Created by Roman Lenov on 06.06.2017.
 */

public class DBHelper {
    final private AirHockeyDatenbank AHDB;
    final private SQLiteDatabase db;

    public DBHelper(Context applicationContext) {

        AHDB = AirHockeyDatenbank.getInstance(applicationContext);
        db = AHDB.getWritableDatabase();
    }

    public SettingsValues returnSavedValues() {

        String[] projection = {
                "effects",
                "soundtrack",
                "difficulty"
        };
        String selection = "id" + " = ?";
        String[] selectionArgs = { "1" };
        String sortOrder =
                "effects" + " DESC";

        Cursor cursor = db.query(
                "settings",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        int effects = 10;
        int soundtrack = 10;
        int difficulty = 10;

        while(cursor.moveToNext()) {
            effects = cursor.getInt(0);
            soundtrack = cursor.getInt(1);
            difficulty = cursor.getInt(2);
        }
        cursor.close();

        SettingsValues settingsValues = new SettingsValues(effects, soundtrack, difficulty);

        return settingsValues;
    }

    public void setValues(int effects, int soundtrack, int difficulty) {

        ContentValues values = new ContentValues();
        values.put("effects", effects);
        values.put("soundtrack", soundtrack);
        values.put("difficulty", difficulty);

        String selection = "id" + " = ?";
        String[] selectionArgs = { "1" };

        long newRowId = db.update("settings", values, selection, selectionArgs);

    }

}
