package source.main;

import java.util.ArrayList;

import source.support.Events;
import source.support.Settings;


public class ActionHandler {
	
	private Handler handler;
	
	// what tiles are we handling actions for
	private Tile selectedTile;
	private Tile previousTile;
	
	// cities owned by the player
	private ArrayList<City> cities;
	
	// Time management
	private int calculatedMonth = 1;
	
	
	public ActionHandler(Handler h)
	{
		selectedTile = null;
		previousTile = null;
		this.handler = h;
		cities = new ArrayList<City>();
	}
	
	public void updateTile(Tile selected, Tile prv)
	{
		this.selectedTile = selected;
		this.previousTile = prv;
		if (Events.cityPlaced == false && selected != null)
		{
			setupNewGameCity();
		}
	}
	
	public void setupNewGameCity()
	{
		Events.cityPlaced = true;
		City starting = new City(selectedTile, handler, handler.getPlayers()[Settings.PLAYER_ZERO],0);
		cities.add(starting);
		handler.getPlayers()[Settings.PLAYER_ZERO].addCity(starting);
		selectedTile.addCity(starting);
		starting.addManpower(10);
		starting.addPopulation(10);
		starting.addFood(100);
		System.out.println("starting city placed!");
		if (handler.getGame().getTutorial().getStage() == 0)
    		handler.getGame().getTutorial().completeStage();
	}
	
	public void update()
	{
		handleFoodConsumption();
	}
	
	public void handleFoodConsumption()
	{
		int gameMonth = handler.getClock().getGameMonth();
		if (gameMonth != this.calculatedMonth)
		{
			calculatedMonth = gameMonth;
			// manage food consumption
			for (int i = 0; i < cities.size(); i++)
				cities.get(i).reduceFood(cities.get(i).getPopulation());
		}
	}
	
	public Tile getSelectedTile()
	{
		return this.selectedTile;
	}
	
	public Tile getPrvTile()
	{
		return this.previousTile;
	}
	
	public ArrayList<City> getCities()
	{
		return this.cities;
	}
}
