package source.states;

import source.main.Handler;
import source.display.GameUI;

public class Tutorial {
	
	private int stage;
	private final int MAX_STAGE = 10;
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
		stageComplete = false;
		checkTutorial();
	}
	
	public void completeStage()
	{
		stageComplete = true;
		//System.out.println(stage + " complete");
		increaseStage();
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
			ui.changeText(GameUI.MSG, "Notice your empire's resources and the\ncurrent date on the top bar.\nYour population will consume an equal\namount of food at the end of each month.\nPress \"Q\" to open menu.");
		}
		else if (stage == 2)
		{
			ui.changeText(GameUI.MSG, "Basic tile details are shown\n" + 
					"Various details for cities are also displayed.\nNotice you have un-used manpower.\nThis means you have unemployed citizens.\nPress E to open the task menu.");
		}
		else if (stage == 3)
		{
			ui.changeText(GameUI.MSG, "This menu displays actions you can perform\n" + 
					"Give your citizens something to do.\nYou currently have no wood or stone\nproduction.");
		}
	}
}