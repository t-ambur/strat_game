package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import source.support.Assets;

public class City {

	private Tile tile;
	private BufferedImage cityImg;
	private Handler handler;
	private Player owningPlayer;
	private int cityNumber;
	private static final int xOffset = 7;
	private static final int yOffset = 7;
	
	// resources
	private int manpower;
	private int population;
	
	private int food;
	private int wood;
	private int stone;
	
	public City(Tile tile, Handler h, Player p, int num)
	{
		this.tile = tile;
		cityImg = Assets.campImg;
		this.handler = h;
		this.owningPlayer = p;
		this.cityNumber = num;
		
		manpower = 0;
		population = 0;
		food = 0;
		wood = 0;
		stone = 0;
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(cityImg, (int) (tile.getX() - handler.getCamera().getxOffset() + xOffset), (int) (tile.getY() - handler.getCamera().getyOffset() + yOffset), null);
	}
	
	public Player getOwningPlayer()
	{
		return owningPlayer;
	}
	
	public void removeOwningPlayer()
	{
		if (owningPlayer != null)
		{
			handler.getPlayers()[owningPlayer.getPlayerNumber()].removeCity(cityNumber);
			owningPlayer = null;
		}
	}
	
	public void setOwningPlayer(Player p, int num)
	{
		owningPlayer = p;
		handler.getPlayers()[p.getPlayerNumber()].addCity(this);
		cityNumber = num;
	}
	
	public int getManpower() {
		return manpower;
	}

	public void addManpower(int amount) {
		this.manpower += amount;
	}
	
	public boolean reduceManpower(int amount) 
	{
		if (manpower - amount >= 0)
		{
			manpower -= amount;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getPopulation() {
		return population;
	}

	public void addPopulation(int amount) {
		this.population += amount;
	}
	
	public boolean reducePopulation(int amount) 
	{
		if (population - amount >= 0)
		{
			population -= amount;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getFood() {
		return food;
	}

	public void addFood(int amount) {
		this.food += amount;
	}
	
	public boolean reduceFood(int amount) 
	{
		if (food - amount >= 0)
		{
			food -= amount;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getWood() {
		return wood;
	}

	public void addWood(int amount) {
		this.wood += amount;
	}
	
	public boolean reduceWood(int amount) 
	{
		if (wood - amount >= 0)
		{
			wood -= amount;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean reduceStone(int amount) 
	{
		if (stone - amount >= 0)
		{
			stone -= amount;
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getStone() {
		return stone;
	}

	public void addStone(int amount) {
		this.stone += amount;
	}
}
