package ch.laessker.jonathan.airhockey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);


        Button saveSettingsButton = (Button) findViewById(R.id.single_player_pre_game);

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            //TODO: Load Settings from Database
            public void onClick(View v) {
                //TODO: Save Settings in Database
                final Intent i = new Intent(SettingsMenu.this,HauptMenu.class);
                startActivity(i);
            }
        });

    }
}
