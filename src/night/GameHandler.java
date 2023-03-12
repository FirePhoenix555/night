package night;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GameHandler extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	final int width = 707, height = 500;
	final int fps = 30; // how often the game updates
	
	private boolean paused = false;
	
	Thread gameThread;
	KeyHandler kh = new KeyHandler();
	MouseHandler mh = new MouseHandler();
	EnemyHandler eh = new EnemyHandler();
	
	SceneManager sm = new SceneManager(this);
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	GameObject water = new GameObject(this, 25, height/2, 15, 15);
	boolean hasWater = false;
	// water.setTexture();
	// or something
	
	GameObject bed = new GameObject(this, width/2 - 50/2, height/2 - 50/2, 50, 50);
	
	Wall[] walls = new Wall[4];
	
	public GameHandler() {
		setPreferredSize(new Dimension(width, height));
		setDoubleBuffered(true);
		addKeyListener(kh);
		addMouseListener(mh);
		setFocusable(true);
		
		initialize();
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void initialize() {
		this.setBackground(Color.black);
		player = new Player(this, width/2, height/2);
		eh = new EnemyHandler();
		sm.initialize();
		hasWater = false;
	}

	@Override
	public void run() {
		double loopInterval = 1000000000/fps;
		double nextLoopTime = System.nanoTime() + loopInterval;
		
		while (gameThread != null) {
			update();
			repaint();
			
			try {
				double remainingTime = (nextLoopTime - System.nanoTime())/1000000;
				if (remainingTime < 0) remainingTime = 0;
				Thread.sleep((long) remainingTime);
				nextLoopTime += loopInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void update() {
		if (paused) {
			return;
		}
		
		Scene s = sm.getScene();
		
		if (s == Scene.BEDROOM || s == Scene.HALLWAY || s == Scene.KITCHEN) {
			mh.updateMouseLocation(this);
	//		player.update(kh, mh);
			player.update();
			
			eh.update(this);
			
			if (player.dead) {
//				paused = true;
				sm.setScene(Scene.LOSS);
				return;
			}
			
//			boolean t = false;
//			for (Wall w : walls) {
//				if (w.intersects(player)) {
//					
//				}
//				
//			}
		}
		if (s == Scene.BEDROOM) {
			if (hasWater && player.intersects(bed)) {
				sm.setScene(Scene.WIN);
			}
		} else if (s == Scene.KITCHEN) { 
			// water.update();
			
			if (water.intersects(player)) {
				// System.out.println("ok");
				hasWater = true;
			}
			
			
		} else if (s == Scene.MENU) {
			if (mh.mouseHeld) {
				sm.setScene(Scene.T1);
				mh.mouseHeld = false;
			}
		} else if (s == Scene.LOSS) {
			if (mh.mouseHeld) {
				sm.setScene(Scene.MENU);
				initialize();
				mh.mouseHeld = false;
			}
		} else if (s == Scene.WIN) {
			if (mh.mouseHeld) {
				sm.setScene(Scene.MENU);
				initialize();
				mh.mouseHeld = false;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g_) {
		super.paintComponent(g_);
		
		Graphics2D g = (Graphics2D) g_;
		
		sm.drawScene(g);
		
		g.dispose();
	}
}