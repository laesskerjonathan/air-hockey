package ch.laessker.jonathan.airhockey.game;

public class Calculations {

    public void calculateAngle(int id)
    {
      /*  int r0 = (int) this.player.getBounds().getMinY();
        double tmpacc = 0;

        if (this.player.getId() == 1)
        {
            tmpacc = this.acceleration1;
        }
        else
        if (this.player.getId() == 2)
        {
            tmpacc = this.acceleration2;
        }

        this.ya = (tmpacc * -Math.cos(Math.PI * (this.getBounds().getCenterY() - r0) / this.player.getBounds().getHeight())
                * 0.75);

        //we distinguish player 1 from player 2 (player 1 here)
        if (this.player.getId() == 1)
        {
            this.xa = distance(tmpacc, this.ya);
        }
        //..here it's player 2
        else
        if(this.player.getId() == 2)
        {
            this.xa = -distance(tmpacc, this.ya);
        }
        this.rotation = this.ya;*/
    }


    //returns distance between 2 values (always positive)
    public double distance(double a, double b)
    {
        if (Math.abs(a) > Math.abs(b))
        {
            return Math.sqrt(Math.pow(a, 2) - Math.pow(b, 2));
        }
        if (Math.abs(a) < Math.abs(b))
        {
            return Math.sqrt(Math.pow(b, 2) - Math.pow(a, 2));
        }
        return 0;
    }
}
