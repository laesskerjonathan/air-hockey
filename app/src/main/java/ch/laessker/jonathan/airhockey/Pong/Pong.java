import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//create the game
public class Pong extends JFrame
{
	private static final long serialVersionUID = 1L;

	private final static int WIDTH = 1000, HEIGHT = 600;

	private PongPanel panel;

	public Pong()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Best Pong ever - because we can!");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new PongPanel(this);
		
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		//menu bar element
		JMenu fileMenu = new JMenu("Game");
		menuBar.add(fileMenu);
		//menu bar element item 1 (new game)
		JMenuItem newMenuItemNewGame = new JMenuItem("New Game", KeyEvent.VK_N);

		//menu action listener
		newMenuItemNewGame.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				menuItemNewGameActionPerformed(evt);
			}
		});
		fileMenu.add(newMenuItemNewGame);

		//menu bar element item 2 (new game)
		JMenuItem newMenuItemExit = new JMenuItem("Close", KeyEvent.VK_C);
		newMenuItemExit.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				menuFileExitActionPerformed(evt);
			}
		});
		fileMenu.add(newMenuItemExit);

		setJMenuBar(menuBar);

		//add panel to the game
		add(panel);
		setResizable(false);
		setVisible(true);
	}

	//restart game
	public void restart()
	{
		panel.ball.resetBall();
		panel.getPlayer(2).resetPlayer();
		panel.getPlayer(1).resetPlayer();
	}

	private static void menuFileExitActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.exit(0);
	}

	private void menuItemNewGameActionPerformed(java.awt.event.ActionEvent evt)
	{
		restart();
	}

	public PongPanel getPanel()
	{
		return panel;
	}

	//start teh game
	public static void main(String[] args)
	{
		new Pong();
	}
}
