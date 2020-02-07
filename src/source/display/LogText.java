package source.display;

public class LogText {
	
	private String[] messages;
	private int viewing;
	private int stored;
	
	public static final int LOG_LENGTH = 100;
	
	public LogText()
	{
		viewing = 0;
		messages = new String[LOG_LENGTH];
	}
	
	public void goToRecent()
	{
		if (stored > 0)
			viewing = stored-1;
		else
			viewing = LOG_LENGTH-1;
	}
	
	public String getTitle()
	{
		return "Log " + (viewing+1);
	}
	
	public void addMessage(String msg)
	{
		if (stored >= messages.length)
			stored = 0;
		this.messages[stored] = msg;
		viewing = stored;
		stored++;
	}
	
	public String viewLog()
	{
		if (blankMessage())
		{
			return "End of Log";
		}
		return messages[viewing];
	}
	
	public boolean goBackMessage()
	{
		if (viewing > 0)
		{
			viewing--;
			return true;
		}
		return false;
	}
	
	public boolean goForwardMessage()
	{
		if (viewing < LOG_LENGTH-1)
		{
			viewing++;
			return true;
		}
		return false;
	}
	
	public boolean blankMessage()
	{
		if (messages[viewing] == null || messages[viewing] == "")
		{
			return true;
		}
		return false;
	}
}
