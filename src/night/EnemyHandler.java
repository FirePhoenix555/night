package night;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class EnemyHandler {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	final private static double initialSpawnRate = 0.03;
	final private static double spawnRateRate = 0.000005;
	
	private Random r = new Random();
	
	private double spawnRate = initialSpawnRate;
	
	public EnemyHandler() {
		
	}
	
	void update(GameHandler g) {
		if (r.nextDouble() < spawnRate) {
			// spawn new enemy
			int[] pos = genPos(Math.max(g.width, g.height) * 1.25/2);
			enemies.add(new Enemy(pos[0], pos[1]));
		}
		
		Flashlight f = g.player.f;
		
		for (Enemy e : enemies) {
			e.update(g);
			
			if (f.on && f.intersects(e)) {
				e.health -= Flashlight.dmg;
			}
		}
		
		for (int i = enemies.size() - 1; i >= 0; i--) {
			Enemy e = enemies.get(i);
			if (e.destroyed) enemies.remove(e);
		}
		
		spawnRate += spawnRateRate;
	}
	
	void draw(Graphics2D g) {
		for (Enemy e : enemies) {
			e.draw(g);
		}
	}
	
	private int[] genPos(double radius) {
		double theta = r.nextDouble() * 2 * Math.PI;
		
		int x = (int) (radius * Math.cos(theta));
		int y = (int) (radius * Math.sin(theta));
		
		return new int[]{x + 250, y + 250};
	}
}
