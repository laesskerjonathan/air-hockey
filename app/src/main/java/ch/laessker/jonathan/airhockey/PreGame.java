package ch.laessker.jonathan.airhockey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreGame extends AppCompatActivity {

    private Button newGameButton;
    private Button loadGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        newGameButton = (Button) findViewById(R.id.new_game);
        loadGameButton = (Button) findViewById(R.id.load_game);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(PreGame.this,AirHockeyActivity.class);
                i.putExtra("startNew", true);
                startActivityForResult(i, 0);

            }
        });
        loadGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent i = new Intent(PreGame.this,AirHockeyActivity.class);
                i.putExtra("startNew", false);

                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }
}
