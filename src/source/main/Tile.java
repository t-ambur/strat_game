package source.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {
	
	// static related to Tile
	public static final int TILEWIDTH = 40;
	public static final int TILEHEIGHT = 40;
	
	
	// class start
	private final int TILE_ID; // represents which tile this is on the map, should be unique for each tile
	private int i;
	private int j; // i and j represent this tile's position in an array
	
	private int terrain; // represents the terrain type for this tile
	private BufferedImage img; // the image of this tile
	
	private int x; // x-coordinate where this tile is located
	private int y; // y-coordinate where this tile is located

	private boolean hasCity;
	
	public Tile(int t, int x, int y, BufferedImage img, int id)
	{
		terrain = t;
		this.x = x;
		this.y = y;
		this.img = img; // passing the image to the alternate constructor is probably faster than loading an image for every tile constructed
		this.TILE_ID = id;
		
		i = -1;
		j = -1;
		init();
	}
	
	public Tile(int t, int x, int y, BufferedImage img, int i, int j, int id)
	{
		terrain = t;
		this.x = x;
		this.y = y;
		this.img = img; // passing the image to the alternate constructor is probably faster than loading an image for every tile constructed
		this.i = i;
		this.j = j;
		this.TILE_ID = id;
		init();
	}
	
	public void init()
	{
		hasCity = false;
	}
	
	public void addCity()
	{
		hasCity = true;
	}
	
	public boolean isCityPresent()
	{
		return hasCity;
	}
	
	public void setCoor(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setTerrain(int t)
	{
		terrain = t;
	}
	
	public int getT()
	{
		return terrain;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getI()
	{
		return i;
	}
	
	public int getJ()
	{
		return j;
	}
	
	public int getTileID()
	{
		return TILE_ID;
	}
	
	public BufferedImage getImg()
	{
		return img;
	}
	
	public void render(Graphics2D g)
    {
        g.drawImage(img, x, y, null);
    }
	
	// overloaded
	public void render(Graphics2D g, int x, int y){
		g.drawImage(img, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public String toString() // doesn't tell you what kind of tile it is, but it'll do
	{
		return "Tile #" + TILE_ID + ", coor: (" + x + "," + y + ") array: [" + i + "][" + j + "]";  
	}
	
	public boolean equals(Object obj) // only checks tile number and terrain type
	{
		if (obj instanceof Tile)
		{
			Tile t2 = (Tile) obj;
			
			return t2.getTileID() == TILE_ID;
		}
		else
			return false;
	}

}
