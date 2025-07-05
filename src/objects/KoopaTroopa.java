package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class KoopaTroopa extends Entity{
	
	private static final double WALKING_SPEED = 0.65 * GamePanel.SCALE;
	private int level = 2;

	public KoopaTroopa(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public void update(double x, double screen_x) {super.updateEnemy(x, WALKING_SPEED, screen_x);}
	
	public void draw(Graphics2D g2) {super.draw(g2, Color.green);}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int i) {
		level = i;
	}
}
