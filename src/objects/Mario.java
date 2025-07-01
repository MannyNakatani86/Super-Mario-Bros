package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import main.GamePanel;

public class Mario{
	private static final int SCALE = GamePanel.SCALE;
	// Size and Position
	private static final double WIDTH = 16*SCALE, HEIGHT = 16*SCALE, HEIGHT_BIG = 32*SCALE;
	private double x = 40*SCALE, y = 208*SCALE, screen_x = 40*SCALE;
	// Physics
	private double velocityY = 0*SCALE, velocityX = 0*SCALE;
	private static final double GRAVITY_UP = 1*SCALE, GRAVITY_DOWN = 1.5*SCALE,
			MAX_FALL_SPEED = 6*SCALE, ACCELERATION = 0.078*SCALE, WALK_SPEED = 1*SCALE,
			MAX_VELOCITY_X = 2.5*SCALE;
	
	boolean isOnFeet = true, isLookingRight = true, upCollision = false, jump = false,
			walkRight = false, walkLeft = false, runRight = false, runLeft = false,
			damaging = false;
	// Other
	private int level = 1, damagingTimer;
	
	public Mario() {}
	
	public void walkRightRequest() {
		walkRight = true;
		walkLeft = false;
		runRight = false;
		runLeft = false;
		isLookingRight = true;
	}	
	public void walkLeftRequest() {
		walkRight = false;
		walkLeft = true;
		runRight = false;
		runLeft = false;
		isLookingRight = false;
	}	
	public void runRightRequest() {
		walkRight = false;
		walkLeft = false;
		runRight = true;
		runLeft = false;
		isLookingRight = true;
	}	
	public void runLeftRequest() {
		walkRight = false;
		walkLeft = false;
		runRight = false;
		runLeft = true;
		isLookingRight = false;
	}
	
	public void jumpRequest() {
		jump = true;
	}
	
	public void stopRequest() {
		walkRight = false;
		walkLeft = false;
		runRight = false;
		runLeft = false;
		velocityX = 0;
	}
	
	private void horizontalMove() {
		if(walkRight) {
			velocityX = WALK_SPEED;
		}else if(walkLeft) {
			velocityX = -WALK_SPEED;
		}else if(runRight) {
			// initial velocity
			if(velocityX <= 0) {
				velocityX = 1.5 * SCALE;
			}
			// Applying momentum
			velocityX += ACCELERATION;
			if(velocityX >  MAX_VELOCITY_X) {
				velocityX = MAX_VELOCITY_X;
			}
		}else if(runLeft) {
			// initial velocity
			if(velocityX >= 0) {
				velocityX = -1.5 * SCALE;
			}
			velocityX -= ACCELERATION;
			if(velocityX <  -MAX_VELOCITY_X) {
				velocityX = -MAX_VELOCITY_X;
			}
		}
		// maybe include deceleration
		x += velocityX;
	}

	private void manageJump() {
		if(jump && isOnFeet) {
			isOnFeet = false;
			velocityY = -12 * SCALE;
		}
		jump = false;
	}
	
	private void applyGravity() {
		if(!isOnFeet) {
			if(velocityY <= 0) {
				velocityY += GRAVITY_UP;
			}else {
				velocityY += GRAVITY_DOWN;
				if(velocityY >  MAX_FALL_SPEED) {
					velocityY = MAX_FALL_SPEED;
				}
			}
		}
	}
	
	public void manageLanding(double y) {
		this.y = y - getHeight();
		isOnFeet = true;
		velocityY = 0;
	}
	
	public void manageLandingOnEnemy(double y) {
		this.y = y - getHeight();
		velocityY = -6 * SCALE;
	}
	
	public void manageRight(double x) {
		if(!upCollision) {
			this.x = x - getWidth() ;
			stopRequest();
		}
	}
	public void manageLeft(double x) {
		if(!upCollision) {
			this.x = x;
			stopRequest();
		}
	}
		
	public void manageUp(double y) {
		this.y = y;
		if(velocityY < 0) {
			velocityY = 0;
		}
		upCollision = true;
	}

	public void powerUp() {
		if(level == 1) {
			level = 2; // tall mario
		}else if(level == 2) {
			level = 3; // fire mario
		}
	}
	
	public void takeDamage() {
		if(damaging) return;
		if(level > 1) {
			level--;
			damaging = true;
			// damaging timer
			damagingTimer = 120;
		}else {
			dead();
		}
	}
	
	public void dead() {}
	
	public void update() {
		if(damaging) {
			damagingTimer--;
			if(damagingTimer <= 0) {
				damaging = false;
			}
		}
		// handling  the basic movement request
		horizontalMove();
		manageJump();
		applyGravity();
		y += velocityY;
		// Boundary check
		if(x < 0) {
			x = 0;
			velocityX = 0;
		}
		if(y > GamePanel.HEIGHT + 100) {
			dead();
		}
	}
	
	// should use the below getImage()
	public void draw(Graphics2D g2) {
		// do something when damaged
		if(level == 1 || level == 2) {
			g2.setColor(Color.red);
		}else if(level == 3){
			g2.setColor(Color.white);
		}
		g2.fill(new Rectangle2D.Double(screen_x, y, WIDTH, HEIGHT));
	}
	
	public double getX() {return x;}
	public double getScreenX() {return screen_x;}
	public double getY() {return y;}
	public double getWidth() {return WIDTH;}
	public double getHeight() {return level == 1 ? HEIGHT : HEIGHT_BIG;}
	public double getVelocityY() {return velocityY;}
	public double getVelocityX() {return velocityX;}
	
	public boolean isWalkingRight() {return walkRight;}
	public boolean isWalkingLeft() {return walkLeft;}
	public boolean isRunningRight() {return runRight;}
	public boolean isRunningLeft() {return runLeft;}
	public boolean isOnFeet() {return isOnFeet;}

	public void setIsOnFeet(boolean b) {isOnFeet = b;}
	public void setUpCollision(boolean b) {upCollision = false;}
}
