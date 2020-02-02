package source.states;

import source.main.Handler;
import source.display.GameUI;

public class Tutorial {
	
	private int stage;
	private final int MAX_STAGE = 2;
	private boolean tutorialComplete;
	private boolean stageComplete;
	public final boolean ENABLED;
	
	private GameUI ui;
	
	Handler handler;
	
	public Tutorial(Handler handler, boolean use)
	{
		this.handler = handler;
		stage = 0;
		tutorialComplete = false;
		stageComplete = false;
		
		ENABLED = use;
	}
	
	public void setUI(GameUI gui)
	{
		this.ui = gui;
		checkTutorial();
	}
	
	public void increaseStage()
	{
		stage++;
		checkTutorial();
	}
	
	public void completeStage()
	{
		stageComplete = true;
	}
	
	public int getStage()
	{
		return stage;
	}
	
	public boolean isCompleted()
	{
		return tutorialComplete;
	}
	
	public boolean isStageComplete()
	{
		return stageComplete;
	}
	
	public void checkStage()
	{
		if (stageComplete)
		{
			increaseStage();
			stageComplete = false;
			
			if (stage >= MAX_STAGE)
				tutorialComplete = true;
		}
	}
	
	public void checkTutorial()
	{
		if (stage == 0)
		{
			String firstMsg = "You can toggle this log with the \"L\" key\nYou can move the camera with WASD\n or the mouse\nZooming will turn off the UI" +
					"\nZoom with the scrollwheel or PG UP/DOWN\nIf you get lost, press HOME to center on the\n map\nYou can also press the middle mouse button to\n reset zoom" +
							"\nClick a tile to start a city on.";
			ui.toggleMsg();
			ui.changeTitle(GameUI.MSG, "Tutorial");
			ui.changeText(GameUI.MSG, firstMsg);
		}
		else if (stage == 1)
		{
			ui.changeText(GameUI.MSG, "Press \"Q\" to open menu.");
		}
	}
}