package ch.laessker.jonathan.airhockey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import ch.laessker.jonathan.airhockey.data.BackgroundMusicService;
import ch.laessker.jonathan.airhockey.data.soundEffectsService;
import ch.laessker.jonathan.airhockey.util.DBHelper;

public class HauptMenu extends AppCompatActivity {

    Button singlePlayerButton;
    Button highScoreButton;
    Button statisticButton;
    Button settingsButton;

    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haupt_menu);

        singlePlayerButton = (Button) findViewById(R.id.single_player_pre_game);

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(HauptMenu.this,SinglePlayerPreGame.class);
                startActivity(i);
            }
        });

        highScoreButton = (Button) findViewById(R.id.highscoreb);

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(HauptMenu.this,HighScore.class);
                startActivity(i);
            }
        });
        statisticButton = (Button) findViewById(R.id.statisticb);

        statisticButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(HauptMenu.this,Statistics.class);
                startActivity(i);
            }
        });
        settingsButton = (Button) findViewById(R.id.settingsb);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(HauptMenu.this,SettingsMenu.class);
                startActivity(i);
            }
        });

        serviceIntent = new Intent(HauptMenu.this,BackgroundMusicService.class);

        DBHelper helper = new DBHelper(getApplicationContext());
        int musicIsOn = helper.returnSavedValues().getSoundtrack();

        if(musicIsOn == 1){
            startService(serviceIntent);
        } else {
            stopService(serviceIntent);
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }

    @Override
    protected void onResume(){
        super.onResume();

        DBHelper helper = new DBHelper(getApplicationContext());
        int musicIsOn = helper.returnSavedValues().getSoundtrack();

        if(musicIsOn == 1){
            startService(serviceIntent);
        } else {
            stopService(serviceIntent);
        }

    }

}
