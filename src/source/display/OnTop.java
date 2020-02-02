package source.display;

import java.awt.Graphics2D;
import java.util.ArrayList;

import source.main.Handler;
import source.main.City;

public class OnTop {
	
	private Handler handler;
	
	public OnTop(Handler h)
	{
		this.handler = h;
	}
	
	public void render(Graphics2D g)
	{
		ArrayList<City> cities = handler.getActionHandler().getCities();
		for (int i = 0; i < cities.size(); i++)
			cities.get(i).render(g);
	}
}
