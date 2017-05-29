package ch.laessker.jonathan.airhockey.game;

public class Puk {



    public Vector2d velocity;
    public Vector2d position;

    public final int RADIUS = 10;
    private float angularVel;
    private float orientation = 45f;
    // conflict angle
    private double degrees = 0;

    public Puk(float x, float y, Vector2d velocity)
    {
        this.velocity = new Vector2d(1, 1);
        this.position = new Vector2d(x, y);
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d speed) {
        this.velocity = velocity;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int compareTo(Puk puk) {
        if (this.position.getX() - RADIUS > puk.position.getX() - RADIUS)
        {
            return 1;
        }
        else if (this.position.getX() - RADIUS < puk.position.getX() - RADIUS)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
     public void update()
     {

     }
}
