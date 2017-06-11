package ch.laessker.jonathan.airhockey.game;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;

import ch.laessker.jonathan.airhockey.AirHockeyActivity;
import ch.laessker.jonathan.airhockey.GameFinished;
import ch.laessker.jonathan.airhockey.HauptMenu;
import ch.laessker.jonathan.airhockey.SettingsMenu;
import ch.laessker.jonathan.airhockey.SinglePlayerPreGame;
import ch.laessker.jonathan.airhockey.objects.Puck;
import ch.laessker.jonathan.airhockey.util.DBHelper;

public class Game {
    private static Game instance = null;
    private final int MAXPOINTS = 10;
    private int id;
    private int duration;
    private int difficulty;

    private Player player1;
    private Player player2;


    protected Game() {
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void setValues(int id, int difficulty, Player player1, Player player2) {
        this.id = id;
        this.difficulty = difficulty;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getScore(int playerNo)
    {
        if (playerNo == 1)
            return player1.getScore();
        else
            return player2.getScore();
    }
    public void increaseScore(int playerNo, int score)
    {
        if (playerNo == 1)
            this.player1.incrementScore(score);
        else
        if (playerNo == 2)
            this.player2.incrementScore(score);

    }


    //restart game
    public void restart()
    {
      //  panel.ball.resetBall();

    }
    public void checkWin(Activity activity) {
        if (player1.getScore() >= this.MAXPOINTS || player2.getScore() >= this.MAXPOINTS)
        {

            DBHelper helper = new DBHelper(activity.getApplicationContext());
            helper.addFinishedGame();
            final Intent i = new Intent(activity.getApplicationContext(),GameFinished.class);
            activity.startActivityForResult(i, 0);

        }
        else
        {
            // TODO
            // continue playing
            // reset puk position in the field where the goal was scored
        }

    }

    public boolean player1Won(){
        return player1.getScore() > player2.getScore();
    }



}
