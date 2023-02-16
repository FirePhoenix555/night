package night;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GameHandler extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	final int width = 707, height = 500;
	final int fps = 30; // how often the game updates
	
	Thread gameThread;
	KeyHandler kh = new KeyHandler();
	MouseHandler mh = new MouseHandler();
	
	Player player;
	
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
		player = new Player(width/2, height/2, 15, 15); // x, y, w, h
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
		mh.updateMouseLocation(this);
		player.update(kh, mh);
	}
	
	@Override
	public void paintComponent(Graphics g_) {
		super.paintComponent(g_);
		
		Graphics2D g = (Graphics2D) g_;
		
		player.draw(g);
	
		g.dispose();
	}
}