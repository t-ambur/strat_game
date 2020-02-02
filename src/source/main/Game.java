package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import source.display.Camera;
import source.display.Display;
import source.file.FileData;
import source.file.OptionsData;
import source.input.KeyManager;
import source.input.MouseManager;
import source.states.GameState;
import source.states.MenuState;
import source.states.State;
import source.states.Tutorial;
import source.support.Assets;
import source.support.Events;
import source.support.Settings;
import source.support.Timer;

public class Game implements Runnable {
	
	private Display display;
	private int width, height;
	public String title;
	public boolean fullScreen;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics2D g;
	
	//States
	public State gameState;
	public State menuState;
	
	public Player[] players;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private Camera camera;
	
	//Handler
	private Handler handler;
	
	// Clock - Game Timer
	private Timer timer;
	
	// File Management
	OptionsData oData;
	
	// New Game tutorial
	private Tutorial tutorial;
	public final boolean TUT_ON = true;
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullScreen = true;
		handler = new Handler(this);
		keyManager = new KeyManager();
		mouseManager = new MouseManager(handler);
		players = new Player[Settings.NUMBER_PLAYERS];
		players[0] = new Player(Settings.PLAYER_ZERO);
	}
	// The order of init is important
	private void init(){
		// Import the stored option settings
		oData = new OptionsData(FileData.OPTIONS_PATH);
		if (oData.loaded())
		{
			width = oData.loadedResWidth();
			height = oData.loadedResHeight();
			fullScreen = oData.isFullScreen();
		}
		else // there was something wrong with the loading process
		{
			oData.write(width, height, fullScreen);
		}
		// create the display, continue to init game framework
		display = new Display(title, width, height, fullScreen);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
		Assets.init();
		Settings.init(width, height, fullScreen);
		
		camera = new Camera(handler, 0, 0);
		timer = new Timer();
		
		// true turns on tutorial, false turns off
		tutorial = new Tutorial(handler, TUT_ON);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
	}
	
	public void reset()
	{
		//stop(); // freezes the window
		System.exit(0);
	}
	
	private void update(){
		keyManager.update();
		
		if(State.getState() != null)
			State.getState().update();
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		do
		{
		g = (Graphics2D) bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		if(State.getState() != null)
			State.getState().render(g);
		
		//End Drawing!
		bs.show();
		g.dispose();
		} while (bs.contentsLost());
	}
	
	public void run(){
		
		init();
		// This is the top level game loop
		while(running)
		{
			timer.update();
			if(timer.renderTime())
			{
				update();
				render();
				timer.renderReset();
			}
			// check to see if fpsTimer must reset, then resets it if it is true. Parameter is for whether you want to print FPS and current Time
			timer.tickTime(false, true);
		}
		
		stop();
		
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public Timer getClock()
	{
		return timer;
	}
	
	public Timer getTimer()
	{
		return timer;
	}
	
	public OptionsData getOptData()
	{
		return oData;
	}
	
	public Tutorial getTutorial()
	{
		return tutorial;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Player[] getPlayers()
	{
		return players;
	}
	
	public int getHeight(){
		return height;
	}
	// Create a thread, then thread.start() will call run
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
				
		} catch (InterruptedException e) {
			System.err.println("Caught thread stop error in Game.java");
			e.printStackTrace();
		}
	}
}
