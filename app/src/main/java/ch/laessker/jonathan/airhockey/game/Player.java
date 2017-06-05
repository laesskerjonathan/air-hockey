package ch.laessker.jonathan.airhockey.game;

import ch.laessker.jonathan.airhockey.objects.Mallet;
public class Player {

    private int score;
    private int id;
    private String name;

    public Player(int id, String name, int initialScore) {
        setId(id);
        setName(name);
        setScore(initialScore);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int amount) {
        this.score += amount;
    }

}
