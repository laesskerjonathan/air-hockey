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


    public void update() {

       /* this.x += this.xa;
        this.y += this.ya;

        //collision with left game border
        if (this.x < 0)
        {
            this.game.getPanel().increaseScore(1);
            this.player = this.game.getPanel().getPlayer(2);
            this.x = this.game.getWidth() / 2;
            this.xa = -this.xa;
            this.increaseHits();
            checkWin();
        }
        //collision with right game border
        else
        if (this.x > game.getWidth() - this.width)
        {
            this.game.getPanel().increaseScore(2);
            this.player = this.game.getPanel().getPlayer(1);
            this.x = this.game.getPanel().getWidth() / 2;
            this.xa = -this.xa;
            this.increaseHits();
            checkWin();
        }

        //collision with top game border
        if (this.y < 0)
        {
            this.ya = Math.sqrt(Math.pow(this.ya, 2));
        }
        else
            //collision with bottom game border
            if (this.y > game.getPanel().getHeight() - this.height)
            {
                this.ya = -Math.sqrt(Math.pow(this.ya, 2));
            }

        //collision with bonus
        if (game.getPanel().getBonus() != null && this.player != null)
        {
            if (game.getPanel().getBonus().getBounds().intersects(this.getBounds()))
            {
                this.game.getPanel().getBonus().setPlayersRole(this.player);
                switch(this.game.getPanel().getBonus().getId())
                {
                    case 1:
                        this.game.getPanel().getBonus().increaseBallSpeed();
                        break;
                    case 2:
                        this.game.getPanel().getBonus().increaseBallDimension();
                        break;
                    case 3:
                        this.game.getPanel().getBonus().changePlayerSpeed();
                        break;
                    case 4:
                        this.game.getPanel().getBonus().changePlayerDimension();
                        break;
                }
                this.isBonusNeeded = true;
                this.resetHits();
                this.game.getPanel().deleteBonus();
            }
        }

        checkCollision();
    }*/
    }
}