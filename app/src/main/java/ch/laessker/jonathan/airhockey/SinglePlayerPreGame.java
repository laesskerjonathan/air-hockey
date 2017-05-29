package ch.laessker.jonathan.airhockey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SinglePlayerPreGame extends AppCompatActivity {

    private Button newGameButton;
    private Button loadGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_pre_game);

        newGameButton = (Button) findViewById(R.id.new_game);
        loadGameButton = (Button) findViewById(R.id.load_game);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(SinglePlayerPreGame.this,AirHockeyActivity.class);
                startActivity(i);
            }
        });
        loadGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //TODO load and start loaded game
            }
        });
    }
}
