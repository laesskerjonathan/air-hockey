package ch.laessker.jonathan.airhockey;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roman Lenov on 22.04.2017.
 */

public class AirHockeyDatenbank extends SQLiteOpenHelper {

    private static final String DATENBANK_NAME = "settings.db";

    private static final int DATENBANK_VERSION = 1;

    public AirHockeyDatenbank(Context context){
        super(
                context,
                DATENBANK_NAME,
                null,
                DATENBANK_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        //Settings Tabelle erstellen
        db.execSQL("CREATE TABLE settings(" +
                "id INTEGER PRIMARY KEY, " +
                "effects INTEGER," +
                "soundtrack INTEGER," +
                "difficulty INTEGER);");
        //Default Settings eintragen
        db.execSQL("INSERT INTO settings " +
                "VALUES (1, 1, 1, 1);");
        //saveGame Tabelle erstellen
        db.execSQL("CREATE TABLE saveGame(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT " +
                "playerPoints INTEGER, " +
                "enemyPoints INTEGER, " +
                "playTime INTEGER); ");
    }

    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE settings;");
        db.execSQL("DROP TABLE saveGame;");
        onCreate(db);
    }

    public void updateSettings(SQLiteDatabase db,
                               int effects,
                               int soundtrack,
                               int difficulty) {
        db.execSQL("UPDATE settings " +
                "SET effects = " + effects +
                " soundtrack = " + soundtrack +
                " difficulty = " + difficulty +
                " WHERE id = 1;");
    }

    private int getSettingsField(SQLiteDatabase db,
                                String field) {
        int ergebnis = 1;
        Cursor effects = db.query(
                false,
                "settings",
                new String[] {
                        field
                },
                "id = ?",
                new String[] {
                        "1"
                },
                null, null, null, null
        );
        while( effects.moveToNext()) {
            ergebnis = effects.getInt(0);
        }
        return ergebnis;
    }

    public int getEffects(SQLiteDatabase db) {
        return getSettingsField(db, "effects");
    }

    public int getSoundtrack(SQLiteDatabase db) {
        return getSettingsField(db, "soundtrack");
    }

    public int getDifficulty(SQLiteDatabase db) {
        return getSettingsField(db, "difficulty");
    }

    public void saveGame(SQLiteDatabase db,
                        int playerPoints,
                        int enemyPoints,
                        int time){
        db.execSQL("INSERT INTO saveGame (playerPoints, enemyPoints, playTime) " +
                "VALUES (" + playerPoints + ", " + enemyPoints + ", " + time + ");");
    }

    private int getStatsField(SQLiteDatabase db,
                                 String field) {
        int ergebnis = 1;
        Cursor effects = db.query(
                false,
                "saveGame",
                new String[] {
                        "SUM(" + field +")"
                },
                "id = ?",
                new String[] {
                        "1"
                },
                null, null, null, null
        );
        while( effects.moveToNext()) {
            ergebnis = effects.getInt(0);
        }
        return ergebnis;
    }


    public int getPlayerPoints(SQLiteDatabase db) {
        return getSettingsField(db, "playerPoints");
    }

    public int getEnemyPoints(SQLiteDatabase db) {
        return getSettingsField(db, "enemyPoints");
    }

    public int getTime(SQLiteDatabase db) {
        return getSettingsField(db, "playTime");
    }

    private HighScorePoints getHighscoreField(SQLiteDatabase db,
                                 String field) {
        int ergebnis = 1;
        Cursor highScore = db.query(
                false,
                "saveGame",
                new String[] {
                    "name",
                    "playerPoints"
                },
                null,
                null,
                null, null,
                "playerPoints DESC", //ORDER BY
                "3" //Limit
        );
        HighScorePoints points = new HighScorePoints();
        while( highScore.moveToNext()) {
            points.setEntry(highScore.getInt(0), highScore.getString(0));
        }
        return points;
    }



}

