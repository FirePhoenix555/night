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
			"Hello.",
			"Testing.",
			"Aaaaaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa.",
			"B",
			"C",
			"Hello I'm testing to see if this is going to work as intended."
	};
	private int currentString;
	
	private TextHandler t;
	
	private GameHandler gh;
	
	public SceneManager(GameHandler g_) {
		gh = g_;
		// initialize(g_);
	}
	
	public void initialize() {
		currentString = 0;
		temptime = 0;
		currentbg = 1;
		transitioning = false;
		t = new TextHandler(gh, "", 0, 0);
		
		setScene(Scene.MENU);
	}
	
	public void setScene(Scene s) {
		
//		if (currentScene == Scene.BEDROOM && s == Scene.HALLWAY) {
//			
//		}
		
		
		currentScene = s;
		
		setWalls(s);
		
		if (s == Scene.T1) {
			temptime = System.nanoTime() + transitionTime;
//			System.out.println(currentbg);
		}
		if (s == Scene.INTRO) {
			currentString = 0;
			t = new TextHandler(gh, "", 0, 0);
		}
		
		gh.eh.destroyAll();
	}
	
	public Scene getScene() {
		return currentScene;
	}
	
	public void drawScene(Graphics2D g) {
		if (currentScene == Scene.MENU) {
			gh.setBackground(Color.white);
			
		} else if (currentScene == Scene.INTRO) {
//			drawRoom(Scene.BEDROOM, gh, gp);
			// do intro sequence
			
			if (gh.mh.mouseHeld) {
				currentString++;
				gh.mh.mouseHeld = false;
				
				if (currentString >= introSequence.length) {
					setScene(Scene.BEDROOM);
					t.resetTimer();
					gh.player = new Player(gh, gh.width/2, gh.height/2);
					return;
				}
			}
			
			if (!t.text.equals(introSequence[currentString])) {
				t = new TextHandler(gh, introSequence[currentString], gh.width/2, gh.height/2);
				t.setColor(Color.white);
			}
			
			t.update();
			t.draw(g);
			
		} else if (currentScene == Scene.BEDROOM) {
			drawRoom(Scene.BEDROOM, g);
			
			gh.bed.draw(g);
			
			gh.eh.draw(g);
			gh.player.draw(g);
			
		} else if (currentScene == Scene.HALLWAY) {
			drawRoom(Scene.BEDROOM, g);
			
			gh.eh.draw(g);
			gh.player.draw(g);
		} else if (currentScene == Scene.KITCHEN) {
			drawRoom(Scene.KITCHEN, g);
			
			if (!gh.hasWater) gh.water.draw(g);
			gh.eh.draw(g);
			gh.player.draw(g);
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
					transitioning = false;
					return;
				}
			}
			
			temptime += transitionTime;
//			System.out.println(currentbg);
			gh.setBackground(new Color(currentbg, currentbg, currentbg));
		}
	}
	
	private void drawRoom(Scene s, Graphics2D g) {
		if (s == Scene.BEDROOM) {
			gh.setBackground(Color.black);
			
			
			for (Wall wall : gh.walls) {
				if (wall != null) wall.draw(g);
			}
//			gp.fillRect(0, 0, gh.width, 20);
			
			
			
		} else if (s == Scene.HALLWAY) {
			gh.setBackground(Color.black);
			
			
			for (Wall wall : gh.walls) {
				if (wall != null) wall.draw(g);
			}
			
		} else if (s == Scene.KITCHEN) {
			gh.setBackground(Color.darkGray);
			
			for (Wall wall : gh.walls) {
				if (wall != null) wall.draw(g);
			}
		}
	}
	
	private void setWalls(Scene s) {
		if (!(s == Scene.BEDROOM || s == Scene.HALLWAY || s == Scene.KITCHEN)) {
			for (int i = 0; i < gh.walls.length; i++) {
				gh.walls[i] = null;
			}
			return;
		}
		
		gh.walls[0] = new Wall(gh, 0, 0, gh.width, 20);
		gh.walls[1] = new Wall(gh, 0, gh.height - 20, gh.width, 20);
		
		if (s == Scene.BEDROOM) {
			gh.walls[2] = new Wall(gh, 0, 0, 20, gh.height);
			gh.walls[2].setDoor(0, gh.height/2 - 50/2, 20, 50, Scene.HALLWAY);
			
			gh.walls[3] = new Wall(gh, gh.width - 20, 0, 20, gh.height);
		} else if (s == Scene.HALLWAY) {
			gh.walls[2] = new Wall(gh, 0, 0, 20, gh.height);
			gh.walls[2].setDoor(0, gh.height/2 - 50/2, 20, 50, Scene.KITCHEN);
			
			gh.walls[3] = new Wall(gh, gh.width - 20, 0, 20, gh.height);
			gh.walls[3].setDoor(gh.width - 20, gh.height/2 - 50/2, 20, 50, Scene.BEDROOM);
		} else if (s == Scene.KITCHEN) {
			gh.walls[2] = new Wall(gh, 0, 0, 20, gh.height);
			
			gh.walls[3] = new Wall(gh, gh.width - 20, 0, 20, gh.height);
			gh.walls[3].setDoor(gh.width - 20, gh.height/2 - 50/2, 20, 50, Scene.HALLWAY);
		}
	}
}
