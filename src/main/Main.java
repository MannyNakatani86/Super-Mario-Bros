package main;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Super Mario Bros.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // restrict resizing the frame
		
		// adding GamePanel to the frame
		GamePanel gp = new GamePanel();
		frame.add(gp);
		frame.pack(); // the size of gp adapts to the frame
		
		frame.setLocationRelativeTo(null); // pops the frame in the center
		frame.setVisible(true); // now the game will be visible
		
		gp.launchGame(); // start the game
	}

}
