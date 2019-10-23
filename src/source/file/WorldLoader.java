package source.file;

import java.awt.Point;

import source.main.Tile;
import source.support.Assets;

public class WorldLoader {
	// path to the file to load
	private String path;
	// arrays for storing information from the file
	private String[] comments;
	private int[] data;
	// the first two values of the world are the width and height
	private int width;
	private int height;
	// where to reset the home button to
	Point home;
	// name of the world
	private String name;
	// was the load successful
	private boolean loadedData;
	
	public WorldLoader(String path)
	{
		this.path = path;
		name = "name not loaded";
		home = null;
		width = 0;
		height = 0;
		load();
	}
	
	private void load()
	{
		FileManager fm = new FileManager(path);
		data = fm.read();
		comments = fm.getWords();
		if (data == null)
		{
			System.err.println("FATAL ERROR: Unable to load world! Data is null.");
			loadedData = false;
			return;
		}
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] < 0)
			{
				System.err.println("FATAL ERROR: Unable to load world! Data value less than 0.");
				loadedData = false;
				return;
			}
		}
		if (data.length < 5)
		{
			System.err.println("FATAL ERROR: Unable to load world! Data array length is less than 5!");
			loadedData = false;
			return;
		}
		
		width = data[0];
		height = data[1];
		home = new Point(data[2], data[3]);
		int[] convert = new int[data.length-4];
		
		for (int i = 0; i < convert.length; i++)
			convert[i] = data[i+4];
		data = convert;
		
		System.out.println("Succesfully read data from world file: " + path);
		loadedData = true;
		
		if (comments.length > 0)
			name = comments[0];
	}
	
	public Tile[][] generateTileSet()
	{
		Tile[][] tileSet = new Tile[width][height];
		int x = 0;
		int y = 0;
		int tileID = 0;
		
		for (int i = 0; i < tileSet.length; i++) // for every row
        {
        	if ((i%2) == 0) // if you are at an even row
        		x = 0;
        	else // you are at an odd row
        		x = 20;
        	
        	for (int j = 0; j < tileSet[i].length; j++) // for each column in that row
        	{
        		if (data[tileID] == 0)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileOceanImg,i,j,tileID++);
        		else if (data[tileID] == 1)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tilePlainsImg,i,j,tileID++);
        		else if (data[tileID] == 2)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileBeachImg,i,j,tileID++);
        		else if (data[tileID] == 3)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileForestImg,i,j,tileID++);
        		else if (data[tileID] == 4)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileSwampImg,i,j,tileID++);
        		else if (data[tileID] == 5)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileMountainImg,i,j,tileID++);
        		else if (data[tileID] == 6)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileLakeImg,i,j,tileID++);
        		else if (data[tileID] == 7)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileDesertImg,i,j,tileID++);
        		else if (data[tileID] == 8)
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileSnowImg,i,j,tileID++);
        		else
        			tileSet[i][j] = new Tile(data[tileID],x,y,Assets.tileErrorImg,i,j,tileID++);
        		x += 40; // move over one tile
        	}
        	y += 30; // move down one row
        }
		
		return tileSet;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public boolean loaded()
	{
		return loadedData;
	}
	
	public int[] getWorld()
	{
		if (loadedData == true)
			return data;
		else
			return null;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Point getHome()
	{
		return home;
	}
}
