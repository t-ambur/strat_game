package source.states;

import source.main.Handler;
import source.display.GameUI;

public class Tutorial {
	
	private int stage;
	private final int MAX_STAGE = 6; // UPDATE THIS WHEN ADDING NEW TUTORIAL MESSAGES
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
		if (stage <= this.MAX_STAGE)
			ui.changeTitle(GameUI.MSG, "Tutorial");
		if (stage == 0)
		{
			String firstMsg = "You can toggle this log with the \"L\" key\nYou can move the camera with WASD\n or the mouse\nZooming will turn off the UI" +
					"\nZoom with the scrollwheel or PG UP/DOWN\nIf you get lost, press HOME to center on the map\nYou can also press the middle mouse button to reset\nzoom. " +
							"\nThe world map contains several terrain types.\nIt is recommended you start on a plains or forest file.\nClick a tile to start a city on."
					+ "\nThe light green tiles are plains.\nDarker green tiles for forests.\nDifferent terrains give different bonuses to cities.";
			ui.toggleMsg();
			ui.changeTitle(GameUI.MSG, "Tutorial");
			ui.changeText(GameUI.MSG, firstMsg);
		}
		else if (stage == 1)
		{
			ui.changeText(GameUI.MSG, "Notice your empire's resources and the\ncurrent date on the top bar.\nYour population will consume an equal\namount of food at the end of each month." +
		"\nPeople will starve and die if you don't feed them.\nPress \"Q\" to open menu.");
		}
		else if (stage == 2)
		{
			ui.changeText(GameUI.MSG, "Basic tile details are shown\n" + 
					"Various details for cities are also displayed.\nNotice you have un-used manpower.\nThis means you have unemployed citizens.\nPress E to open the task menu.");
		}
		else if (stage == 3)
		{
			ui.changeText(GameUI.MSG, "This menu displays actions you can perform\n" + 
					"Give your citizens something to do.\nYou currently have no production of anything.\nThe number of each type of worker you have\n appears on the left bar (Q)." +
					"\nForagers search for food every month.\nThey are more succesful on tiles such as plains and\n forests. Train a forager by pressing (1)");
		}
		else if (stage == 4)
		{
			ui.changeText(GameUI.MSG, "Some actions are instant.\nOther actions take time to produce.\nThe game time is displayed in the upper right\n" + 
					"You can pause the game with (Space Bar).\nIncrease time with (+)\nSlow Time with (-)\nUn-Pause the game now.\n");
		}
		else if (stage == 5)
		{
			ui.changeText(GameUI.MSG, "Close your action window with (E).\nTry clicking on another tile.\nIf you get tired of clicking, you can" + 
					"\nuse (F1) to cycle through your cities.\nPress (F1) to select your first city.");
		}
		else if (stage == MAX_STAGE)
		{
			ui.changeText(GameUI.MSG, "This is the end of the tutorial.\nGood Luck!\nRemember, (L) toggles this log.\nYou can use (O) and (P) to cycle through log messages." + 
					"");
		}
	}
}