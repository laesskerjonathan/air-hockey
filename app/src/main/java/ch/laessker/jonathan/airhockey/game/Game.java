package ch.laessker.jonathan.airhockey.game;


import android.content.Intent;

import ch.laessker.jonathan.airhockey.HauptMenu;
import ch.laessker.jonathan.airhockey.SinglePlayerPreGame;
import ch.laessker.jonathan.airhockey.objects.Puck;

public class Game {
    private final int MAXPOINTS = 10;
    private int id;
    private int duration;
    private int difficulty;
    private Player player1;
    private Player player2;


    public Game(int id, int difficulty, Player player1, Player player2) {
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
    public void checkWin() {
        if (player1.getScore() > this.MAXPOINTS)
        {
            // player 1 win


        }
        else if(player2.getScore() > this.MAXPOINTS)
        {
            //player 2 wins
        }
        else
        {
            // continue playing
            // reset puk position in the field where the goal was scored
        }

    }



}
