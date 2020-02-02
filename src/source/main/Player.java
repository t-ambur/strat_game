package source.main;

import java.util.ArrayList;

public class Player {
	
	private int playerNumber;
	
	// owned entities
	private ArrayList<City> cities;
	
	// resources
	
	
	public Player(int num)
	{
		this.playerNumber = num;
		cities = new ArrayList<City>();
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public ArrayList<City> getCities()
	{
		return cities;
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
	
	public int getFood()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getFood();
		return sum;
	}
	
	public int getWood()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getWood();
		return sum;
	}
	
	public int getStone()
	{
		int sum = 0;
		for (int i = 0; i < cities.size(); i++)
			sum += cities.get(i).getStone();
		return sum;
	}
}
