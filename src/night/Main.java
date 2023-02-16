package night;

import javax.swing.JFrame;

//https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
//^ very helpful!

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Game");
		
		GameHandler gh = new GameHandler();
		window.add(gh);
		
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
	}

}