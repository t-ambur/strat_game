package source.display;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import source.file.WorldLoader;
import source.main.Handler;
import source.main.Tile;
import source.support.Assets;
import source.support.Text;
import source.ui.UITextHolder;

public class World {
	
	private Handler handler;
	// how many tiles wide and high the map is
	private int width, height;
	// how many tiles there are total and what type are they
	//private int mapSize; // later classify this as tiny, small, medium, large, huge, etc.
	// The world map
	private WorldLoader map;
	// The UI for changing in game
	private GameUI gameUI;
	// The tileSet and it's pointers
	private Tile[][] tileSet;
	private Tile selectedTile;
	private Tile prvSelectedTile;
	
	// Mouse positions
	private Point virtualMP;
	private double vMx;
	private double vMy;
	
	// Home position of this world, home is always two int values
	private Point home;
	
	// what zoom are we at now? Where is the center of the screen?
	private double zoomNow;
	
	public World(Handler handler, String path, GameUI ui){
		this.handler = handler;
		this.gameUI = ui;
		map = new WorldLoader(path);
		if (!map.loaded())
		{
			System.err.println("FATAL ERROR: FORCE CLOSE: MAP WAS NOT LOADED PROPERLY!");
			System.exit(0);
		}
		width = map.getWidth();
		height = map.getHeight();
		//mapSize = width*height;
		this.home = map.getHome();
		
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		virtualMP = new Point((int) vMx, (int) vMy);
		
		zoomNow = handler.getCamera().getDefaultZoom();
		
		init();
		loadWorld();
	}
	
	public void init()
	{
		selectedTile = null;
		prvSelectedTile = null;
		handler.getCamera().centerOnPoint(home);
		gameUI.setBounds((int) home.getX(), (int) home.getY());
	}
	
	public void update(){
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		virtualMP.setLocation((int)vMx, (int)vMy);
		
		// if the left mouse button is clicked, search all the tiles and see if the mouse is inside them. Uses 4 rectangles to make the clicking more accurate
		if (handler.getMouseManager().isLeftPressed())
		{
			for(int i = 0; i < tileSet.length; i++)
            {
            	for (int j = 0; j < tileSet[i].length; j++)
            	{
            		if(new Rectangle(tileSet[i][j].getX(), tileSet[i][j].getY() + 10, 40, 20).contains(virtualMP))
            		{
            			prvSelectedTile = selectedTile;
            			selectedTile = tileSet[i][j];
            			break;
            		}
            		else if(new Rectangle(tileSet[i][j].getX() + 15, tileSet[i][j].getY() + 2, 10, 36).contains(virtualMP))
            		{
            			prvSelectedTile = selectedTile;
            			selectedTile = tileSet[i][j];
            			break;
            		}
            		else if(new Rectangle(tileSet[i][j].getX() + 9, tileSet[i][j].getY() + 5, 22, 30).contains(virtualMP))
            		{
            			prvSelectedTile = selectedTile;
            			selectedTile = tileSet[i][j];
            			break;
            		}
            		else if(new Rectangle(tileSet[i][j].getX() + 5, tileSet[i][j].getY() + 7, 30, 26).contains(virtualMP))
            		{
            			prvSelectedTile = selectedTile;
            			selectedTile = tileSet[i][j];
            			break;
            		}
            	}
            }
			handler.getActionHandler().updateTile(selectedTile, prvSelectedTile);
		}
		
		
		mouseMoveCamera();
		keyMoveCamera();
	}
	
	public void keyMoveCamera()
	{	
		// UP
		if (handler.getKeyManager().W)
		{
			handler.getCamera().moveBoundsUp();
		}
		// DOWN
		if (handler.getKeyManager().S)
		{
			handler.getCamera().moveBoundsDown();
		}
		// LEFT
		if (handler.getKeyManager().A)
		{
			handler.getCamera().moveBoundsLeft();
		}
		// RIGHT
		if (handler.getKeyManager().D)
		{
			handler.getCamera().moveBoundsRight();
		}
		
		/*if (handler.getKeyManager().UP)
			handler.getCamera().testUp();
		if (handler.getKeyManager().DOWN)
			handler.getCamera().testDown();;
		if (handler.getKeyManager().LEFT)
			handler.getCamera().testLeft();;
		if (handler.getKeyManager().RIGHT)
			handler.getCamera().testRight();;*/
		
		
		// Home key should reset zoom to default
		if (handler.getKeyManager().HOME)
		{
			handler.getCamera().resetZoom();
			handler.getCamera().centerOnPoint(home);
			gameUI.showAll();
		}
		// pg_down should zoom in centered on the screen
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_DOWN))
		{
			handler.getCamera().zoomIn();
			gameUI.hideAll();
		}
		// pg_up should zoom out the way it came in
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_UP))
		{
			handler.getCamera().zoomOut();
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
				gameUI.showAll();
		}
	}
	
	public void mouseMoveCamera()
	{
		// UP
		if (handler.getCamera().upperContains(virtualMP))
		{
			handler.getCamera().moveBoundsUp();
		}
		// DOWN
		if (handler.getCamera().lowerContains(virtualMP))
		{
			handler.getCamera().moveBoundsDown();
		}
		// LEFT
		if (handler.getCamera().leftContains(virtualMP))
		{
			handler.getCamera().moveBoundsLeft();
		}
		// RIGHT
		if (handler.getCamera().rightContains(virtualMP))
		{
			handler.getCamera().moveBoundsRight();
		}
		
		if (handler.getMouseManager().isMiddlePressed())
		{
			handler.getCamera().resetZoom();
			gameUI.showAll();
		}
	}
	
	public void render(Graphics2D g){
		
		// check to see if the zoom has changed
    	if (zoomNow != handler.getCamera().getZoom())
    		zoomNow = handler.getCamera().getZoom();
    	// scale all objects to the zoom ratio
    	g.scale(zoomNow, zoomNow);
    	
    	// big for loop to render all the tiles under a buffer. Only renders the tiles currently on the screen
		for (int i = 0; i < tileSet.length; i++)
        {
        	for (int j = 0; j < tileSet[i].length; j++)
        	{
        		// greater than LEFT of screen and less than BOTTOM of screen plus offset
        		if (tileSet[i][j].getX() > handler.getCamera().getxOffset() - Tile.TILEWIDTH && tileSet[i][j].getY() < handler.getCamera().getyOffset() + handler.getHeight())
        		{
        			// less than RIGHT of screen and greater than TOP of screen
        			if (tileSet[i][j].getX() < handler.getWidth() + handler.getCamera().getxOffset() && tileSet[i][j].getY() > handler.getCamera().getyOffset() - Tile.TILEHEIGHT)
        			{
        				tileSet[i][j].render(g, (int) (tileSet[i][j].getX() - handler.getCamera().getxOffset()),
    	        				(int) (tileSet[i][j].getY() - handler.getCamera().getyOffset()));
        			}
        		}
        	}
        }
		
		// if the player has clicked a tile
        if (selectedTile != null)
        {
        	if (handler.getGame().getTutorial().getStage() == 0)
        		handler.getGame().getTutorial().completeStage();
        	int blinkTime = (int) (System.currentTimeMillis() / 500);
        	if ((blinkTime % 2) == 0)
        		g.drawImage(Assets.tileSelectorImg, (int) (selectedTile.getX() - handler.getCamera().getxOffset()),
        				(int) (selectedTile.getY() - handler.getCamera().getyOffset()), null);
        }
        
        // paint the bounds for testing them only
        /*g.setColor(Color.red);
        g.fillRect((int) (handler.getCamera().getLeftBound().getX() - handler.getCamera().getxOffset()), (int) (handler.getCamera().getLeftBound().getY() - handler.getCamera().getyOffset()),
        		(int) handler.getCamera().getLeftBound().getWidth(), (int) handler.getCamera().getLeftBound().getHeight());
        
        g.setColor(Color.blue);
        g.fillRect((int) (handler.getCamera().getRightBound().getX() - handler.getCamera().getxOffset()), (int) (handler.getCamera().getRightBound().getY() - handler.getCamera().getyOffset()),
        		(int) handler.getCamera().getRightBound().getWidth(), (int) handler.getCamera().getRightBound().getHeight());
        
        g.setColor(Color.red);
        g.fillRect((int) (handler.getCamera().getUpperBound().getX() - handler.getCamera().getxOffset()), (int) (handler.getCamera().getUpperBound().getY() - handler.getCamera().getyOffset()),
        		(int) handler.getCamera().getUpperBound().getWidth(), (int) handler.getCamera().getUpperBound().getHeight());
        
        g.setColor(Color.orange);
        g.fillRect((int) (handler.getCamera().getLowerBound().getX() - handler.getCamera().getxOffset()), (int) (handler.getCamera().getLowerBound().getY() - handler.getCamera().getyOffset()),
        		(int) handler.getCamera().getLowerBound().getWidth(), (int) handler.getCamera().getLowerBound().getHeight());*/
	}
		
	private void loadWorld()
	{
		tileSet = map.generateTileSet();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public GameUI getUI()
	{
		return gameUI;
	}

}
