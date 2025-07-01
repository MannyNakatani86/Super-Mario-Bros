package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import objects.Background;
import objects.Block;
import objects.Entity;
import objects.Goomba;
import objects.Ground;
import objects.KoopaTroopa;
import objects.Mario;
import objects.Pipe;
import objects.QuestionBlock;

public class PlayManager {
	
	// concepts
	private static boolean gameOver = false;
	private static int score = 0;
	private static final int SCALE = GamePanel.SCALE;
	
	// Objects
	Mario mario;
	Background background;
	ArrayList<Pipe> pipes = new ArrayList<>();
	ArrayList<Block> blocks = new ArrayList<>();
	ArrayList<QuestionBlock> qBlocks = new ArrayList<>();
	ArrayList<Ground> ground = new ArrayList<>();
	ArrayList<Goomba> goombas = new ArrayList<>();
	ArrayList<KoopaTroopa> koopaTroopas = new ArrayList<>();
	
	// size
	private static final double PIPE_W = 32, BLOCK_SIDE = 16, GROUND_H = 32, 
			GOOMBA_W = 16, GOOMBA_H = 16, KOOPA_W = 16, KOOPA_H = 24;
	
	public PlayManager() {
		mario = new Mario();
		background = new Background(0,0,3584, 256);
		
		double[] pipe_x = {448,608,736,912,2608,2864};
		double[] pipe_y = {188,171,153,153,188,188};
		for(int i = 0; i < pipe_x.length; i++) {
			pipes.add(new Pipe(pipe_x[i], pipe_y[i], PIPE_W, 200));
		}
		
		double[] block_x = {320,352,384,1232,1264,1280,1296,1312,1328,1344,1360,
				1376,1392,1456,1472,1488,1504,1600,1616,1888,1936,1952,1968,2048,
				2064,2080,2096,2688,2704,2736};
		double[] block_y = {154,154,154,154,154,85,85,85,85,85,85,85,85,85,85,85,
				154,154,154,154,85,85,85,85,154,154,85,154,154,154};
		for(int i = 0; i < block_x.length; i++) {
			blocks.add(new Block(block_x[i], block_y[i], BLOCK_SIDE, BLOCK_SIDE));
		}
		
		double[] qBlock_x = {256,336,352,368,1248,1504,1696,1744,1744,1792,2064,2080,
				2720};
		double[] qBlock_y = {154,154,85,154,154,85,154,85,154,154,85,85,154};
		for(int i = 0; i < qBlock_x.length; i++) {
			qBlocks.add(new QuestionBlock(qBlock_x[i], qBlock_y[i], BLOCK_SIDE, BLOCK_SIDE));
		}
		
		double[] ground_x = {0,1136,1424,2480};
		double[] ground_w = {1104,240,1024,1104};
		for(int i = 0; i < ground_x.length; i++) {
			ground.add(new Ground(ground_x[i], 224, ground_w[i], GROUND_H));
		}
		
		double[] goomba_x = {384,672,864,888,1296,1328,1552,1576,1840,1864,2000,2024,2072,
				2096,2800,2824};
		double[] goomba_y = {208,208,208,208,69,69,208,208,208,208,208,208,208,208,208,208};
		for(int i = 0; i < goomba_x.length; i++) {
			goombas.add(new Goomba(goomba_x[i], goomba_y[i], GOOMBA_W, GOOMBA_H));
		}
		
		koopaTroopas.add(new KoopaTroopa(1712, 200, KOOPA_W, KOOPA_H));
	}
	
	private void handleInput() {
		if(KeyHandler.upPressed) {
			mario.jumpRequest();
		}
		if(KeyHandler.rightPressed) {
			mario.walkRightRequest();
		}else if(KeyHandler.leftPressed){
			mario.walkLeftRequest();
		}else if(KeyHandler.extLeftPressed){
			mario.runLeftRequest();
		}else if(KeyHandler.extRightPressed){
			mario.runRightRequest();
		}else {
			mario.stopRequest();
		}
	}

	private void gameOver() {
		
	}
	
	private ArrayList<Entity> getAllEntity(){
		ArrayList<Entity> all = new ArrayList<>();
		all.addAll(pipes);
		all.addAll(blocks);
		all.addAll(qBlocks);
		all.addAll(ground);
		all.addAll(goombas);
		all.addAll(koopaTroopas);
		all.addAll(ground);
		return all;
	}
	
	// Collision Utilities
	private boolean isOverlapping(Mario m, Entity e) {
		return m.getX() + mario.getWidth() > e.getX() && m.getX() < e.getX() + e.getWidth() &&
				m.getY() + mario.getHeight() > e.getY() && m.getY() < e.getY() + e.getHeight();
	}	
	private boolean isCollidingFromTop(Mario m, Entity e) {
		return isOverlapping(m,e) && m.getY() < e.getY() && m.getVelocityY() >= 0;
	}
	private boolean isCollidingFromBottom(Mario m, Entity e) {
		return isOverlapping(m,e) && m.getY() + mario.getHeight() > e.getY() + e.getHeight();
	}
	private boolean isCollidingFromLeft(Mario m, Entity e) {
		return isOverlapping(m,e) && m.getX() < e.getX() && (m.isRunningRight() || m.isWalkingRight());
	}
	private boolean isCollidingFromRight(Mario m, Entity e) {
		return isOverlapping(m,e) && m.getX() > e.getX() && (m.isRunningLeft() || m.isWalkingLeft());
	}
	
	private void checkCollisions() {
		ArrayList<Entity> entities = getAllEntity();
		boolean inAir = true;
		for(Entity e : entities) {
			if(isOverlapping(mario, e)) {
				if(isCollidingFromBottom(mario, e)) {
					mario.manageUp(e.getY() + e.getHeight());
					// manage when hitting block
					e.setGotHit(true);
				}else if(isCollidingFromTop(mario, e)) {
					if(e instanceof Goomba || e instanceof KoopaTroopa) {
						mario.manageLandingOnEnemy(e.getY());
						e.setDead(true);
						score += 100;
					}else {
						mario.manageLanding(e.getY());
					}
					inAir = false;
				}else if(isCollidingFromLeft(mario, e)) {
					if(e instanceof Goomba || e instanceof KoopaTroopa) {
						mario.takeDamage();
					}else {
						mario.manageRight(e.getX());
					}
				}else if(isCollidingFromRight(mario, e)) {
					if(e instanceof Goomba || e instanceof KoopaTroopa) {
						mario.takeDamage();
					}else {
					mario.manageLeft(e.getX() + e.getWidth());
					}
				}
			}
		}
		if(inAir) {
			mario.setIsOnFeet(false);
		}
		for(Goomba g : goombas) {
			if(!g.isDead()) {
				checkCollisionsEnemy(g, entities);
			}
		}
		for(KoopaTroopa k : koopaTroopas) {
			if(!k.isDead()) {
				checkCollisionsEnemy(k, entities);
			}
		}
	}
	
	// Collision Utilities for Enemy Troops
	private boolean isOverlappingEnemy(Entity e, Entity other) {
		return e.getX() + e.getWidth() > other.getX() && e.getX() < other.getX() + other.getWidth() &&
				e.getY() + e.getHeight() > other.getY() && e.getY() < other.getY() + other.getHeight();
	}
	private boolean isCollidingFromTopEnemy(Entity e, Entity other) {
		return isOverlappingEnemy(e,other) && e.getY() < other.getY() && !e.isOnFeet();
	}
	private boolean isCollidingFromLeftEnemy(Entity e, Entity other) {
		return isOverlappingEnemy(e,other) && e.getX() < other.getX();
	}
	private boolean isCollidingFromRightEnemy(Entity e, Entity other) {
		return isOverlappingEnemy(e,other) && e.getX() > other.getX();
	}
	
	private void checkCollisionsEnemy(Entity enemy, ArrayList<Entity> entities) {
		boolean inAir = true;
		for(Entity e : entities) {
			if(e != enemy) {
				if(isCollidingFromTopEnemy(enemy, e)) {
					enemy.manageLanding(e.getY());
					inAir = false;
				}else if(isCollidingFromLeftEnemy(enemy, e)) {
					enemy.manageRight(e.getX());
				}else if(isCollidingFromRightEnemy(enemy, e)) {
					enemy.manageLeft(e.getX() + e.getWidth());
				}
			}
		}
		if(inAir) {
			enemy.setIsOnFeet(false);
		}
	}
	
	private void deleteEntities() {}
	
	public void update() {
		if(gameOver) {
			// end it
		}
		handleInput();
		mario.update();
		mario.setUpCollision(false);
		for(Pipe p : pipes) {p.update(mario.getX(), mario.getScreenX());}
		for(Block b : blocks) {b.update(mario.getX(), mario.getScreenX());}
		for(QuestionBlock q : qBlocks) {q.update(mario.getX(), mario.getScreenX());}
		for(Ground g : ground) {g.update(mario.getX(), mario.getScreenX());}
		for(Goomba g : goombas) {g.update(mario.getX(), mario.getScreenX());}
		for(KoopaTroopa k : koopaTroopas) {k.update(mario.getX(), mario.getScreenX());}
		background.update(mario.getX(), mario.getScreenX());
		
		checkCollisions();
		deleteEntities();
	}
	
	public void draw(Graphics2D g2) {
		background.draw(g2);
		for(Pipe p : pipes) {p.draw(g2);}
		for(Ground g : ground) {g.draw(g2);}
		for(Block b : blocks) {b.draw(g2);}
		for(QuestionBlock q : qBlocks) {q.draw(g2);}
		for(Goomba g : goombas) {g.draw(g2);}
		for(KoopaTroopa k : koopaTroopas) {k.draw(g2);}
		mario.draw(g2);
	}
}
