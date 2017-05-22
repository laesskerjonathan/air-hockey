package ch.laessker.jonathan.airhockey.game;

public class Puk {

    private int speed;
    private final int RADIUS=10;
    //Position
    private double x, y, xa = 1, ya = 1;

    private double xa1 = 1, ya1 = 1;
    private double xa2 = 1, ya2 = 1;

    // conflict angle
    private double degrees = 0;

    private double acceleration = xa + ya;
    private double acceleration1 = xa1 + ya1;
    private double acceleration2 = xa2 + ya2;


    public Puk(int speed)
    {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
