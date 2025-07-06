package objects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pole extends Entity{

	public Pole(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public void update(double x, double screen_x) {super.update(x, screen_x);}
	
	public void draw(Graphics2D g2) {super.draw(g2, Color.white);}
}
