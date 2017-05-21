import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Racket
{
	private double width = 30, height = 100;
	private int up, down;
	private double x, y;
	private double ya;
	private double speed = 2;
	private int id;
	private int score = 0;
	private int speedBonusCount = 0;
	private int dimensionBonusCount = 0;
	public String name;

	private Pong game;
	private PongPanel pongpanel;
	private ImageIcon image;
	private Image preImage;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image scaledimage;

	//racket gets created
	public Racket(int id, Pong game, int up, int down, double x, String name, double width, double height)
	{
		this.width = width;
		this.height = height;
		this.id = id;
		this.game = game;
		this.x = x;
		this.y = this.game.getHeight() / 2 - this.height / 2;
		this.up = up;
		this.down = down;
		this.name = name;

		if (id == 1)
		{
			this.preImage = this.toolkit.getImage("src/batman.png");
		}
		else
			if (id == 2)
			{
				this.preImage = this.toolkit.getImage("src/superman.png");
			}
		this.scaledimage = this.preImage.getScaledInstance((int) this.width, (int) this.height, Image.SCALE_DEFAULT);
		this.image = new ImageIcon(scaledimage);
	}

	
	public void update()
	{
		//allow movement when not on border
		if (y > 0 && y < this.game.getPanel().getHeight() - this.height)
			this.y += this.ya;
		else
			//bounce off the top wall
			if (y <= 0)
				this.y += 25;
			else
				//bounce off the bottom wall
				if (y >= this.game.getPanel().getHeight() - this.height)
					this.y -= 25;
	}

	//movement key pressed
	public void pressed(int keyCode)
	{
		if (keyCode == this.up)
			this.ya = -this.speed;
		else
			if (keyCode == this.down)
				this.ya = this.speed;
	}

	//..and released
	public void released(int keyCode)
	{
		if (keyCode == this.up || keyCode == this.down)
			this.ya = 0;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int) this.x, (int) this.y, (int) this.width, (int) this.height);
	}

	public String getName()
	{
		return this.name;
	}

	public void increasePlayerScore()
	{
		this.score++;
	}

	public int getPlayerScore()
	{
		return this.score;
	}

	public void setScore(int newScore)
	{

		this.score = newScore;
	}

	//reset player for new game or when game has finished
	public void resetPlayer()
	{
		this.width = 30;
		this.height = 100;
		this.speed = 2;
		this.dimensionBonusCount = 0;
		this.speedBonusCount = 0;
		this.score = 0;
		this.y = this.game.getHeight() / 2 - this.height / 2;

	}

	//change speed of racket
	public void changeSpeed(int changeSpeed)
	{
		if (this.speed + changeSpeed <= 1)
		{
			this.speed *= 0.75;
		}
		else
		{
			this.speed += changeSpeed;
		}
	}

	//change dimension
	public void changeDimension(int changeDimension)
	{
		if (this.height + changeDimension <= 20)
		{
			this.height *= 0.75;
		}
		else
			if (this.height + changeDimension >= 200)
			{
				//we get the OTHER player and decrese his score by 1 (we're too powerful here)
				this.game.getPanel().getPlayer(((this.id+2)%2)+1).score--;
			}
			else
			{
				this.height += changeDimension;
			}
	}

	//change bonus count (top bonus display)
	public void changeSpeedBonusCount(int changingCount)
	{
		this.speedBonusCount += changingCount;
	}
	//..same
	public void changeDimensionBonusCount(int changingCount)
	{
		this.dimensionBonusCount += changingCount;
	}
	//...and same
	public int getSpeedBonusCount()
	{
		return this.speedBonusCount;
	}
	//.....and same again
	public int getDimensionBonusCount()
	{
		return this.dimensionBonusCount;
	}

	//paint everything
	public void paint(Graphics g)
	{
		if (id == 1)
		{
			preImage = toolkit.getImage("src/batman.png");
		}
		else
		{
			preImage = toolkit.getImage("src/superman.png");
		}
		Image scaledimage = preImage.getScaledInstance((int) this.width, (int) this.height, Image.SCALE_DEFAULT);
		image = new ImageIcon(scaledimage);
		image.paintIcon(this.pongpanel, g, (int) x, (int) y);
	}
}