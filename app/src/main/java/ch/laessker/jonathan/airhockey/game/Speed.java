package ch.laessker.jonathan.airhockey.game;

public class Speed {
    private double xspeed, yspeed;

    public Speed(double xspeed, double yspeed) {
        this.xspeed = xspeed;
        this.yspeed = yspeed;
    }

    public void setXspeed(double xspeed) {
        this.xspeed = xspeed;
    }

    public void setYspeed(double yspeed) {
        this.yspeed = yspeed;
    }

    public double getXspeed() {
        return xspeed;
    }

    public double getYspeed() {
        return yspeed;
    }

    public double absSpeed() {
        return Math.sqrt((Math.pow(xspeed, 2) + Math.pow(yspeed, 2)));
    }
}
