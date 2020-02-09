package source.display;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import source.main.Handler;
import source.support.Assets;
import source.support.Settings;
import source.ui.ClickListener;
import source.ui.UIImageButton;
import source.ui.UIManager;
import source.ui.UITextHolder;
import source.ui.UITextButton;
import source.main.Tile;
import source.states.State;
import source.main.City;

public class GameUI {
	
	private Handler handler;
	private UIManager uiManager;
	
	private UITextHolder topBar;
	private UITextHolder leftBar;
	private UITextHolder msg;
	private UITextHolder bigBox;
	
	private UITextButton foodButton;
	private UITextButton woodButton;
	private UITextButton stoneButton;
	private UITextButton wallButton;

	private LogText log;
	
	private int page;
	private static final int MAX_PAGES = 2;
	
	public static final int TOP_BAR = 0, LEFT_BAR = 1, MSG = 2, BIG_BOX = 3;
	
	public static final int MSG_WIDTH = 500, MSG_HEIGHT = 400;
	public static final int BIGBOX_W = 1050, BIGBOX_H = 1000;
	public static final int LEFT_WIDTH = 300;
	public static final int LOG_W = 50, LOG_H = 30;
	
	public static final int BIGBOX_OFFSET = 350, BIGBOX_Y_OFFSET = 50;
	
	// buttons
	public static final int BUTTON_W = 150, BUTTON_H = 100; //150 100
	
	public static final int BUTTON1_X_OFFSET = BIGBOX_OFFSET + 10, BUTTON1_Y_OFFSET = BIGBOX_Y_OFFSET + 35;
	public static final int BUTTON2_X_OFFSET = BUTTON1_X_OFFSET + BUTTON_W + 5, BUTTON2_Y_OFFSET = BUTTON1_Y_OFFSET;
	public static final int BUTTON3_X_OFFSET = BUTTON2_X_OFFSET + BUTTON_W + 5, BUTTON3_Y_OFFSET = BUTTON2_Y_OFFSET;
	
	private boolean bigBoxActive;
	
	public GameUI(Handler handler)
	{
		this.handler = handler;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		init();
	}
	
	public void init()
	{
		page = 0;
		log = new LogText();
		// Rectangles hold the dimension info for ui
		Rectangle topBarRect = new Rectangle(0,0,handler.getWidth(),45);
		Rectangle leftBarRect = new Rectangle(0+handler.getCamera().BOUND_SIZE,0,LEFT_WIDTH,handler.getHeight());
		Rectangle messageRect = new Rectangle(handler.getWidth() - MSG_WIDTH,handler.getHeight() - MSG_HEIGHT,MSG_WIDTH,MSG_HEIGHT);
		Rectangle bigBoxRect = new Rectangle(BIGBOX_OFFSET,BIGBOX_Y_OFFSET,BIGBOX_W,BIGBOX_H);
		
		// Rectangles to hold the button info
		Rectangle button1Rect = new Rectangle(BUTTON1_X_OFFSET, BUTTON1_Y_OFFSET, BUTTON_W, BUTTON_H);
		Rectangle button2Rect = new Rectangle(BUTTON2_X_OFFSET, BUTTON2_Y_OFFSET, BUTTON_W, BUTTON_H);
		Rectangle button3Rect = new Rectangle(BUTTON3_X_OFFSET, BUTTON3_Y_OFFSET, BUTTON_W, BUTTON_H);
		
		// info at top of screen
		topBar = new UITextHolder(topBarRect, Assets.topBarImg, new Point(10, 5), false, handler, new ClickListener() {
    		@Override
    		public void onClick() {
    			// Do nothing
    		}
    	});
		// base menu for all menus
		leftBar = new UITextHolder(leftBarRect, Assets.leftBarImg, new Point(30, 5), false, handler, new ClickListener() {
    		@Override
    		public void onClick() {
    			// Do nothing
    		}
    	});
		// log/message box
		msg = new UITextHolder(messageRect, Assets.messageImg, new Point(0,0), false, handler, new ClickListener() {
    		@Override
    		public void onClick() {
    			// I don't think this works
    		}
    	});
		// holds action buttons
		bigBox = new UITextHolder(bigBoxRect, Assets.bigBoxImg, new Point(BIGBOX_OFFSET+14, BIGBOX_Y_OFFSET), false, handler, new ClickListener() {
    		@Override
    		public void onClick() {
    			// Do nothing
    		}
    	});
		// buttons
		foodButton = new UITextButton(button1Rect, Assets.buttonImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			System.out.println("clicked");
    		}
    	});
		woodButton = new UITextButton(button2Rect, Assets.buttonImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			System.out.println("clicked");
    		}
    	});
		stoneButton = new UITextButton(button3Rect, Assets.buttonImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			System.out.println("clicked");
    		}
    	});
		wallButton = new UITextButton(button1Rect, Assets.buttonImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			System.out.println("clicked");
    		}
    	});
		msg.setTextPos((int)(msg.getX() + 10), (int)(msg.getY() + 50));
		msg.setTitlePos((int)(msg.getX() + 10), (int)(msg.getY() + 28));
		// add objects and toggle off menus
		uiManager.addObject(topBar);
		uiManager.addObject(leftBar);
		uiManager.addObject(msg);
		uiManager.addObject(bigBox);
		uiManager.addObject(foodButton);
		uiManager.addObject(woodButton);
		uiManager.addObject(stoneButton);
		uiManager.addObject(wallButton);
		leftBar.setFont(Assets.tnr20);
		toggleLeft();
		toggleMsg();
		bigBoxActive = true; // toggle will turn this off
		//bigBox.setFont(Assets.tnr20);
		foodButton.setText("Train Food\nForager (1)");
		woodButton.setText("Train Wood\nCutter (2)");
		stoneButton.setText("Train Stone\nHarvester (3)");
		wallButton.setText("Build Wooden\nWalls (1)");
		toggleBigBox();
		wallButton.toggleHide();
		bigBox.setText("Production Units         use (Z) and (X) to cycle pages");
	}
	
	public void updateTopBar()
	{
		int manpower = handler.getPlayers()[Settings.PLAYER_ZERO].getManpower();
		int pop = handler.getPlayers()[Settings.PLAYER_ZERO].getPopulation();
		int food = handler.getPlayers()[Settings.PLAYER_ZERO].getFood();
		int wood = handler.getPlayers()[Settings.PLAYER_ZERO].getWood();
		int stone = handler.getPlayers()[Settings.PLAYER_ZERO].getStone();
		topBar.setText("(Q) Tile Detail, (E) Action Menu           Pop= " + pop + " * MP= "+manpower+" * Food= " + food + " * Wood= " + wood + " * Stone= " + stone);
	}
	
	public void updateLeftBar()
	{
		Tile tile = handler.getActionHandler().getSelectedTile();
		if (tile != null)
		{
			String terrain = tile.getTerrainString();
			int tileID = tile.getTileID();
			int posI = tile.getI();
			int posJ = tile.getJ();
			String t = "Terrain Type: " + terrain + "\nTile ID: " + tileID + "\nTile Coord: " + posI + ":" + posJ;
			t += "\n----------------";
			if (tile.isCityPresent())
			{
				City c = tile.getCity();
				int cityNum = c.getCityNum();
				String playerN = c.getOwningPlayer().getPlayerName();
				int pop = c.getPopulation();
				int mp = c.getManpower();
				int food = c.getFood();
				int wood = c.getWood();
				int stone = c.getStone();
				int foragers = c.getForagers();
				int woodC = c.getWoodCutters();
				int stoneH = c.getStoneHarvesters();
				int pfood = c.getPrvFood();
				int pwood = c.getPrvWood();
				int pstone = c.getPrvStone();
				t += "\nCity Name: " + cityNum + "\nOwned by: " + playerN + "\nPopulation:" + pop + "\nManpower: " + mp + "\nFood: " + food + "\nWood: " + wood + "\nStone: " + stone;
				t += "\n----------------";
				t += "\nForagers: " + foragers + "\nWood Cutters: " + woodC + "\nStone Harvesters: " + stoneH;
				t += "\n----------------";
				t += "\n---Production last month---\nFood: " + (pfood-pop) + "\nWood: " + pwood + "\nStone: " + pstone;
				t += "\n----------------\n";
				t += c.getStatus();
			}
			leftBar.setText(t);
		}
	}
	
	public void checkKeys()
	{
		// left bar toggle Q
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
				toggleLeft();
			if (handler.getGame().getTutorial().getStage() == 1)
	    		handler.getGame().getTutorial().completeStage();
		}
		
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				toggleBigBox();
			}
			if (handler.getGame().getTutorial().getStage() == 2)
	    		handler.getGame().getTutorial().completeStage();
		}
		
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_L))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				toggleMsg();
				log.goToRecent();
				msg.setText(log.viewLog());
				msg.setTitle(log.getTitle());
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				pageDown();
			}
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
			{
				pageUp();
			}
		}
		
		
	}
	
	public void update() {
		uiManager.update();
		updateTopBar();
		updateLeftBar();
		updateBigBox();
		checkKeys();
	}
	
	public void updateBigBox()
	{
		Tile tile = handler.getActionHandler().getSelectedTile();
		if (tile != null)
		{
			String t = "";
			if (tile.isCityPresent())
			{
				City c = tile.getCity();
				int cityNum = c.getCityNum();
				if (page == 0)
				{
					t +="Production Units         use (Z) and (X) to cycle pages";
				}
				else if (page == 1)
				{
					t +="Basic Structures         use (Z) and (X) to cycle pages";
				}
			}
			else
			{
				t = "You must select a tile with a city in order to perform these tasks";
			}
			bigBox.setText(t);
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(Assets.logImg, handler.getWidth() - LOG_W, handler.getHeight() - LOG_H, LOG_W, LOG_H, null);
		uiManager.render(g);
	}
	
	public void hide(int index)
	{
		uiManager.getObjects().get(index).turnOff();
	}
	
	public void hideAll()
	{
		for (int i = 0; i < uiManager.getObjects().size(); i++)
			uiManager.getObjects().get(i).turnOff();
		bigBoxActive = false;
	}
	
	public void show(int index)
	{
		uiManager.getObjects().get(index).turnOn();
	}
	
	public void showAll()
	{
		for (int i = 0; i < uiManager.getObjects().size(); i++)
			uiManager.getObjects().get(i).turnOn();
		bigBoxActive = true;
		toggleLeft();
		toggleMsg();
		toggleBigBox();
	}
	
	public void toggleHide(int index)
	{
		uiManager.getObjects().get(index).toggleHide();;
	}
	
	public void toggleAll()
	{
		for (int i = 0; i < uiManager.getObjects().size(); i++)
			uiManager.getObjects().get(i).toggleHide();
	}
	
	public void changeText(int index, String s)
	{
		if (index == MSG)
		{
			log.addMessage(s);
			msg.setText(log.viewLog());
		}
		else
			System.err.println("Error: Attempted to change text at unknown index");
	}
	
	public void previousLog()
	{
		if (log.goBackMessage())
		{
			msg.setText(log.viewLog());
			changeTitle(MSG,log.getTitle());
		}
	}
	
	public void forwardLog()
	{
		if (log.goForwardMessage())
		{
			msg.setText(log.viewLog());
			changeTitle(MSG, log.getTitle());
		}
	}
	
	public void changeTitle(int index, String s)
	{
		if (index == MSG)
			msg.setTitle(s);
		else
			System.err.println("Error: Attempted to change title at unknown index");
	}
	
	public void toggleLeft()
	{
		leftBar.toggleHide();
	}
	
	public void toggleBigBox()
	{
		bigBox.toggleHide();
		toggleButtons();
		bigBoxActive = !bigBoxActive;
	}
	
	public void pageUp()
	{
		toggleButtons();
		if (page <= 0)
		{
			page++;
		}
		else // page == 1
		{
			page = 0;
		}
	}
	
	public void pageDown()
	{
		toggleButtons();
		if (page >= 1)
		{
			page--;
		}
		else // page == 0
		{
			page = 1;
		}
	}
	
	public void toggleButtons()
	{
		foodButton.toggleHide();
		woodButton.toggleHide();
		stoneButton.toggleHide();
		if (page == 1)
			togglePage1();
	}
	
	public void togglePage1()
	{
		
	}
	
	public void toggleMsg()
	{
		msg.toggleHide();
	}
	
	public void showMsg()
	{
		msg.turnOn();
	}
	
	public void hideMsg()
	{
		msg.turnOff();
	}
	
	public UIManager getUIManager()
	{
		return uiManager;
	}
	
	public void setBounds(int xAmt, int yAmt)
	{
		uiManager.setBounds(xAmt, yAmt);
	}
	
	public boolean isBigBoxActive()
	{
		return bigBoxActive;
	}
	
	public LogText getLog()
	{
		return log;
	}
	
	public int getUIpage()
	{
		return page;
	}
}