package ch.laessker.jonathan.airhockey;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingsMenu extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);


        final AirHockeyDatenbank AHDB = AirHockeyDatenbank.getInstance(getApplicationContext());
        final SQLiteDatabase db = AHDB.getWritableDatabase();

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
        Toast.makeText(SettingsMenu.this, "Settings Loaded: effects: " + effects + ", soundtrack: " + soundtrack + ", difficulty: " + difficulty, Toast.LENGTH_LONG).show();

        Button saveSettingsButton = (Button) findViewById(R.id.button);

        final Switch effectSwitch = (Switch) findViewById(R.id.switch_effects);
        final Switch soundtrackSwitch = (Switch) findViewById(R.id.switch_soundtrack);
        final Spinner difficultySpinner = (Spinner) findViewById(R.id.spinner_difficulty);

        if(effects == 1) {
            effectSwitch.setChecked(true);
        }
        if(soundtrack == 1) {
            soundtrackSwitch.setChecked(true);
        }
        difficultySpinner.setSelection(difficulty);

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int effects = (effectSwitch.isChecked() ? 1 : 0);
                int soundtrack = (soundtrackSwitch.isChecked() ? 1 : 0);
                int difficulty = difficultySpinner.getSelectedItemPosition();

                ContentValues values = new ContentValues();
                values.put("effects", effects);
                values.put("soundtrack", soundtrack);
                values.put("difficulty", difficulty);

                String selection = "id" + " = ?";
                String[] selectionArgs = { "1" };

                long newRowId = db.update("settings", values, selection, selectionArgs);

                Toast.makeText(SettingsMenu.this, "Settings Saved: effects: " + effects + ", soundtrack: " + soundtrack + ", difficulty: " + difficulty, Toast.LENGTH_LONG).show();
                final Intent i = new Intent(SettingsMenu.this, HauptMenu.class);
                startActivity(i);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SettingsMenu Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
