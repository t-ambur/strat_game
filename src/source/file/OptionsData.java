package source.file;

public class OptionsData {
	
	private String path;
	private int[] data;
	private final int DATA_LENGTH = 3; // CHANGE THIS TO THE AMOUNT OF DATA TO LOAD FROM THE FILE // MAKE SURE TO UPDATE BOTH LOAD() AND WRITE()
	
	private int resWidth, resHeight;
	private boolean fullScreen;
	private boolean loadedData;
	
	public OptionsData(String p)
	{
		this.path = p;
		load();
	}
	
	private void load()
	{
		// create a FileManager object to read from the options file
		FileManager fr = new FileManager(path);
		data = fr.read();
		// make sure that there is nothing wrong with the data
		if (data.length != DATA_LENGTH)
		{
			System.err.println("Options data CORRUPT! INCORRECT DATA AMOUNT! Resetting options data. . .");
			loadedData = false;
			return;
		}
		if (data == null)
		{
			System.err.println("Unable to load from options. Data is null.");
			loadedData = false;
			return;
		}
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] < 0)
			{
				System.out.println("Options data not initialized . . .");
				loadedData = false;
				return;
			}
		}
		
		resWidth = data[0];
		resHeight = data[1];
		
		if (data[2] == 1)
			fullScreen = true;
		else if (data[2] == 0)
			fullScreen = false;
		else
		{
			System.err.println("Options data CORRUPT! INVALID SCREEN SETTING! Resetting options data. . .");
			loadedData = false;
			return;
		}
		
		loadedData = true;
	}
	
	public void write(int w, int h, boolean fs)
	{	
		FileManager fm = new FileManager(path);
		
		String[] comment = new String[DATA_LENGTH];
		int[] store = new int[DATA_LENGTH];
		
		store[0] = w;
		store[1] = h;
		if (fs)
			store[2] = 1;
		else
			store[2] = 0;
		
		comment[0] = "resolution_width=";
		comment[1] = "resolution_height=";
		comment[2] = "fullscreen=";
		
		fm.write(comment, store);
	}
	
	public String getPath()
	{
		return path;
	}
	
	public boolean loaded()
	{
		return loadedData;
	}
	
	public boolean isFullScreen()
	{
		return fullScreen;
	}
	
	public int loadedResWidth()
	{
		return resWidth;
	}
	
	public int loadedResHeight()
	{
		return resHeight;
	}
}