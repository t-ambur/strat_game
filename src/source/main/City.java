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
	private int pfood;
	private int wood;
	private int pwood;
	private int stone;
	private int pstone;
	
	// Production
	private boolean producing;
	private int productionType;
	private String status;
	//
	public static final int FORAGE = 0, WOOD_CUT = 1, STONE_HARVEST = 2;
	public static final int ALREADY_PRODUCING = -1, NO_MANPOWER = -2, NO_ERROR = -3, NOT_ASSIGNED = -4, WRONG_TYPE = -5;
	//
	private int foragers;
	private int woodCutters;
	private int stoneHarvesters;
	
	public City(Tile tile, Handler h, Player p, int num)
	{
		this.tile = tile;
		cityImg = Assets.campImg;
		this.handler = h;
		this.owningPlayer = p;
		this.cityNumber = num;
		
		producing = false;
		productionType = NOT_ASSIGNED;
		
		manpower = 10;
		population = 10;
		food = 100;
		wood = 0;
		stone = 0;
		
		pfood = 0;
		pwood = 0;
		pstone = 0;
		
		foragers = 0;
		woodCutters = 0;
		stoneHarvesters = 0;
		
		status = "";
		
		owningPlayer.addCity(this);
		tile.addCity(this);
		owningPlayer.updateResources();
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
		
		producing = true;
		
		if (type == FORAGE)
		{
			productionType = FORAGE;
			manpower -= 1;
			foragers += 1;
			producing = false;
			return NO_ERROR;
		}
		else if (type == WOOD_CUT)
		{
			productionType = WOOD_CUT;
			manpower -= 1;
			woodCutters += 1;
			producing = false;
			return NO_ERROR;
		}
		else if (type == STONE_HARVEST)
		{
			productionType = STONE_HARVEST;
			manpower -= 1;
			stoneHarvesters += 1;
			producing = false;
			return NO_ERROR;
		}
		
		return WRONG_TYPE;
	}
	
	public void gatherResources()
	{
		status = "";
		gatherFood();
		gatherWood();
		gatherStone();
	}
	
	public void gatherFood()
	{
		Random rand = new Random();
		int forage_sum = 0;
		int failed_forages = 0;
		// generate between 0 to 5 food per forager
		for (int i = 0; i < foragers; i++)
		{
			int forage_amount = 0;
			if (tile.getT() == Settings.T_PLAINS || tile.getT() == Settings.T_FOREST)
				forage_amount = rand.nextInt(6) + 1;
			else
				forage_amount = rand.nextInt(4);
			
			if (forage_amount == 0)
			{
				failed_forages++;
			}
			forage_sum += forage_amount;
		}
		pfood = forage_sum;
		food += forage_sum;
		if (failed_forages > 0)
		{
			status += (failed_forages + " foragers failed to find any food.\n");
		}
	}
	
	public void gatherStone()
	{
		Random rand = new Random();
		int forage_sum = 0;
		// generate
		for (int i = 0; i < foragers; i++)
		{
			int forage_amount = 0;
			if (tile.getT() == Settings.T_MOUNTAIN)
				forage_amount = rand.nextInt(6) + 1;
			else
				forage_amount = rand.nextInt(4) + 1;
			
			forage_sum += forage_amount;
		}
		pstone = forage_sum;
		stone += forage_sum;
	}
	
	public void gatherWood()
	{
		int amount = 3;
		if (tile.getT() == Settings.T_MOUNTAIN || tile.getT() == Settings.T_BEACH || tile.getT() == Settings.T_DESERT)
			amount = 1;
		else if (tile.getT() == Settings.T_FOREST)
			amount = 5;
		pwood = amount;
		wood += amount;
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
			int shortage = amount - food;
			food = 0;
			return shortage;
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
	
	public Tile getTile()
	{
		return tile;
	}
	
	public int getPrvFood()
	{
		return pfood;
	}
	
	public int getPrvWood()
	{
		return pwood;
	}
	
	public int getPrvStone()
	{
		return pstone;
	}
	
	public int getWoodCutters()
	{
		return woodCutters;
	}
	
	public int getStoneHarvesters()
	{
		return stoneHarvesters;
	}
}
