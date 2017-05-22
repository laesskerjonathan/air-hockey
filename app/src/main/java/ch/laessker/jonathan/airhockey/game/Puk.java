package ch.laessker.jonathan.airhockey.game;

public class Puk {

    private Speed speed;
    private final int RADIUS=10;
    //Position
    private Position position;
    private double xa = 1, ya = 1;

    private double xa1 = 1, ya1 = 1;
    private double xa2 = 1, ya2 = 1;

    // conflict angle
    private double degrees = 0;

    public Puk(Speed speed)
    {
        this.speed = speed;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void update()
    {

    }

}
