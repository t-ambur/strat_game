package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import source.support.Assets;

public class City {

	private Tile tile;
	private BufferedImage cityImg;
	private Handler handler;
	private Player owningPlayer;
	private static final int xOffset = 7;
	private static final int yOffset = 7;
	
	public City(Tile tile, Handler h, Player p)
	{
		this.tile = tile;
		cityImg = Assets.campImg;
		this.handler = h;
		this.owningPlayer = p;
	}
	
	public void render(Graphics2D g)
	{
		g.drawImage(cityImg, (int) (tile.getX() - handler.getCamera().getxOffset() + xOffset), (int) (tile.getY() - handler.getCamera().getyOffset() + yOffset), null);
	}
}
