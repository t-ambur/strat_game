package source.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import source.main.Handler;
import source.support.Assets;
import source.support.Settings;
import source.support.Text;
import source.ui.ClickListener;
import source.ui.UIImageButton;
import source.ui.UIManager;

public class MenuState extends State {
	// The manager will hold all the buttons
	private UIManager uiManager;
    // layout coordinate constants
	private final int CENTERED_W; // "centered"
	private final int BUTTON_W = 338; // painted button width
	private final int BUTTON_H = 95; // painted button height
	private final int BUTTON_TINY_W = 100;
	// These constants are to help in accessing array elements
    private final int NEW = 0, EXIT_OPTIONS = 3, OPT_DISPLAY = 4;
    //private final int CONTINUE = 1, OPTIONS = 2
    private final int RETURN = 5, SAVE = 6, RES_PLUS = 7, RES_MINUS = 8, FULLSCREEN = 9, WINDOWED = 10;
    // this constant is used for toggling a large number of buttons at once
    private final int MAX_OPT_TOGGLE = 4;
    
    // for keeping track of rendering some images
    private enum MENU_POSITION { TITLE, OPTIONS, DISPLAY }
    private MENU_POSITION menuPosition;
    // render coordinate variables for images and the images they refer to
    private BufferedImage resolution;
    private final int RES_X, RES_Y;
    private final int DISP_MSG_X = 5, DISP_MSG_Y = 40;
    private final int TITLE_X, TITLE_Y; 
    
    // FOR CUSTOM RESOLUTION
    private boolean renderRes = false;
    
    // mouse location variables for rendering mouse cursor
    private int mx;
    private int my;

	public MenuState(Handler handler) {
		super(handler);
		// These are just the settings for where to render images (NOT BUTTONS) to the screen
		CENTERED_W = handler.getWidth()/2-200;
		RES_X = CENTERED_W + 250;
		RES_Y = handler.getHeight()/2 - 110;
		TITLE_X = CENTERED_W + 15;
		TITLE_Y = 300;
		
		init();
	}
	
	public void init()
	{
		// we don't want to render all the images and text associated with certain menus at start
		menuPosition = MENU_POSITION.TITLE;
		// mouse positions used to paint cursor to screen
		mx = handler.getMouseManager().getMouseX();
		my = handler.getMouseManager().getMouseY();
		// UIManager holds all the buttons for the menu
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		// These rectangles define the size and position of each button
		// Title Menu Rectangles
    	Rectangle newGRect = new Rectangle(CENTERED_W, handler.getHeight()/2 + 0, BUTTON_W, BUTTON_H);
    	Rectangle conGRect = new Rectangle(CENTERED_W, handler.getHeight()/2 + 100, BUTTON_W, BUTTON_H);
    	Rectangle optRect = new Rectangle(CENTERED_W, handler.getHeight()/2 + 200, BUTTON_W, BUTTON_H);
    	Rectangle exitOptRect = new Rectangle(CENTERED_W, handler.getHeight()/2 + 300, BUTTON_W, BUTTON_H);
    	// Opt Menu Rectangles
    	Rectangle optDisplayRect = new Rectangle(CENTERED_W, handler.getHeight()/2 - 300, BUTTON_W, BUTTON_H);
    	Rectangle returnRect = new Rectangle((int)(exitOptRect.getX() - BUTTON_W - 50), handler.getHeight()/2 + 300, BUTTON_W, BUTTON_H);
    	Rectangle saveRect = new Rectangle((int)(exitOptRect.getX() + BUTTON_W + 50), handler.getHeight()/2 + 300, BUTTON_W, BUTTON_H);
    	Rectangle plusRect = new Rectangle(RES_X + BUTTON_W + 10, RES_Y, BUTTON_TINY_W, BUTTON_H);
    	Rectangle minusRect = new Rectangle(RES_X - BUTTON_TINY_W - 10, RES_Y, BUTTON_TINY_W, BUTTON_H);
    	Rectangle screenSettingFullRect = new Rectangle(CENTERED_W + 200, handler.getHeight()/2, BUTTON_W, BUTTON_H);
    	Rectangle screenSettingWindowRect = new Rectangle(CENTERED_W - 200, handler.getHeight()/2, BUTTON_W, BUTTON_H);
    	
    	// The actual button objects. Takes in the rectangle bounds, the image array and the action required on click
    	// 0 NEW GAME
    	uiManager.addObject(new UIImageButton(newGRect, Assets.newGImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			handler.getClock().start();
    			State.setState(handler.getGame().gameState);
    		}
    	}));
    	// 1 CONTINUE GAME
    	uiManager.addObject(new UIImageButton(conGRect, Assets.conGImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			// TO BE IMPLEMENTED
    		}
    	}));
    	// 2 OPTIONS
    	uiManager.addObject(new UIImageButton(optRect, Assets.optImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			for (int i = NEW; i <= MAX_OPT_TOGGLE; i++)
    				uiManager.getObjects().get(i).toggleHide();
    			menuPosition = MENU_POSITION.OPTIONS;
    		}
    	}));
    	// 3 EXIT OPTIONS
    	uiManager.addObject(new UIImageButton(exitOptRect, Assets.exitOptImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			for (int i = NEW; i <= MAX_OPT_TOGGLE; i++)
    				uiManager.getObjects().get(i).toggleHide();
    			menuPosition = MENU_POSITION.TITLE;
    		}
    	}));
    	// 4 DISPLAY
    	uiManager.addObject(new UIImageButton(optDisplayRect, Assets.optDisplayImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			for (int i = EXIT_OPTIONS; i <= RES_MINUS; i++)
    				uiManager.getObjects().get(i).toggleHide();
    			menuPosition = MENU_POSITION.DISPLAY;
    			
    			if (Settings.isFullScreen())
    				uiManager.getObjects().get(FULLSCREEN).toggleHide();
    			else
    				uiManager.getObjects().get(WINDOWED).toggleHide();
    			
    			if (Settings.getCurrentResolutionIndex() == Settings.getNumberResolutions())
    				renderRes = true;
    			else
    				renderRes = false;
    		}
    	}));
    	// 5 RETURN (DISPLAY SETTINGS)
    	uiManager.addObject(new UIImageButton(returnRect, Assets.returnImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			for (int i = EXIT_OPTIONS; i <= RES_MINUS; i++)
    				uiManager.getObjects().get(i).toggleHide();
    			
    			uiManager.getObjects().get(FULLSCREEN).turnOff();
    			uiManager.getObjects().get(WINDOWED).turnOff();
    			menuPosition = MENU_POSITION.OPTIONS;
    			
    			Settings.resetSizeChange();
    			Settings.resetResToggled();
    			Settings.returnRes();
    			resolution = Settings.getResImage();
    			renderRes = false;
    		}
    	}));
    	// 6 SAVE (DISPLAY SETTINGS) // MAYBE CHANGE THIS TO APPLY
    	uiManager.addObject(new UIImageButton(saveRect, Assets.saveImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			
    			int w = handler.getWidth();
    			int h = handler.getHeight();
    			
    			boolean changed = false;
    			if (Settings.resToggled())
    			{
    				w = (int)Settings.getNewRes().getWidth();
    				h = (int)Settings.getNewRes().getHeight();
    				changed = true;
    			}
    			if (Settings.sizeChanged())
    			{
    				Settings.toggleSize();
    				changed = true;
    			}
    			
    			if (changed)
    			{
    				handler.getOptData().write(w, h, Settings.isFullScreen());
    				handler.getGame().reset();
    			}
    			
    			for (int i = EXIT_OPTIONS; i <= RES_MINUS; i++)
    				uiManager.getObjects().get(i).toggleHide();
    			
    			uiManager.getObjects().get(FULLSCREEN).turnOff();
    			uiManager.getObjects().get(WINDOWED).turnOff();
    			menuPosition = MENU_POSITION.OPTIONS;
    			renderRes = false;
    		}
    	}));
    	// 7 RESOLUTION PLUS
    	uiManager.addObject(new UIImageButton(plusRect, Assets.plusImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			if (Settings.canIncreaseRes())
    			{
	    			Settings.increaseRes();
	    			resolution = Settings.getResImage();
	    			
	    			if (Settings.getCurrentResolutionIndex() == Settings.getNumberResolutions())
	    				renderRes = true;
	    			else
	    				renderRes = false;
    			}
    		}
    	}));
    	// 8 RESOLUTION MINUS
    	uiManager.addObject(new UIImageButton(minusRect, Assets.minusImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			if (Settings.canDecreaseRes())
    			{
	    			Settings.decreaseRes();
	    			resolution = Settings.getResImage();
	    			
	    			if (Settings.getCurrentResolutionIndex() == Settings.getNumberResolutions())
	    				renderRes = true;
	    			else
	    				renderRes = false;
    			}
    		}
    	}));
    	// 9 FULLSCREEN BUTTON
    	uiManager.addObject(new UIImageButton(screenSettingFullRect, Assets.fullscreenImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			Settings.toggleSizeChanged();
    			uiManager.getObjects().get(FULLSCREEN).turnOff();
    			uiManager.getObjects().get(WINDOWED).turnOn();
    		}
    	}));
    	// 10 WINDOWED BUTTON
    	uiManager.addObject(new UIImageButton(screenSettingWindowRect, Assets.windowedImgs, new ClickListener() {
    		@Override
    		public void onClick() {
    			Settings.toggleSizeChanged();
    			uiManager.getObjects().get(FULLSCREEN).turnOn();
    			uiManager.getObjects().get(WINDOWED).turnOff(); 
    		}
    	}));
    	// Turns off the buttons that aren't used at menu start
    	uiManager.getObjects().get(EXIT_OPTIONS).toggleHide();
    	uiManager.getObjects().get(OPT_DISPLAY).toggleHide();
    	uiManager.getObjects().get(RETURN).toggleHide();
    	uiManager.getObjects().get(SAVE).toggleHide();
    	uiManager.getObjects().get(RES_PLUS).toggleHide();
    	uiManager.getObjects().get(RES_MINUS).toggleHide();
    	uiManager.getObjects().get(FULLSCREEN).toggleHide();
    	uiManager.getObjects().get(WINDOWED).toggleHide();
    	
    	// initialize images
    	resolution = Settings.getResImage();
	}

	@Override
	public void update() {
		mx = handler.getMouseManager().getMouseX();
		my = handler.getMouseManager().getMouseY();
		
		if (handler.getKeyManager().esc)
			System.exit(0);
		
		uiManager.update();
	}

	@Override
	public void render(Graphics2D g) {
		// render background image and title
		g.drawImage(Assets.menuBackgroundImg, 0, 0, handler.getWidth(), handler.getHeight(), null);
		// render text and images and then all the buttons and then the mouse cursor
		if (menuPosition == MENU_POSITION.TITLE)
		{
			Text.drawString(g, "GREECE", TITLE_X, TITLE_Y, false, Color.WHITE, Assets.tnr72);
		}
		else if (menuPosition == MENU_POSITION.OPTIONS)
		{
			
		}
		else if (menuPosition == MENU_POSITION.DISPLAY)
		{
			g.drawImage(resolution, RES_X, RES_Y, null);
			Text.drawString(g, "Saving any changes to display settings", DISP_MSG_X, DISP_MSG_Y, false, Color.red, Assets.tnr48);
			Text.drawString(g, "will cause the game to close", DISP_MSG_X+10, DISP_MSG_Y*2+10, false, Color.red, Assets.tnr48);
			Text.drawString(g, "Launch the game again to start", handler.getWidth()/2 + handler.getWidth()/6-10, DISP_MSG_Y, false, Color.black, Assets.tnr48);
			Text.drawString(g, "with new display settings.", handler.getWidth()/2 + handler.getWidth()/6, DISP_MSG_Y*2+10, false, Color.black, Assets.tnr48);
			Text.drawString(g, "Screen Size (Resolution):", RES_X - BUTTON_W*2, RES_Y + 50, false, Color.BLACK, Assets.tnr48);
			if (renderRes)
				Text.drawString(g, handler.getWidth()+"x"+handler.getHeight(), RES_X + 160, RES_Y + 50, true, Color.BLACK, Assets.tnr48);
		}
		uiManager.render(g);
        g.drawImage(Assets.cursorImg, mx - Settings.cursorImgMiddleWidth, my - Settings.cursorImgMiddleHeight, null);
	}
}