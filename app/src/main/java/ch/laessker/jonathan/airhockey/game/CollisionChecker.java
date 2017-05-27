package ch.laessker.jonathan.airhockey.game;

public class CollisionChecker {

    public boolean checkCollision(Mallet mallet, Puk puk){
        double x, y;
        x = puk.getPosition().getX() - mallet.getPosition().getX();
        y = puk.getPosition().getY() - mallet.getPosition().getY();

        double collisionDist = Math.sqrt( x*x + y*y);

        if (collisionDist < puk.RADIUS)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public void resolveCollision(Mallet mallet, Puk puk)
    {
        // get the mtd
        Vector2d delta = (mallet.getPosition().subtract(puk.getPosition()));
        float d = delta.getLength();
        // minimum translation distance to push balls apart after intersecting


        // impact speed
        Vector2d v = (mallet.ge.velocity.subtract(ball.velocity));
        float vn = v.dot(mtd.normalize());

        // sphere intersecting but moving away from each other already
        if (vn > 0.0f) return;

        // collision impulse
        float i = (-(1.0f + Constants.restitution) * vn) / (im1 + im2);
        Vector2d impulse = mtd.multiply(i);

        // change in momentum
        this.velocity = this.velocity.add(impulse.multiply(im1));
        ball.velocity = ball.velocity.subtract(impulse.multiply(im2));

    }
}

