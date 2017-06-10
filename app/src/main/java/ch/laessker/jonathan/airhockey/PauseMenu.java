package ch.laessker.jonathan.airhockey;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ch.laessker.jonathan.airhockey.game.Game;
import ch.laessker.jonathan.airhockey.util.DBHelper;

public class PauseMenu extends AppCompatActivity {

    private Button continueGameButton;
    private Button saveGameButton;
    private Button mainMenuButton;
    private Game currentGame;
    private DBHelper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_menu);

        Context applicationContext = getApplicationContext();
        helper = new DBHelper(applicationContext);

        continueGameButton = (Button) findViewById(R.id.continueGame);
        saveGameButton = (Button) findViewById(R.id.saveGame);
        mainMenuButton = (Button) findViewById(R.id.mainMenu);

        currentGame = Game.getInstance();

        continueGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        saveGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                helper.saveGame();
                Toast.makeText(PauseMenu.this, "Game saved!", Toast.LENGTH_LONG).show();

            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

    }
}
