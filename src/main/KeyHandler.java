package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public static boolean upPressed, leftPressed, rightPressed, extRightPressed, extLeftPressed;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_Q) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_E) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			extRightPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			extLeftPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_Q) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_E) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			extRightPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			extLeftPressed = false;
		}
	}

}
