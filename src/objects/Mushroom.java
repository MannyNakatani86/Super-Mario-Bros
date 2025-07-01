package objects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mushroom extends Entity{
	public Mushroom(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public void update(double x, double screen_x) {super.updateItem(x, screen_x);}
	
	public void draw(Graphics2D g2) {super.draw(g2, Color.red);}
}
