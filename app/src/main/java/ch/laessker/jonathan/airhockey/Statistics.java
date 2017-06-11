package ch.laessker.jonathan.airhockey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import ch.laessker.jonathan.airhockey.util.DBHelper;
import ch.laessker.jonathan.airhockey.util.StatisticsValues;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        DBHelper helper = new DBHelper(getApplicationContext());
        StatisticsValues stats = helper.getStatistics();

        TextView player1Text = (TextView) findViewById(R.id.player_points);
        TextView player2Text = (TextView) findViewById(R.id.enemy_points);
        TextView durationText = (TextView) findViewById(R.id.play_time);

        player1Text.setText(Integer.toString(stats.getPlayer1Points()));
        player2Text.setText(Integer.toString(stats.getPlayer2Points()));
        durationText.setText(Integer.toString(stats.getDuration()));

        Log.d("statisticsoncreate", "");

    }

    @Override
    protected void onResume(){
        super.onResume();

        DBHelper helper = new DBHelper(getApplicationContext());
        StatisticsValues stats = helper.getStatistics();


        TextView player1Text = (TextView) findViewById(R.id.player_points);
        TextView player2Text = (TextView) findViewById(R.id.enemy_points);
        TextView durationText = (TextView) findViewById(R.id.play_time);

        player1Text.setText(Integer.toString(stats.getPlayer1Points()));
        player2Text.setText(Integer.toString(stats.getPlayer2Points()));
        durationText.setText(Integer.toString(stats.getDuration()));

        Log.d("statisticsoncreate", "");

    }

}
