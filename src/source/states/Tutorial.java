package source.states;

import source.main.Handler;

public class Tutorial {
	
	private int stage;
	private final int MAX_STAGE = 2;
	private boolean tutorialComplete;
	private boolean stageComplete;
	public final boolean ENABLED;
	
	Handler handler;
	
	public Tutorial(Handler handler, boolean use)
	{
		this.handler = handler;
		stage = 0;
		tutorialComplete = false;
		stageComplete = false;
		
		ENABLED = use;
	}
	
	public void increaseStage()
	{
		stage++;
		
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
}