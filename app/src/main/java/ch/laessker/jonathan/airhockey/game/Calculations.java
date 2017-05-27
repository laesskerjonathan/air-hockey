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

    /*//return collision bounds
    public Rectangle getBounds()
    {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    //repaint everything
    public void paint(Graphics g)
    {
        //ball rotation animation
        if (this.degrees >= 360)
        {
            this.degrees = 0;
        }
        this.degrees += this.rotation * 1.5;
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform old = g2d.getTransform();
        if (this.player == null)
        {
            this.preImage = this.toolkit.getImage("src/grey.png");
            g2d.rotate(Math.toRadians(this.degrees), this.x + this.width / 2, this.y + this.height / 2);
            this.scaledimage = this.preImage.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
        }
        //..ball rotation animation goes on
        else
        {
            if (this.player == this.game.getPanel().getPlayer(1))
            {
                this.preImage = this.toolkit.getImage("src/red.png");
                g2d.rotate(Math.toRadians(this.degrees), this.x + this.width1 / 2, this.y + this.height1 / 2);
                this.scaledimage = this.preImage.getScaledInstance(this.width1, this.height1, Image.SCALE_DEFAULT);
            }
            else
            {
                this.preImage = this.toolkit.getImage("src/blue.png");
                g2d.rotate(Math.toRadians(this.degrees), this.x + this.width2 / 2, this.y + this.height2 / 2);
                this.scaledimage = this.preImage.getScaledInstance(this.width2, this.height2, Image.SCALE_DEFAULT);
            }
        }

        this.image = new ImageIcon(scaledimage);

        this.image.paintIcon(game.getPanel(), g, (int) x, (int) y);
        g2d.setTransform(old);
    }*/

}
