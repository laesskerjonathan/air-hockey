package ch.laessker.jonathan.airhockey;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AirHockeyDatenbank extends SQLiteOpenHelper {

    private static final String DATENBANK_NAME = "settings.db";
    private static final int DATENBANK_VERSION = 1;
    private final SQLiteDatabase db = this.getWritableDatabase();

    private AirHockeyDatenbank(Context context){
        super(
                context,
                DATENBANK_NAME,
                null,
                DATENBANK_VERSION);
    }

    private static AirHockeyDatenbank sINSTANCE;
    private static Object sLOCK = "";

    public static AirHockeyDatenbank getInstance(Context context) {
        if( sINSTANCE == null) {
            synchronized(sLOCK) {
                if (sINSTANCE == null && context != null ) {
                    sINSTANCE = new AirHockeyDatenbank(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
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
        //Game Tabelle erstellen
        db.execSQL("CREATE TABLE game(" +
                "id INTEGER PRIMARY KEY, " +
                "difficulty INTEGER," +
                "duration INTEGER," +
                "pointsPlayer1 INTEGER," +
                "pointsPlayer2 INTEGER," +
                "namePlayer1 STRING," +
                "namePlayer2 STRING);");
        //Default Settings eintragen
        db.execSQL("INSERT INTO game " +
                "VALUES (1, 1, 0, 0, 0, 'unnamed', 'unnamed');");
        //saveGame Tabelle erstellen
        db.execSQL("CREATE TABLE saveGame(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT " +
                "playerPoints INTEGER, " +
                "enemyPoints INTEGER, " +
                "playTime INTEGER); ");
        //statistic Tabelle erstellen
        db.execSQL("CREATE TABLE highscore(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT " +
                "playerPoints INTEGER); ");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE settings;");
        db.execSQL("DROP TABLE saveGame;");
        db.execSQL("DROP TABLE game;");
        db.execSQL("DROP TABLE highscore;");
        onCreate(db);
    }



}

