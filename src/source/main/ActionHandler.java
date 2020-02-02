package source.main;

import java.util.ArrayList;
import java.util.HashMap;

import source.support.Events;


public class ActionHandler {
	
	private Handler handler;
	
	// what tiles are we handling actions for
	private Tile selectedTile;
	private Tile previousTile;
	
	// cities owned by the player
	private ArrayList<City> cities;
	
	
	
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
			Events.cityPlaced = true;
			cities.add(new City(selectedTile,handler));
			System.out.println("starting city placed!");
		}
	}
	
	public void update()
	{
		
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
