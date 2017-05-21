import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Ball
{
	private int width = 50, height = 50;
	private int width1 = width, width2 = width, height1 = height, height2 = height;

	private double x, y, xa = 1, ya = 1;

	private double xa1 = 1, ya1 = 1;
	private double xa2 = 1, ya2 = 1;
	private double rotation = 1;
	public int hitsSeries = 0;
	private double degrees = 0;
	public boolean isBonusNeeded = true;

	private double acceleration = xa + ya;
	private double acceleration1 = xa1 + ya1;
	private double acceleration2 = xa2 + ya2;

	private ImageIcon image;
	private Image preImage;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image scaledimage;

	public Pong game;
	private Racket player = null;
	Bonus bonus = null;
	Random rand = new Random();
	PongPanel pongpanel;

	public int nextRandomBonus = rand.nextInt(1) + 1;
	
	
	
	private int roundtoWin = 5;

	public Ball(Pong game)
	{
		this.game = game;

		x = game.getWidth() / 2 - width / 2;
		y = game.getHeight() / 2 - height / 2;

		preImage = toolkit.getImage("src/grey.png");
		this.scaledimage = preImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		this.image = new ImageIcon(scaledimage);
	}

	private void checkWin()
	{
		if (game.getPanel().getScore(1) == this.roundtoWin)
		{
			JOptionPane.showMessageDialog(null, game.getPanel().getPlayer(1).getName() + " wins", "Pong",
					JOptionPane.PLAIN_MESSAGE);
			game.getPanel().getPlayer(2).resetPlayer();
			game.getPanel().getPlayer(1).resetPlayer();
			this.resetBall();
		}
		else
			if (game.getPanel().getScore(2) == this.roundtoWin)
			{
				JOptionPane.showMessageDialog(null, game.getPanel().getPlayer(2).getName() + " wins", "Pong",
						JOptionPane.PLAIN_MESSAGE);
				game.getPanel().getPlayer(2).resetPlayer();
				game.getPanel().getPlayer(1).resetPlayer();
				this.resetBall();
			}
	}
	
	public void update()
	{

		this.x += this.xa;
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
	}

	public void checkCollision()
	{
		//bonus needs to appear
		if (this.isBonusNeeded == true)
		{
			this.nextRandomBonus = rand.nextInt(1) + 1;
			this.isBonusNeeded = false;
		}
		//bonus really needs to appear (add bonus to the game)
		if (this.getHits() == this.nextRandomBonus && this.game.getPanel().getBonus() == null)
		{
			this.game.getPanel().bonus = new Bonus(rand.nextInt(4) + 1, this.game, this);
		}
		//collision with batman (?)
		if (this.game.getPanel().getPlayer(1).getBounds().intersects(this.getBounds()))
		{
			this.player = game.getPanel().getPlayer(1);
			this.increaseHits();

			this.calculateAngle(1);
		}
		else
			//collision with superman (?)
			if (this.game.getPanel().getPlayer(2).getBounds().intersects(this.getBounds()))
			{
				this.player = this.game.getPanel().getPlayer(2);
				this.increaseHits();

				this.calculateAngle(2);
			}
	}

	//set speed on X-axis
	public void setSpeedX(double aSpeedX)
	{
		this.xa = aSpeedX;
	}
	//set speed on Y-axis
	public void setSpeedY(double aSpeedY)
	{
		this.ya = aSpeedY;
	}

	//change dimensions :P
	public void changeDimensions(int aDimension)
	{
		//..depending on player ball belongs to (here batman (?))
		if (this.player.getId() == 1)
		{
			this.width1 -= aDimension;
			this.height1 -= aDimension;
			this.width2 += aDimension;
			this.height2 += aDimension;
		}
		//..depending on player ball belongs to (here superman (?))
		else
			if (this.player.getId() == 2)
			{
				this.width1 += aDimension;
				this.height1 += aDimension;
				this.width2 -= aDimension;
				this.height2 -= aDimension;
			}
	}

	//reset ball (new game/game finished + restarted)
	public void resetBall()
	{
		this.player = null;
		this.setSpeedX(1);
		this.setSpeedY(1);
		this.acceleration = this.xa + this.ya;
		this.acceleration1 = this.acceleration;
		this.acceleration2 = this.acceleration;
		this.width = 50;
		this.height = 50;
		this.width1 = 50;
		this.width2 = 50;
		this.height1 = 50;
		this.height2 = 50;
		this.x = this.game.getWidth() / 2 - this.width / 2;
		this.y = this.game.getHeight() / 2 - this.height / 2;
	}

	//change speed
	public void changeSpeed(double percentIncrease)
	{
		//..depending on the player (here again)
		if (this.player.getId() == 1)
		{
			this.acceleration1 += this.acceleration1 * percentIncrease;
		}
		//..depending on the player (here again)
		else
			if (this.player.getId() == 2)
			{
				this.acceleration2 += this.acceleration2 * percentIncrease;
			}
	}

	//reset hit series after bonus was picked up
	public void resetHits()
	{
		this.hitsSeries = 0;
	}

	//increase hit series (bonus wasn't picked up)
	public void increaseHits()
	{
		this.hitsSeries++;
	}

	public int getHits()
	{
		return this.hitsSeries;
	}

	//calculate reflection angle after impact on racket
	public void calculateAngle(int id)
	{
		int r0 = (int) this.player.getBounds().getMinY();
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
		this.rotation = this.ya;
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

	//return collision bounds
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
	}
}