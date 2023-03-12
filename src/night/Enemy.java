package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends GameObject {
	
	private static int speed = 5;
	private static float dmg = 0.075f;
	
	public boolean destroyed = false;
	
	public Enemy(GameHandler gh_, int x_, int y_) {
		super(gh_, x_, y_, 10, 10);
	}
	
	private double[] seek(GameObject g) { // Moves directly toward the player
		double dx = g.x - x;
		double dy = g.y - y;
		
//		System.out.println("Old: <" + dx + ", " + dy + ">");
		
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		if (dist == 0) return new double[] {0, 0};
		
//		System.out.println(dist);
		
		dx *= 1 / dist;
		dy *= 1 / dist;
		
		return new double[] {dx, dy};
		
//		System.out.println("New: <" + dx + ", " + dy + ">");
		
//		x += dx;
//		y += dy;
	}
	
	public void update() {
//		if (destroyed) return;
		
		double dx = Math.abs(gh.player.x - x);
		double dy = Math.abs(gh.player.y - y);
		
		if (dx <= width && dy <= height) {
			gh.player.health -= dmg;
			destroy();
			return;
		}
		
		
		
		double[][] forces = new double[gh.eh.enemies.size() + 1][2];
		
		

		forces[0] = seek(gh.player);
		
		// we're going to deal w forces here
		// seek() returns a force float[2]
		
		for (int i = 0; i < gh.eh.enemies.size(); i++) {
			Enemy e = gh.eh.enemies.get(i);
			forces[i+1] = seek(e);
		}
		
		double totalX = forces[0][0] * 10;
		double totalY = forces[0][1] * 10;
		
		for (int i = 1; i < forces.length; i++) {
			totalX -= forces[i][0] * 1;
			totalY -= forces[i][1] * 1;
		}
		
		double len = Math.sqrt(Math.pow(totalX, 2) + Math.pow(totalY, 2));
		
		if (len != 0) {
			x += speed * totalX / len;
			y += speed * totalY / len;
		}
		
		
		
		
		// add forces but weighted
		// then normalize and multiply by speed
		
		
		
		
		
		
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
