package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import source.support.Assets;
import source.support.Settings;

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
	
	// Production
	private boolean producing;
	private int productionType;
	private String status;
	//
	public static final int FORAGE = 0;
	public static final int ALREADY_PRODUCING = -1, NO_MANPOWER = -2, NO_ERROR = -3, NOT_ASSIGNED = -4;
	//
	private int foragers;
	
	public City(Tile tile, Handler h, Player p, int num)
	{
		this.tile = tile;
		cityImg = Assets.campImg;
		this.handler = h;
		this.owningPlayer = p;
		this.cityNumber = num;
		
		producing = false;
		productionType = NOT_ASSIGNED;
		
		manpower = 0;
		population = 0;
		food = 0;
		wood = 0;
		stone = 0;
		
		foragers = 0;
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(cityImg, (int) (tile.getX() - handler.getCamera().getxOffset() + xOffset), (int) (tile.getY() - handler.getCamera().getyOffset() + yOffset), null);
	}
	
	public int getCityNum()
	{
		return cityNumber;
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
	
	public boolean isProducing()
	{
		return producing;
	}
	
	public int getProductionType()
	{
		return productionType;
	}
	
	public int setProduction(int type)
	{
		if (producing)
		{
			return ALREADY_PRODUCING;
		}
		if (manpower <= 0)
		{
			return NO_MANPOWER;
		}
		
		if (type == FORAGE)
		{
			producing = true;
			productionType = FORAGE;
			manpower -= 1;
			foragers += 1;
			producing = false;
		}
		
		return NO_ERROR;
	}
	
	public void gatherResources()
	{
		status = "";
		gatherFood();
	}
	
	public void gatherFood()
	{
		Random rand = new Random();
		int forage_sum = 0;
		int failed_forages = 0;
		// generate between 0 to 3 food per forager
		for (int i = 0; i < foragers; i++)
		{
			int forage_amount = rand.nextInt(3);
			if (forage_amount == 0)
			{
				if (tile.getT() == Settings.T_PLAINS || tile.getT() == Settings.T_FOREST)
					forage_amount = 1;
				else
				{
					failed_forages++;
				}
			}
			forage_sum += forage_amount;
		}
		food += forage_sum;
		if (failed_forages > 0)
		{
			status += (failed_forages + " foragers failed to find food");
		}
	}
	
	public String getStatus()
	{
		return status;
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
	
	public int reduceFood(int amount) 
	{
		if (food - amount >= 0)
		{
			food -= amount;
			return 0;
		}
		else if (food > 0)
		{
			return amount - food;
		}
		else
		{
			return amount;
		}
	}
	
	public int getWood() {
		return wood;
	}

	public void addWood(int amount) {
		this.wood += amount;
	}
	
	public int reduceWood(int amount)
	{
		if (wood - amount >= 0)
		{
			wood -= amount;
			return 0;
		}
		else if (wood > 0)
		{
			return amount - wood;
		}
		else
		{
			return amount;
		}
	}
	
	public int reduceStone(int amount)
	{
		if (stone - amount >= 0)
		{
			stone -= amount;
			return 0;
		}
		else if (stone > 0)
		{
			return amount - stone;
		}
		else
		{
			return amount;
		}
	}

	public int getStone() {
		return stone;
	}

	public void addStone(int amount) {
		this.stone += amount;
	}
	
	public int getForagers()
	{
		return foragers;
	}
}
