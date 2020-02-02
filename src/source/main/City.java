package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import source.support.Assets;

public class City {

	private Tile tile;
	private BufferedImage cityImg;
	private Handler handler;
	private int xOffset = 7;
	private int yOffset = 7;
	
	public City(Tile tile, Handler h)
	{
		this.tile = tile;
		cityImg = Assets.campImg;
		this.handler = h;
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(cityImg, (int) (tile.getX() - handler.getCamera().getxOffset() + xOffset), (int) (tile.getY() - handler.getCamera().getyOffset() + yOffset), null);
	}
}
