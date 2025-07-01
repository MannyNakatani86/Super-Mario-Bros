package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Goomba extends Entity{
	
	private static final double WALKING_SPEED = 0.65 * GamePanel.SCALE;

	public Goomba(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public void update(double x, double screen_x) {super.updateEnemy(x, WALKING_SPEED, screen_x);}
	
	public void draw(Graphics2D g2) {super.draw(g2, new Color(168, 74, 0));}
}
