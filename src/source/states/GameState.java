package source.states;

import java.awt.Graphics2D;

import source.display.GameUI;
import source.display.World;
import source.file.FileData;
import source.main.Handler;
import source.support.Assets;
import source.support.Settings;
import source.support.Text;
import source.ui.UITextHolder;

public class GameState extends State {
	
	private World world;
	// The UI that appears above the map
	private GameUI gameUI;
	
	// Mouse positions
	private double vMx;
	private double vMy;
	
	public GameState(Handler handler){
		super(handler);
		gameUI = new GameUI(handler);
		world = new World(handler, FileData.GREECE, gameUI);
		handler.setWorld(world);
		
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		init();
	}
	
	public void init()
	{
		if (handler.getGame().TUT_ON)
		{
			String firstMsg = "Click a tile to start a city on.\nYou can toggle this log with the \"L\" key\nYou can move the camera with WASD\n or the mouse\nZooming will turn off the UI";
			firstMsg += "\nZoom with the scrollwheel or PG UP/DOWN\nIf you get lost, press HOME to center on the\n map\nYou can also press the middle mouse button to\n reset zoom";
			gameUI.toggleMsg();
			gameUI.changeTitle(GameUI.MSG, "Tutorial");
			gameUI.changeText(GameUI.MSG, firstMsg);
		}
	}
	
	@Override
	public void update() {
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		
		if (handler.getKeyManager().esc)
			System.exit(0);
		
		world.update();
		gameUI.update();
		// if the tutorial is enabled
		if (handler.getGame().getTutorial().ENABLED)
		{
			if (handler.getGame().getTutorial().isStageComplete())
			{
				handler.getGame().getTutorial().checkStage();
				checkTutRequirement();
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		world.render(g);
		gameUI.render(g);
		// the clock
		Text.drawString(g, handler.getClock().toStringGame(), handler.getWidth() - 310, 34, false, UITextHolder.DEFAULT_COLOR, UITextHolder.DEFAULT_FONT);
		// RENDER THE MOUSE CURSOR LAST
        g.drawImage(Assets.cursorImg, (int) ((vMx - Settings.cursorImgMiddleWidth) - handler.getCamera().getxOffset()),
        		(int) (vMy - Settings.cursorImgMiddleHeight - handler.getCamera().getyOffset()), null);
	}
	
	public void checkTutRequirement()
	{
		int stage = handler.getGame().getTutorial().getStage();
		
		if (stage == 1)
			gameUI.changeText(GameUI.MSG, "Press \"Q\" to open menu.");
	}
}
