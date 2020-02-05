package source.main;

import java.util.ArrayList;

import source.support.Settings;

public class Player {
	
	private int playerNumber;
	
	// owned entities
	private ArrayList<City> cities;
	
	// resources
	private int food, wood, stone;
	
	public Player(int num)
	{
		this.playerNumber = num;
		cities = new ArrayList<City>();
		
		food = 0;
		wood = 0;
		stone = 0;
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public String getPlayerName()
	{
		if (playerNumber == Settings.PLAYER_ZERO)
			return "Human Player";
		else
			return "AI";
	}
	
	public ArrayList<City> getCities()
	{
		return cities;
	}
	
	public void updateResources()
	{
		setFood();
		setWood();
		setStone();
	}
	
	public void addCity(City c)
	{
		cities.add(c);
	}
	
	public void removeCity(int num)
	{
		cities.remove(num);
	}
	
	public int getNumCities()
	{
		return cities.size();
	}
	
	public int getManpower()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getManpower();
		return sum;
	}
	
	public int getPopulation()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getPopulation();
		return sum;
	}
	
	public int setFood()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getFood();
		food = sum;
		return sum;
	}
	
	public int setWood()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getWood();
		wood = sum;
		return sum;
	}
	
	public int setStone()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getStone();
		stone = sum;
		return sum;
	}
	
	public int getFood()
	{
		return food;
	}
	
	public int getStone()
	{
		return stone;
	}
	
	public int getWood()
	{
		return wood;
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
}
