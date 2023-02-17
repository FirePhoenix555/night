package night;

import java.util.Random;

public class Enemy extends GameObject {
	
	private static int speed = 5;
	
	public Enemy(int x_, int y_) {
		super(x_, y_, 10, 10);
	}
	
	public Enemy(int[] pos) {
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
		
		if (dx == 0 && dy == 0) return; // we've hit the player and no longer need to move
		
		double dist = Math.sqrt(dx*dx + dy*dy);
		
//		System.out.println(dist);
		
		dx *= speed / dist;
		dy *= speed / dist;
		
//		System.out.println("New: <" + dx + ", " + dy + ">");
		
		x += dx;
		y += dy;
	}
	
	public void update(Player p) {
		seek(p);
	}
}
