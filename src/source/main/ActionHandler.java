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
	private int cycleIndex; // used for cycling cities with F1 key
	
	// Time management
	private int calculatedMonth = 1;
	
	// Production
	
	
	
	public ActionHandler(Handler h)
	{
		selectedTile = null;
		previousTile = null;
		this.handler = h;
		cities = new ArrayList<City>();
		cycleIndex = 0;
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
		if (selectedTile == null)
			return;
		// action manager controls
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				if (handler.getWorld().getUI().isBigBoxActive())
				{
					if (selectedTile.isCityPresent())
					{
						if (handler.getGame().getTutorial().getStage() == 3)
				    		handler.getGame().getTutorial().completeStage();
						City cy = selectedTile.getCity();
						int status = cy.setProduction(City.FORAGE);
						if (status != City.NO_ERROR)
						{
							GameUI ui = handler.getWorld().getUI();
							ui.changeTitle(ui.MSG, "Production Error");
							String t = "Failed to train forager:\n";
							if (status == City.ALREADY_PRODUCING)
								t += "You are already training another unit!";
							else if (status == City.NO_MANPOWER)
								t += "You have no manpower!\n";
							ui.changeText(ui.MSG, t);
						}
					}
				}
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_2))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				if (handler.getWorld().getUI().isBigBoxActive())
				{
					if (selectedTile.isCityPresent())
					{
						if (handler.getGame().getTutorial().getStage() == 3)
				    		handler.getGame().getTutorial().completeStage();
						City cy = selectedTile.getCity();
						int status = cy.setProduction(City.WOOD_CUT);
						if (status != City.NO_ERROR)
						{
							GameUI ui = handler.getWorld().getUI();
							ui.changeTitle(ui.MSG, "Production Error");
							String t = "Failed to train wood cutter:\n";
							if (status == City.ALREADY_PRODUCING)
								t += "You are already training another unit!";
							else if (status == City.NO_MANPOWER)
								t += "You have no manpower!\n";
							ui.changeText(ui.MSG, t);
						}
					}
				}
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_3))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				if (handler.getWorld().getUI().isBigBoxActive())
				{
					if (selectedTile.isCityPresent())
					{
						if (handler.getGame().getTutorial().getStage() == 3)
				    		handler.getGame().getTutorial().completeStage();
						City cy = selectedTile.getCity();
						int status = cy.setProduction(City.STONE_HARVEST);
						if (status != City.NO_ERROR)
						{
							GameUI ui = handler.getWorld().getUI();
							ui.changeTitle(ui.MSG, "Production Error");
							String t = "Failed to train stone harvester:\n";
							if (status == City.ALREADY_PRODUCING)
								t += "You are already training another unit!";
							else if (status == City.NO_MANPOWER)
								t += "You have no manpower!\n";
							ui.changeText(ui.MSG, t);
						}
					}
				}
			}
		}
		
		// misc controls
		// cycle through cities
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_F1))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom() && handler.getPlayers() != null)
			{
				ArrayList<City> playerCities = handler.getPlayers()[0].getCities();
				if (playerCities != null && selectedTile != null && playerCities.size() > 0)
				{
					if (cycleIndex >= playerCities.size())
					{
						cycleIndex = 0;
					}
					previousTile = selectedTile;
					selectedTile = playerCities.get(cycleIndex).getTile();
					cycleIndex++;
					handler.getWorld().setTiles(previousTile, selectedTile);
					if (handler.getGame().getTutorial().getStage() == 5)
			    		handler.getGame().getTutorial().completeStage();
				}
			}
		}
		// handle log messages
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_P))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				handler.getUI().forwardLog();
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_O))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				handler.getUI().previousLog();
			}
		}
		
		// time controls
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE))
		{
			handler.getClock().togglePause();
			if (handler.getGame().getTutorial().getStage() == 4)
	    		handler.getGame().getTutorial().completeStage();
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PLUS) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))
		{
			handler.getClock().increaseSpeed();
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_UNDERSCORE))
		{
			handler.getClock().decreaseSpeed();
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
					int toStarve = cy.reduceManpower(foodNeeded);
					if (toStarve > 0)
					{
						boolean noPeople = cy.seriousStarvation(toStarve);
						if (noPeople)
						{
							// remove city
							int num = cy.getCityNum();
							cities.remove(num);
							cy.getOwningPlayer().removeCity(num);
							cy.getTile().removeCity();
							GameUI ui = handler.getUI();
							if (cy.getOwningPlayer().getCities().size() <= 0)
							{
								ui.changeText(ui.MSG, "All Cities have fallen!\nCity Name: " + cy.getCityNum() + " starved!");
								ui.changeTitle(ui.MSG, "GAME OVER");
								handler.getClock().togglePause();
							}
							else
							{
								ui.changeText(ui.MSG, "A city has fallen due to starvation!\nName: " + cy.getCityNum());
								ui.changeTitle(ui.MSG, "STARVATION");
							}
						}
					}
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
