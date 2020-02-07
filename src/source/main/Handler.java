package source.main;

import source.display.Camera;
import source.display.World;
import source.file.OptionsData;
import source.input.KeyManager;
import source.input.MouseManager;
import source.support.Clock;
import source.display.GameUI;
import source.display.LogText;

public class Handler {
	
	private Game game;
	private World world;
	private ActionHandler actionH;
	
	public Handler(Game game){
		this.game = game;
		this.actionH = new ActionHandler(this);
	}
	
	public Camera getCamera(){
		return game.getCamera();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public Player[] getPlayers() {
		return game.getPlayers();
	}
	
	public ActionHandler getActionHandler() {
		return actionH;
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public Clock getClock()
	{
		return game.getClock();
	}
	
	public OptionsData getOptData()
	{
		return game.getOptData();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public GameUI getUI()
	{
		return getWorld().getUI();
	}
}
