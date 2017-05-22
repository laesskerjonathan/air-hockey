package ch.laessker.jonathan.airhockey.game;

public class Player {

    private int score;
    private int id;
    private String name;
    private Mallet mallet;
    private Position position;
    public Player(int id, String name,int initialScore,Mallet mallet)
    {
        setId(id);
        setName(name);
        setScore(initialScore);
        setMallet(mallet);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMallet(Mallet mallet) {
        this.mallet = mallet;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    public int getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public int getScore()
    {
        return this.score;
    }

    public Mallet getMallet() {
        return mallet;
    }

    public void incrementScore(int amount)
    {
        this.score += amount;
    }

}
