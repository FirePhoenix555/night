package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {
	final private int speed = 10; // how fast the player moves
	final private int fps = 10; // how often the player moves
	final private double moveInterval = 1000000000/fps;
	private double nextMoveTime;
	
	public Player(int x_, int y_, int width_, int height_) {
		super(x_, y_, width_, height_);
		nextMoveTime = System.nanoTime() + moveInterval;
	}
	
	public void update(KeyHandler kh) {
		double remainingTime = (nextMoveTime - System.nanoTime())/1000000;
		
		if (remainingTime <= 0) {
			move(kh.up, kh.left, kh.down, kh.right);
			nextMoveTime += moveInterval;
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
	}
}
