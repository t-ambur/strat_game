package source.support;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

// Settings contains some specific information about the game
public final class Settings {
	
	// Mouse cursor
	public static int cursorImgMiddleWidth;
	public static int cursorImgMiddleHeight;
	// Stored Resolutions
	public static final Dimension res1920x1080 = new Dimension(1920,1080);
	public static final Dimension res1336x768 = new Dimension(1336,768);
	public static final Dimension res1280x720 = new Dimension(1280,720);
	public static final Dimension res1280x800 = new Dimension(1280,800);
	public static final Dimension res1024x768 = new Dimension(1024,768);
	// variables for determining whether setings need to change or what the current settings are
	private static Dimension applyNewRes = null;
	private static Dimension customResolution = null;
	public final static int NOT_SET = -1;
	public final static int CUSTOM = 5;
	private static int storedResolutions = NOT_SET;
	private static int currentResolution = CUSTOM;
	private static int resBeforeChange = NOT_SET;
	private static boolean resToggled = false;
	private static boolean sizeChanged = false;
	
	private static boolean fullScreen;
	
	public static final int NUMBER_PLAYERS = 3;
	public static final int PLAYER_ZERO = 0;
	
	private Settings() { }
	
	public static void init(int screenWidth, int screenHeight, boolean fs)
	{
		fullScreen = fs;
		Dimension[] resolutions = getResolutions();
		storedResolutions = resolutions.length-1;
		
		customResolution = new Dimension(screenWidth, screenHeight);
		for (int i = 0; i < storedResolutions; i++) // minus 1 to exclude the custom resolution
		{
			if (resolutions[i].equals(customResolution))
			{
				currentResolution = i;
				resBeforeChange = currentResolution;
			}
		}
		
		cursorImgMiddleWidth = Assets.cursorImg.getWidth() / 2;
		cursorImgMiddleHeight = Assets.cursorImg.getHeight() / 2;
	}
	
	public static Dimension[] getResolutions()
	{
		Dimension[] resolutions = new Dimension[6];
		
		resolutions[5] = customResolution;
		resolutions[4] = res1920x1080;
		resolutions[3] = res1336x768;
		resolutions[2] = res1280x800;
		resolutions[1] = res1280x720;
		resolutions[0] = res1024x768;
		
		return resolutions;
	}
	
	public static boolean hasDimensionPreset(Dimension dim)
	{
		Dimension[] resolutions = getResolutions();
		
		for (int i = 0; i < storedResolutions; i++)
		{
			if (resolutions[i].equals(dim))
				return true;
		}
		
		return false;
	}
	
	public static BufferedImage findResImage(Dimension dim)
	{
		if (hasDimensionPreset(dim))
		{
			if (dim.equals(res1920x1080))
				return Assets.res1920;
			else if (dim.equals(res1336x768))
				return Assets.res1336;
			else if (dim.equals(res1280x720))
				return Assets.res1280;
			else if (dim.equals(res1280x800))
				return Assets.res1280_800;
			else if (dim.equals(res1024x768))
				return Assets.res1024;
		}
		
		return null;
	}
	
	public static BufferedImage getResImage()
	{
		if (currentResolution >= 0)
		{
			Dimension[] resolutions = getResolutions();
			for (int i = 0; i < storedResolutions; i++)
			{
				if (i == currentResolution)
					return findResImage(resolutions[i]);
			}
		}
		
		return Assets.blankButtonImg;
	}
	
	public static int getCurrentResolutionIndex()
	{
		return currentResolution;
	}
	
	public static int getResIndexBeforeChange()
	{
		return resBeforeChange;
	}
	
	public static int getNumberResolutions()
	{
		return storedResolutions;
	}
	
	public static boolean canIncreaseRes()
	{
		if (currentResolution < storedResolutions)
			return true;
		else
			return false;
	}
	
	public static boolean canDecreaseRes()
	{
		if (currentResolution > 0)
			return true;
		else
			return false;
	}
	
	public static void increaseRes()
	{
		if (canIncreaseRes())
		{
			if (currentResolution == getResolutions().length)
			{
				currentResolution = CUSTOM; // -2
				applyNewRes = customResolution;
			}
			else
			{
				currentResolution++;
				applyNewRes = getResolutions()[currentResolution];	
			}
			
			resToggled = true;
		}
	}
	
	public static void decreaseRes()
	{
		if (canDecreaseRes())
		{
			currentResolution--;
			applyNewRes = getResolutions()[currentResolution];
			resToggled = true;
		}
	}
	
	public static void toggleSizeChanged()
	{
		sizeChanged = !sizeChanged;
	}
	
	public static void resetSizeChange()
	{
		sizeChanged = false;
	}
	
	public static void resetResToggled()
	{
		resToggled = false;
	}
	
	public static Dimension getNewRes()
	{
		return applyNewRes;
	}
	
	public static boolean isCustomRes()
	{
		return currentResolution == CUSTOM;
	}
	
	public static boolean resToggled() {
		return resToggled;
	}
	
	/*public static boolean resChanged()
	{
		return (currentResolution == resBeforeChange);
	}*/
	
	public static boolean sizeChanged()
	{
		return sizeChanged;
	}
	
	public static boolean isFullScreen()
	{
		return fullScreen;
	}
	
	public static void toggleSize()
	{
		fullScreen = !fullScreen;
	}
	
	public static void returnRes()
	{
		currentResolution = resBeforeChange;
	}
}