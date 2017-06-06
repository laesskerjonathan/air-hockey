package ch.laessker.jonathan.airhockey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import ch.laessker.jonathan.airhockey.data.BackgroundMusicService;

public class HauptMenu extends AppCompatActivity {

    Button singlePlayerButton;
    Button highScoreButton;
    Button statisticButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent = new Intent(this,BackgroundMusicService.class);
        startService(serviceIntent);
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
        //TODO: Load Settings from Database and start the FUN (Music & Effects)
    }

}
