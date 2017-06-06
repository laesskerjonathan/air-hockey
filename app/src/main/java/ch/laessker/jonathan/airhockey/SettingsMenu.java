package ch.laessker.jonathan.airhockey;

import android.content.ContentValues;
import android.content.Context;
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

import ch.laessker.jonathan.airhockey.util.DBHelper;
import ch.laessker.jonathan.airhockey.util.SettingsValues;

public class SettingsMenu extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        Context applicationContext = getApplicationContext();

        helper = new DBHelper(applicationContext);

        SettingsValues settingsValues = helper.returnSavedValues();

        int effects = settingsValues.getEffects();
        int soundtrack = settingsValues.getSoundtrack();
        int difficulty = settingsValues.getDifficulty();

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

                helper.setValues(effects, soundtrack, difficulty);

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
