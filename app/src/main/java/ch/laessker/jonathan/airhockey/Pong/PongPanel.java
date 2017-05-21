import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

//panel containing EVERYTHING
public class PongPanel extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	public Bonus bonus = null;
	private int midx;

	private Image backgroundImage;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private Pong game;
	public Ball ball;
	private Racket player1, player2;
	
	//create a pongpanel
	public PongPanel(Pong game)
	{
		double racketWidth = 30, racketHeight = 100;
		int space = 20;
		
		setOpaque(true);
		setBackground(Color.decode("0x151515"));
		backgroundImage = toolkit.getImage("src/bgn.png");

		this.game = game;
		this.midx = this.game.getWidth() / 2;

		this.player1 = new Racket(1, game, KeyEvent.VK_W, KeyEvent.VK_S, space, "Batman", racketWidth, racketHeight);
		this.player2 = new Racket(2, game, KeyEvent.VK_UP, KeyEvent.VK_DOWN, game.getWidth() - racketWidth - space, "Superman", racketWidth, racketHeight);

		this.ball = new Ball(game);

		Timer timer = new Timer(5, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}

	public Bonus getBonus()
	{
		return this.bonus;
	}

	public void deleteBonus()
	{
		this.bonus = null;
	}

	public Racket getPlayer(int id)
	{
		if (id == 1)
			return this.player1;
		else
			return this.player2;
	}

	public void increaseScore(int playerNo)
	{
		if (playerNo == 2)
			this.player1.increasePlayerScore();
		else
			if (playerNo == 1)
				this.player2.increasePlayerScore();
	}

	public int getScore(int playerNo)
	{
		if (playerNo == 1)
			return player1.getPlayerScore();
		else
			return player2.getPlayerScore();
	}

	private void update()
	{
		this.ball.update();
		this.player1.update();
		this.player2.update();
	}

	public void actionPerformed(ActionEvent e)
	{
		update();
		repaint();
	}

	public void keyPressed(KeyEvent e)
	{
		this.player1.pressed(e.getKeyCode());
		this.player2.pressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e)
	{
		this.player1.released(e.getKeyCode());
		this.player2.released(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//background image
		g.drawImage(this.backgroundImage, 0, 0, null);
		
		//middle line settings (position + color)
		int[] midLineXpoints = { this.midx, this.midx };
		int[] midLineYpoints = { 0, this.game.getHeight() };
		g.setColor(Color.decode("0x696969"));
		g.drawPolyline(midLineXpoints, midLineYpoints, 2);
		
		//top display
		g.setColor(Color.decode("0x66FF21"));
		g.setFont(new Font("Consolas", Font.PLAIN, 17));
		String score = "Dim : " + this.player1.getDimensionBonusCount() + " Speed : "
				+ this.player1.getSpeedBonusCount() + " " + this.player1.name + " points : "
				+ this.player1.getPlayerScore() + "  " + " " + this.player2.name + " points : "
				+ this.player2.getPlayerScore() + " Dim : " + this.player2.getDimensionBonusCount() + " Speed : "
				+ this.player2.getSpeedBonusCount();
		g.drawString(score, this.game.getWidth() / 2 - g.getFontMetrics().stringWidth(score) / 2 + 8, 20);

		this.ball.paint(g);
		this.player1.paint(g);

		this.player2.paint(g);
		if (this.bonus != null)
		{
			this.bonus.paint(g);
		}
	}
}