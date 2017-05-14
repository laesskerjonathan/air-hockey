package ch.laessker.jonathan.airhockey.game;


public class Game {
    private int id;
    private int duration;
    private int difficulty;
    private Player player1;
    private Player player2;

    public Game(int id, int difficulty, Player player1, Player player2)
    {
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

}
