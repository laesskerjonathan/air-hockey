package ch.laessker.jonathan.airhockey.game;

public class Mallet {
    private int id;
    // color shall be an Hex color
    private String color;

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

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}
