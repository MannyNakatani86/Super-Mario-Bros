package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import main.GamePanel;

public class Entity {
	private static final double SCALE = GamePanel.SCALE;
	// Position and size
	private double world_x, y, width, height, screen_x;
	// Physics
	private double velocityY = 0*SCALE;
	private static final double MAX_FALL_SPEED = 6*SCALE, GRAVITY = 1.5*SCALE;
	// Other
	private boolean isOnFeet=true, movingRight=false, 
			movingLeft=true, dead=false, gotHit=false, visible=true;
	
	public Entity(double x, double y, double width, double height) {
		screen_x = x*SCALE;
		world_x = x*SCALE;
		this.y = y*SCALE;
		this.width = width*SCALE;
		this.height = height*SCALE;
	}
	
	public void update(double x, double screen_x) {
		if(isInViewRange(x)) {
			this.screen_x = world_x - x + screen_x;
		}
		
	}
	
	public void updateEnemy(double x, double enemy_speed, double screen_x) {
		if(dead) {
			visible = false;
			y = 1000; // hmmmmmmmmmmmmmmm
			return;
		}else {
			if(isInViewRange(x)) {
				this.screen_x = world_x - x + screen_x;
				if(movingRight) {
					world_x += enemy_speed;
				}else if(movingLeft) {
					world_x -= enemy_speed;
				}
			}
			// Apply gravity if in the air
			if(!isOnFeet) {
				velocityY += GRAVITY;
				if(velocityY >  MAX_FALL_SPEED) {
					velocityY = MAX_FALL_SPEED;
				}
				y += velocityY;
			}
			// check if fallen off screen
			if(y > GamePanel.HEIGHT + 100) {
				dead = true;
			}
		}
	}
	
	public void updateItem(double x, double screen_x) {
		if(isInViewRange(x)) {
			this.screen_x = world_x - x + screen_x;
			// Finish this physics!!!
			// Apply gravity if in the air
			if(!isOnFeet) {
				velocityY += GRAVITY;
				if(velocityY >  MAX_FALL_SPEED) {
					velocityY = MAX_FALL_SPEED;
				}
				y += velocityY;
			}
			// check if fallen off screen
			if(y > GamePanel.HEIGHT + 100) {
				dead = true;
			}
		}
	}	
	
	private boolean isInViewRange(double x) {
		return x + GamePanel.WIDTH > world_x;
	}
	
	public void manageLanding(double y) {
		this.y = y - height;
		isOnFeet = true;
		velocityY = 0;
	}
	
	public void manageRight(double x) {
		world_x = x - width;
		movingRight = false;
		movingLeft = true;
	}
	
	public void manageLeft(double x) {
		world_x = x;
		movingRight = true;
		movingLeft = false;
	}
	
	public void draw(Graphics2D g2, Color c) {
		if(gotHit) {
			g2.setColor(Color.orange);
		}else {
			g2.setColor(c);
		}
		g2.fill(new Rectangle2D.Double(screen_x, y, width, height));
	}
	
	public double getX() {return world_x;}
	public double getY() {return y;}
	public double getWidth() {return width;}
	public double getHeight() {return height;}
	
	public boolean isMovingRight() {return movingRight;}
	public boolean isMovingLeft() {return movingLeft;}
	public boolean isDead() {return dead;}
	public boolean isVisible() {return visible;}
	public boolean isOnFeet() {return isOnFeet;}
	
	public void setIsOnFeet(boolean b) {isOnFeet = b;}
	public void setDead(boolean b) {dead = b;}
	public void setGotHit(boolean b) {gotHit = b;}
}
