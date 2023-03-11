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
	
	
//	//testing TODO
//	TextHandler t = new TextHandler("Hello there. Did you know I'm working text now?", width/2, height/2, this);
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
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
		player = new Player(width/2, height/2, this);
		// sm.initialize();
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
			
	//		// testing TODO
	//		t.update();
			
			if (player.dead) {
//				paused = true;
				sm.setScene(Scene.LOSS);
				return;
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
		}
	}
	
	@Override
	public void paintComponent(Graphics g_) {
		super.paintComponent(g_);
		
		Graphics2D g = (Graphics2D) g_;
		
		sm.drawScene(this, g);
		
		g.dispose();
	}
}