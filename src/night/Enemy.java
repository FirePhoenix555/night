package night;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Enemy extends GameObject {
	
	private static int speed = 5;
	private static float dmg = 0.01f;
	
	public boolean destroyed = false;
	
	public Enemy(int x_, int y_) {
		super(x_, y_, 10, 10);
	}
	
	private Enemy(int[] pos) {
		this(pos[0], pos[1]);
	}
	
	public Enemy() {
		this(genPos());
	}
	
	private static int[] genPos() {
		Random r = new Random();
		
		double theta = r.nextDouble() * 2 * Math.PI;
		final double radius = 200;
		
		int x = (int) (radius * Math.cos(theta));
		int y = (int) (radius * Math.sin(theta));
		
		return new int[]{x + 250, y + 250};
	}
	
	private void seek(Player p) { // Moves directly toward the player
		double dx = p.x - x;
		double dy = p.y - y;
		
//		System.out.println("Old: <" + dx + ", " + dy + ">");
		
		if (dx <= width && dy <= height) {
			p.health -= dmg;
			destroy();
			return; // we've hit the player and no longer need to move
		}
		
		double dist = Math.sqrt(dx*dx + dy*dy);
		
//		System.out.println(dist);
		
		dx *= speed / dist;
		dy *= speed / dist;
		
//		System.out.println("New: <" + dx + ", " + dy + ">");
		
		x += dx;
		y += dy;
	}
	
	public void update(GameHandler g) {
//		if (destroyed) return;
		seek(g.player); // TODO add repelling force from other enemies (j change dx,dy)
		
		if (health <= 0) {
			destroy();
		}
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	@Override
	public void draw(Graphics2D g) {
//		if (destroyed) return;
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
}
