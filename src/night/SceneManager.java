package night;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class SceneManager {
	private Scene currentScene;
	
	private boolean transitioning = false;
	final private static float transitionTime = 1000000000/250f;
	private double temptime;
	private float currentbg;
	private float currentFadeIn = 0;
//	private boolean playerFadedIn = false;
//	private boolean roomFadedIn = false;
	
	final private static String[] introSequence = new String[] {
			"This is you.",
			"And this is your room.",
			"Hello.",
			"Testing.",
			"Aaaaaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa.",
			"B",
			"C",
			"Hello I'm testing to see if this is going to work as intended."
	};
	final private static String[] winSequence = new String[] {
			"You got your water!",
			"Nice job staying hydrated.",
			"I don't really know what to say now but gj!!",
			"Aaaaaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa.",
			"B",
			"C",
			"Hello I'm testing to see if this is going to work as intended."
	};
	private int currentString;
	
	private TextHandler t;
	
	final private static int[] textLocation = new int[] {400, 250};
	
	private GameHandler gh;
	
	public SceneManager(GameHandler g_) {
		gh = g_;
		// initialize(g_);
	}
	
	public void initialize() {
		currentString = 0;
		temptime = 0;
		currentbg = 1;
		currentFadeIn = 0;
//		playerFadedIn = false;
		transitioning = false;
		t = new TextHandler(gh, "", textLocation[0], textLocation[1]);
		
		setScene(Scene.MENU);
	}
	
	public void setScene(Scene s) {
		
//		if (currentScene == Scene.BEDROOM && s == Scene.HALLWAY) {
//			
//		}
		
		
		currentScene = s;
		
		setWalls(s);
		
		currentbg = 1;
		
		if (s == Scene.T1) {
			temptime = System.nanoTime() + transitionTime;
//			transitioning = true;
//			System.out.println(currentbg);
		}
		if (s == Scene.INTRO) {
			setWalls(Scene.BEDROOM);
			currentString = 0;
			currentFadeIn = 0;
//			playerFadedIn = false;
//			roomFadedIn = false;
			transitioning = true; // because the first string has a transition
			t = new TextHandler(gh, "", textLocation[0], textLocation[1]);
		}
		if (s == Scene.WIN) {
			currentString = 0;
			t = new TextHandler(gh, "", textLocation[0], textLocation[1]);
		}
		if (s == Scene.T2) {
			temptime = System.nanoTime() + transitionTime;
//			transitioning = true;
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
			
			
			if (currentString == 1 && transitioning && currentFadeIn <= 1) {
			    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentFadeIn));
			    
				drawRoom(Scene.BEDROOM, g);
				gh.bed.draw(g);
				
				// set transparency back
			    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			    
			    currentFadeIn += 0.05;
			    
			    if (currentFadeIn >= 1) {
			    	currentFadeIn = 0;
//			    	roomFadedIn = true;
			    	transitioning = false;
			    }
			} else if (currentString >= 1) {
				drawRoom(Scene.BEDROOM, g);
				gh.bed.draw(g);
			}
			
			if (currentString == 0 && transitioning && currentFadeIn <= 1) {
				// make the player transparent
			    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentFadeIn));
			    
				gh.player.drawCharacter(g);
				
				// set transparency back
			    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			    
			    currentFadeIn += 0.05;
			    
			    if (currentFadeIn >= 1) {
			    	currentFadeIn = 0;
//			    	playerFadedIn = true;
			    	transitioning = false;
			    }
			} else gh.player.drawCharacter(g);
			
			// do intro sequence
			
			if (gh.mh.mouseHeld) {
				currentString++;
				gh.mh.mouseHeld = false;
				currentFadeIn = 0;
				if (currentString == 1) transitioning = true; // playerFadedIn = true;
				else if (currentString == 2) transitioning = false; // roomFadedIn = true;
				
//				if (currentString == 1) {
//					currentFadeIn = 0;
//				}
				
				if (currentString >= introSequence.length) {
					setScene(Scene.BEDROOM);
					t.resetTimer();
					gh.player = new Player(gh, gh.width/2, gh.height/2);
					return;
				}
			}
			
			if (!t.text.equals(introSequence[currentString])) {
				t = new TextHandler(gh, introSequence[currentString], textLocation[0], textLocation[1]);
				t.setColor(Color.white);
				t.setFont(new Font("Courier New", Font.PLAIN, 15));
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
			
			if (gh.mh.mouseHeld) {
				currentString++;
				gh.mh.mouseHeld = false;
				
				if (currentString >= winSequence.length) {
					
					t.resetTimer();
					
					if (currentString >= winSequence.length + 1) {
						setScene(Scene.T2);
						gh.mh.mouseHeld = false;
					}
					
					return;
				}
			}
			if (currentString < winSequence.length) {
				if (!t.text.equals(winSequence[currentString])) {
					t = new TextHandler(gh, winSequence[currentString], textLocation[0], textLocation[1]);
					t.setColor(Color.black);
					t.setFont(new Font("Courier New", Font.PLAIN, 15));
				}
				
				t.update();
				t.draw(g);
			}
		} else if (currentScene == Scene.LOSS) {
			gh.setBackground(Color.red);
		} else if (currentScene == Scene.T1) {
			double remainingTime = (temptime - System.nanoTime())/1000000;
			if (remainingTime <= 0) {
				currentbg -= 1/255f;
				if (currentbg <= 0) {
					setScene(Scene.INTRO);
//					transitioning = false;
					return;
				}
			}
			
			temptime += transitionTime;
//			System.out.println(currentbg);
			gh.setBackground(new Color(currentbg, currentbg, currentbg));
		} else if (currentScene == Scene.T2) {
			double remainingTime = (temptime - System.nanoTime())/1000000;
			if (remainingTime <= 0) {
				currentbg -= 2.5/255f;
				if (currentbg <= 0) {
					gh.initialize();
//					transitioning = false;
					return;
				}
			}
			
			temptime += transitionTime;
			gh.setBackground(new Color(1-currentbg, 1, 1-currentbg));
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
