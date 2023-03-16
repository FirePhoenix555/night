package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {
	final private static int speed = 10; // how fast the player moves
	final private static float fps = 10; // how often the player moves
	
	final private static float healthRegen = 0.001f;
	
	public boolean dead = false;
	
	public Flashlight f;
	
	public Player(GameHandler gh_, int x_, int y_) {
		super(gh_, x_, y_, 15, 15);
		
		f = new Flashlight(gh_, this, 0, 0);
		
		t = new Timer(fps, this);
	}
	
	@Override
	public void onTimer() {
		move(gh.kh.up, gh.kh.left, gh.kh.down, gh.kh.right);
	}
	
	public void update() {
		t.update();
		
		f.update();
		
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
		
		for (Wall w : gh.walls) {
			if (w.intersects(this, gh.sm)) {
				x -= dx;
				y -= dy;
				
				// If one direction is fine then do that first!
				x += dx;
				boolean t = true;
				for (Wall w_ : gh.walls) {
					if (w_.intersects(this)) t = false;
				}
				if (t) dx = 0;
				else x -= dx;
				
				y += dy;
				t = true;
				for (Wall w_ : gh.walls) {
					if (w_.intersects(this)) t = false;
				}
				if (t) dy = 0;
				else y -= dy;
				
				// stupid solution but works
				// step forward 1 at a time until you intersect
				if (dx != 0) dx /= Math.abs(dx); // = +- 1
				if (dy != 0) dy /= Math.abs(dy); // = +- 1
				while (!w.intersects(this)) {
					x += dx;
					y += dy;
				}
				x -= dx;
				y -= dy;
			}
		}
		
//		if (x < 0 || x + width > g.width) {
//			// off in horizontal direction
//			if (x < 0) x = 0;
//			else x = g.width;
//		}
//		
//		if (y < 0 || y > g.height) {
//			// off in vertical direction
//			if (y < 0) y = 0;
//			else y = g.height;
//		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawCharacter(g);
		
		f.draw(g);
		
		drawHealthBar(g);
	}
	
	public void drawCharacter(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
	
	private void drawHealthBar(Graphics2D g) {
		g.setColor(Color.red);
		g.drawRect(5, 5, 200, 20);
		g.fillRect(5, 5, (int) (health * 200), 20);
	}
}
