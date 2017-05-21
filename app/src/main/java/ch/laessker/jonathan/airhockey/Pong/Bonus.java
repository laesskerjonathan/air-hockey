import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.ImageIcon;

public class Bonus
{
	private int x, y, width = 70, height = 70;
	private int id;
	private int minX, maxX, minY, maxY;

	private ImageIcon image;
	private Image preImage;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image scaledimage;

	private Pong game;
	public String name;
	private Racket OwnerPlayer, Enemyplayer;
	private Ball ball;

	//bonus gets created
	public Bonus(int id, Pong game, Ball ball)
	{
		this.id = id;
		this.game = game;
		this.ball = ball;
		Random rand = new Random();
		this.minX = this.game.getPanel().getWidth() * 1 / 5;
		this.maxX = this.game.getPanel().getWidth() * 4 / 5 - this.width;
		this.minY = 0;
		this.maxY = this.game.getPanel().getHeight() - this.height;
		this.x = rand.nextInt(this.maxX - this.minX) + this.minX;
		this.y = rand.nextInt(this.maxY - this.minY) + this.minY;
	}

	//who receives bonus and who receives malus
	public void setPlayersRole(Racket player)
	{
		if (player.getId() == 1)
		{
			this.OwnerPlayer = player;
			this.Enemyplayer = game.getPanel().getPlayer(2);
		}
		else
		{
			this.OwnerPlayer = player;
			this.Enemyplayer = game.getPanel().getPlayer(1);
		}
	}

	//who does the ball belong to
	public Racket getOwnerPlayer()
	{
		return this.OwnerPlayer;
	}

	//who does it NOT belong to
	public Racket getEnemyPlayer()
	{
		return this.Enemyplayer;
	}

	// bonus with id 1 (+/- ball speed)
	public void increaseBallSpeed()
	{
		this.ball.changeSpeed(0.3);
	}

	// bonus with id 2 (+/- ball size)
	public void increaseBallDimension()
	{
		this.ball.changeDimensions(5);
	}

	// bonus with id 3 (+/- racket speed)
	public void changePlayerSpeed()
	{
		this.OwnerPlayer.changeSpeed(1);
		this.OwnerPlayer.changeSpeedBonusCount(1);
		this.Enemyplayer.changeSpeed(-1);
		this.Enemyplayer.changeSpeedBonusCount(-1);
	}

	// bonus with id 4 (+/- racket size)
	public void changePlayerDimension()
	{
		this.OwnerPlayer.changeDimension(20);
		this.OwnerPlayer.changeDimensionBonusCount(1);
		this.Enemyplayer.changeDimension(-20);
		this.Enemyplayer.changeDimensionBonusCount(-1);
	}

	public int getId()
	{
		return this.id;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	//paint everything
	public void paint(Graphics g)
	{
		//which bonus was collected		
		switch(this.id)
		{
			case 1:
				preImage = toolkit.getImage("src/bonus_BallS.png");
				break;
			case 2:
				preImage = toolkit.getImage("src/bonus_BallD.png");
				break;
			case 3:
				preImage = toolkit.getImage("src/bonus_Players.png");
				break;
			case 4:
				preImage = toolkit.getImage("src/bonus_PlayerD.png");
				break;
		}
		this.scaledimage = preImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		this.image = new ImageIcon(scaledimage);
		
		this.image.paintIcon(game.getPanel(), g, (int) x, (int) y);
	}
}
