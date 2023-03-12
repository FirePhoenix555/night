package night;

public class Timer {
	private GameObject parent;
//	private GameHandler gh;
	
	private float fps;
	private double updateInterval;
	private double nextUpdateTime;
	
	public Timer(float fps_, GameObject p) {
		fps = fps_;
		updateInterval = 1000000000/fps;
		nextUpdateTime = System.nanoTime() + updateInterval;
		
//		gh = g_;
		parent = p;
	}
	
	public void update() {
		double remainingTime = (nextUpdateTime - System.nanoTime())/1000000;
		if (remainingTime > 0) return;
		nextUpdateTime += updateInterval;
		
		parent.onTimer();
	}
}
