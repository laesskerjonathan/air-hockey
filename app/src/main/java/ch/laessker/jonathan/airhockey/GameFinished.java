package ch.laessker.jonathan.airhockey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.laessker.jonathan.airhockey.game.Game;

public class GameFinished extends AppCompatActivity {
    Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);

        mainMenuButton = (Button) findViewById(R.id.mainMenu);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        Game game = Game.getInstance();

        String playerWon = game.player1Won() ? "Player 1" : "Player 2";
        TextView playerText = (TextView) findViewById(R.id.playerWon);
        playerText.setText(playerWon);

        String score = Integer.toString(game.getScore(1)) + " : " + Integer.toString(game.getScore(2));
        TextView scoreText = (TextView) findViewById(R.id.score);
        scoreText.setText(score);


    }
}
