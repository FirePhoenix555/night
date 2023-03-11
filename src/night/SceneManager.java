package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class SceneManager {
	private Scene currentScene;
	
	private boolean transitioning;
	final private static float transitionTime = 1000000000/250f;
	private double temptime;
	private float currentbg;
	
	final private static String[] introSequence = new String[] {
			"This is you.",
			"Hello",
			"Testing",
			"Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
			"B",
			"C"
	};
	private int currentString;
	
	private TextHandler t;
	
	public SceneManager(GameHandler g) {
		initialize(g);
	}
	
	public void initialize(GameHandler g) {
		currentScene = Scene.MENU;
		currentString = 0;
		temptime = 0;
		currentbg = 1;
		transitioning = false;
		t = new TextHandler("", g.width/2, g.height/2, g);
	}
	
	public void setScene(Scene s) {
		currentScene = s;
		if (s == Scene.T1) {
			temptime = System.nanoTime() + transitionTime;
		}
	}
	
	public Scene getScene() {
		return currentScene;
	}
	
	public void drawScene(GameHandler gh, Graphics2D gp) {
		
		
		if (currentScene == Scene.MENU) {
			gh.setBackground(Color.white);
			
		} else if (currentScene == Scene.INTRO) {
			drawRoom(Scene.BEDROOM, gh, gp);
			// do intro sequence
			
			if (gh.mh.mouseHeld) {
				currentString++;
				gh.mh.mouseHeld = false;
				
				if (currentString >= introSequence.length) {
					setScene(Scene.BEDROOM);
					t.resetTimer();
					gh.player = new Player(gh.width/2, gh.height/2, gh);
					return;
				}
			}
			
			if (!t.text.equals(introSequence[currentString])) {
				t = new TextHandler(introSequence[currentString], gh.width/2, gh.height/2, gh);
				t.setColor(Color.white);
			}
			
			t.update();
			t.draw(gp);
			
		} else if (currentScene == Scene.BEDROOM) {
			drawRoom(Scene.BEDROOM, gh, gp);
			
			gh.eh.draw(gp);
			gh.player.draw(gp);
			
		} else if (currentScene == Scene.HALLWAY) {
			
		} else if (currentScene == Scene.KITCHEN) {
			
		} else if (currentScene == Scene.WIN) {
			gh.setBackground(Color.green);
			
		} else if (currentScene == Scene.LOSS) {
			gh.setBackground(Color.red);
		} else if (currentScene == Scene.T1) {
			double remainingTime = (temptime - System.nanoTime())/1000000;
			if (remainingTime <= 0) {
				currentbg -= 1/255f;
				if (currentbg <= 0) {
					setScene(Scene.INTRO);
					return;
				}
			}
			
			temptime += transitionTime;
			
			gh.setBackground(new Color(currentbg, currentbg, currentbg));
		}
	}
	
	private void drawRoom(Scene s, GameHandler gh, Graphics2D gp) {
		if (s == Scene.BEDROOM) {
			gh.setBackground(Color.black);
			
		} else if (s == Scene.HALLWAY) {
			
		} else if (s == Scene.KITCHEN) {
			
		}
	}
}
