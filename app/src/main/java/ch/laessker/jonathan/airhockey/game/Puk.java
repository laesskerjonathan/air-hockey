package ch.laessker.jonathan.airhockey.game;

public class Puk {

    private Speed speed;
    private final int RADIUS = 10;
    //Position
    private Position position;
    private double xa = 1, ya = 1;

    private double xa1 = 1, ya1 = 1;
    private double xa2 = 1, ya2 = 1;

    // conflict angle
    private double degrees = 0;

    public Puk(Speed speed) {
        this.speed = speed;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void update() {
        //allow movement when not on border
        /*if (y > 0 && y < this.game.getPanel().getHeight() - this.height)
            this.y += this.ya;
        else
            //bounce off the top wall
            if (y <= 0)
                this.y += 25;
            else
                //bounce off the bottom wall
                if (y >= this.game.getPanel().getHeight() - this.height)
                    this.y -= 25;*/
    }

    //change speed of racket
    public void changeSpeed(double changeSpeed)
    {
        {
            this.speed.setABSSpeed(changeSpeed);
        }
    }

}
