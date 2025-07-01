package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	// fixed width and height
	public static final int SCALE = 1;
	public static final int WIDTH = 256 * SCALE, HEIGHT = 256 * SCALE;
	final int FPS = 60;
	Thread gameThread;
	PlayManager pm;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null); // disable pre-set layout
		
		// implement KeyListner
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true); // always listening to key input when the frame is focused
		
		pm = new PlayManager();
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		pm.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
	}
}
