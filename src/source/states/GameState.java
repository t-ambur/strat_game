package source.states;

import java.awt.Graphics2D;

import source.display.GameUI;
import source.display.OnTop;
import source.display.World;
import source.file.FileData;
import source.main.Handler;
import source.support.Assets;
import source.support.Settings;
import source.support.Text;
import source.ui.UITextHolder;

public class GameState extends State {
	
	private World world;
	private OnTop onTop;
	// The UI that appears above the map
	private GameUI gameUI;
	
	// Mouse positions
	private double vMx;
	private double vMy;
	
	public GameState(Handler handler){
		super(handler);
		gameUI = new GameUI(handler);
		world = new World(handler, FileData.GREECE, gameUI);
		onTop = new OnTop(handler);
		handler.setWorld(world);
		
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		init();
	}
	
	public void init()
	{
		if (handler.getGame().TUT_ON)
		{
			handler.getGame().getTutorial().setUI(gameUI);
		}
	}
	
	@Override
	public void update() {
		vMx = handler.getMouseManager().getVMouseX();
		vMy = handler.getMouseManager().getVMouseY();
		
		if (handler.getKeyManager().esc)
			System.exit(0);
		
		world.update();
		onTop.update();
		gameUI.update();
		handler.getActionHandler().update();
	}

	@Override
	public void render(Graphics2D g) {
		world.render(g);
		onTop.render(g);
		gameUI.render(g);
		// the clock
		Text.drawString(g, handler.getClock().toStringGame(), handler.getWidth() - 330, 34, false, UITextHolder.DEFAULT_COLOR, Assets.tnr20);
		// RENDER THE MOUSE CURSOR LAST
        g.drawImage(Assets.cursorImg, (int) ((vMx - Settings.cursorImgMiddleWidth) - handler.getCamera().getxOffset()),
        		(int) (vMy - Settings.cursorImgMiddleHeight - handler.getCamera().getyOffset()), null);
	}
}
