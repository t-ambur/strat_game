package source.support;

public class Timer extends Clock {
	
	// variables used in calculating FPS
	private int fps; // the number of time to "render"- frames per second
	private double timePerTick; // used in calculating FPS
	private double delta; // used in calculating FPS
	private long now; // current time in nanoSeconds
	private long lastTime; // the time at the last tick
	private long fpsTimer; // resets every second
	private int ticks; // resets every second
	private final double SECOND = 1000000000;
	
	public Timer()
	{
		super();
		fps = 60;
		timePerTick = SECOND / fps;
		delta = 0;
		now = -1;
		lastTime = System.nanoTime();
		fpsTimer = 0;
		ticks = 0;
	}
	
	// Clock extended methods //
	public void tickTime(boolean printTimer, boolean printClock)
	{
		if (secondPassed())
		{
			super.elapseSec();
			if (printClock && super.isTicking())
				super.printTime();
			manageSecond(printTimer);
		}
	}
	
	////////// FPS METHODS /////////////////////////////////
	public void update()
	{
		now = System.nanoTime();
		delta += (now - lastTime) / timePerTick;
		fpsTimer += now - lastTime;
		lastTime = now;
	}
	
	public boolean renderTime()
	{
		return delta >= 1;
	}
	
	public boolean secondPassed()
	{
		return fpsTimer > SECOND;
	}
	
	public void renderReset()
	{
		incrementTick();
		decrementDelta();
	}
	
	public void fpsReset()
	{
		ticks = 0;
		fpsTimer = 0;
	}
	
	public void incrementTick()
	{
		ticks++;
	}
	
	public void decrementDelta()
	{
		delta--;
	}
	
	public void printTick()
	{
		System.out.println("Ticks and Frames: " + ticks);
	}
	
	public void manageSecond(boolean printFps)
	{
		if (secondPassed())
		{
			if (printFps)
				printTick();
			fpsReset();
		}
	}
	////////////////////////////////////////////////////////////////////////

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public double getTimePerTick() {
		return timePerTick;
	}

	public double getDelta() {
		return delta;
	}

	public long getNow() {
		return now;
	}

	public long getLastTime() {
		return lastTime;
	}

	public long getFpsTimer() {
		return fpsTimer;
	}

	public int getTicks() {
		return ticks;
	}
}
