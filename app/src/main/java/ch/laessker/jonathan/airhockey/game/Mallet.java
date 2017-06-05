package ch.laessker.jonathan.airhockey.game;

public class Mallet {
    private int id;
    // color shall be an Hex color
    private String color;

    private Vector2d position;
    public Mallet(int id, String color) {
        setId(id);
        setColor(color);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void resetMallet() {

    }
}