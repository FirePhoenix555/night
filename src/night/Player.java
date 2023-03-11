package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {
	final private static int speed = 10; // how fast the player moves
	final private static float fps = 10; // how often the player moves
	
	final private static float healthRegen = 0.001f;
	
	public boolean dead = false;
	
	public Flashlight f;
	
	private GameHandler g;
	
	public Player(int x_, int y_, GameHandler g_) {
		super(x_, y_, 15, 15);
		
		g = g_;
		
		f = new Flashlight(this, 0, 0);
		
		t = new Timer(fps, g, this);
	}
	
	@Override
	public void onTimer() {
		move(g.kh.up, g.kh.left, g.kh.down, g.kh.right);
	}
	
	public void update() {
		t.update();
		
		f.update(g.mh);
		
		if (health <= 0) {
			dead = true;
		} else {
			health += healthRegen;
			if (health > 1) health = 1;
		}
	}
	
	private void move(boolean up, boolean left, boolean down, boolean right) {
		int dx = 0, dy = 0;
		
		if (up) dy = -speed;
		if (down) dy = speed;
		if (left) dx = -speed;
		if (right) dx = speed;
		
		if (dx != 0 && dy != 0) {
			// Moving diagonally shouldn't let you speed up
			dx /= Math.sqrt(2);
			dy /= Math.sqrt(2);
		}
		
		x += dx;
		y += dy;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
		f.draw(g);
		
		g.setColor(Color.red);
		g.drawRect(5, 5, 200, 20);
		g.fillRect(5, 5, (int) (health * 200), 20);
	}
}
