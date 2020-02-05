package source.main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import source.support.Events;
import source.support.Settings;
import source.display.GameUI;


public class ActionHandler {
	
	private Handler handler;
	
	// what tiles are we handling actions for
	private Tile selectedTile;
	private Tile previousTile;
	
	// cities owned by the player
	private ArrayList<City> cities;
	
	// Time management
	private int calculatedMonth = 1;
	
	// Production
	
	
	
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
		starting.getOwningPlayer().updateResources();
		System.out.println("starting city placed!");
		if (handler.getGame().getTutorial().getStage() == 0)
    		handler.getGame().getTutorial().completeStage();
	}
	
	public void update()
	{
		checkKeys();
		// check if it has been a month
		int gameMonth = handler.getClock().getGameMonth();
		if (gameMonth != this.calculatedMonth)
		{
			Player[] players = handler.getPlayers();
			calculatedMonth = gameMonth;
			for (int i = 0; i < cities.size(); i++)
				cities.get(i).gatherResources();
			updatePlayerResources(players);
			handleFoodConsumption();
			updatePlayerResources(players);
		}
	}
	
	public void updatePlayerResources(Player[] players)
	{
		for (int i = 0; i < players.length; i++)
		{
			if (players[i] != null)
			{
				players[i].updateResources();
			}
		}
	}
	
	public void checkKeys()
	{
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				if (handler.getWorld().getUI().isBigBoxActive())
				{
					if (selectedTile.isCityPresent())
					{
						City cy = selectedTile.getCity();
						int status = cy.setProduction(City.FORAGE);
						if (status != City.NO_ERROR)
						{
							GameUI ui = handler.getWorld().getUI();
							ui.changeTitle(ui.MSG, "Production Error");
							ui.changeText(ui.MSG, "Failed to forage food");
						}
					}
				}
			}
		}
	}
	
	public void handleFoodConsumption()
	{	
		// for all cities in the game
		for (int i = 0; i < cities.size(); i++)
		{
			City cy = cities.get(i);
			// try to consume food locally, return unfed amount
			int foodNeeded = cy.reduceFood(cy.getPopulation());
			// if we could't feed everyone
			if (foodNeeded > 0)
			{
				// grab cities owning player and find out how much excess food there is
				Player p = cy.getOwningPlayer();
				int playersFood = p.getFood();
				// if the players excess can cover the amount
				if (playersFood - foodNeeded >= 0)
				{
					p.reduceFood(foodNeeded);
				}
				else
				{
					// people starve
					cy.reduceManpower(foodNeeded);
				}
			}
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
