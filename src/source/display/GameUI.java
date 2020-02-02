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
	
	public static final int TOP_BAR = 0, LEFT_BAR = 1, MSG = 2, BIG_BOX = 3;
	
	public static final int MSG_WIDTH = 500, MSG_HEIGHT = 400;
	public static final int BIGBOX_W = 1050, BIGBOX_H = 1000;
	public static final int LEFT_WIDTH = 300;
	public static final int LOG_W = 50, LOG_H = 30;
	
	public static final int BIGBOX_OFFSET = 350, BIGBOX_Y_OFFSET = 50;
	
	// buttons
	public static final int BUTTON_W = 150, BUTTON_H = 100;
	
	public static final int FOODBUTTON_X_OFFSET = BIGBOX_OFFSET + 10, FOODBUTTON_Y_OFFSET = BIGBOX_Y_OFFSET + 35;
	
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
		// Rectangles hold the dimension info for ui
		Rectangle topBarRect = new Rectangle(0,0,handler.getWidth(),45);
		Rectangle leftBarRect = new Rectangle(0+handler.getCamera().BOUND_SIZE,0,LEFT_WIDTH,handler.getHeight());
		Rectangle messageRect = new Rectangle(handler.getWidth() - MSG_WIDTH,handler.getHeight() - MSG_HEIGHT,MSG_WIDTH,MSG_HEIGHT);
		Rectangle bigBoxRect = new Rectangle(BIGBOX_OFFSET,BIGBOX_Y_OFFSET,BIGBOX_W,BIGBOX_H);
		
		// Rectangles to hold the button info
		Rectangle foodButtonRect = new Rectangle(FOODBUTTON_X_OFFSET, FOODBUTTON_Y_OFFSET, BUTTON_W, BUTTON_H);
		
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
		foodButton = new UITextButton(foodButtonRect, Assets.buttonImgs, new ClickListener() {
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
		//leftBar.setFont(Assets.tnr20);
		toggleLeft();
		toggleMsg();
		bigBoxActive = true; // toggle will turn this off
		//bigBox.setFont(Assets.tnr20);
		bigBox.setText("Food Button     Wood Button     Stone Button");
		foodButton.setText("Forage for\nFood");
		toggleBigBox();
	}
	
	public void updateTopBar()
	{
		int manpower = handler.getPlayers()[Settings.PLAYER_ZERO].getManpower();
		int pop = handler.getPlayers()[Settings.PLAYER_ZERO].getPopulation();
		int food = handler.getPlayers()[Settings.PLAYER_ZERO].getFood();
		int wood = handler.getPlayers()[Settings.PLAYER_ZERO].getWood();
		int stone = handler.getPlayers()[Settings.PLAYER_ZERO].getStone();
		topBar.setText("(Q) Tile Detail, (E) Action Menu           Pop= " + pop + " * Manpower= "+manpower+" * Food= " + food + " * Wood= " + wood + " * Stone= " + stone);
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
				t += "\nCity Name: " + cityNum + "\nOwned by: " + playerN + "\nPopulation:" + pop + "\nManpower: " + mp + "\nFood: " + food + "\nWood: " + wood + "\nStone: " + stone;
				t += "\n----------------";
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
				toggleBigBox();
			if (handler.getGame().getTutorial().getStage() == 2)
	    		handler.getGame().getTutorial().completeStage();
		}
		
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_L))
		{
			if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
				toggleMsg();
		}
	}
	
	public void update() {
		uiManager.update();
		updateTopBar();
		updateLeftBar();
		checkKeys();
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
	}
	
	public void show(int index)
	{
		uiManager.getObjects().get(index).turnOn();
	}
	
	public void showAll()
	{
		for (int i = 0; i < uiManager.getObjects().size(); i++)
			uiManager.getObjects().get(i).turnOn();
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
			msg.setText(s);
		else
			System.err.println("Error: Attempted to change text at unknown index");
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
		foodButton.toggleHide();
		bigBoxActive = !bigBoxActive;
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
}