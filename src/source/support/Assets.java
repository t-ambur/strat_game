package source.support;

import java.awt.Font;
import java.awt.image.BufferedImage;

// Assets contains all the resource files that need to be imported, images, sounds, etc.
public class Assets {
	
	// font variables (name of font / size)
	public static Font tnr20;
	public static Font tnr24;
	public static Font tnr48;
	public static Font tnr72;
	
	// Main menu Images (buttons and background) MenuState
    public static BufferedImage menuBackgroundImg;
    // UI Buttons Menu
    public static BufferedImage[] newGImgs;
    public static BufferedImage[] conGImgs;
    public static BufferedImage[] optImgs;
    public static BufferedImage[] exitOptImgs;
    public static BufferedImage[] optDisplayImgs;
    public static BufferedImage[] returnImgs;
    public static BufferedImage[] saveImgs;
    public static BufferedImage[] plusImgs;
    public static BufferedImage[] minusImgs;
    public static BufferedImage[] fullscreenImgs;
    public static BufferedImage[] windowedImgs;
    
    public static BufferedImage blankButtonImg;
    public static BufferedImage res1920;
    public static BufferedImage res1336;
    public static BufferedImage res1280;
    public static BufferedImage res1280_800;
    public static BufferedImage res1024;
    
    // GameState Images
    public static BufferedImage cursorImg;
    
    // Tiles
    public static BufferedImage tileSelectorImg;
    public static BufferedImage tilePlainsImg;
    public static BufferedImage tileOceanImg;
    public static BufferedImage tileBeachImg;
    public static BufferedImage tileForestImg;
    public static BufferedImage tileSwampImg;
    public static BufferedImage tileMountainImg;
    public static BufferedImage tileLakeImg;
    public static BufferedImage tileDesertImg;
    public static BufferedImage tileSnowImg;
    
    public static BufferedImage tileErrorImg;
    
    // On top of tiles
    public static BufferedImage campImg;
    
    // UI
    public static BufferedImage topBarImg;
    public static BufferedImage leftBarImg;
    public static BufferedImage messageImg;
    public static BufferedImage logImg;
    
	public static void init(){
		
		// Load main menu images
		// background image
		menuBackgroundImg = ImageLoader.loadImage("/images/menu.jpg");
		// buttons
        newGImgs = new BufferedImage[2];
        conGImgs = new BufferedImage[2];
        optImgs = new BufferedImage[2];
        exitOptImgs = new BufferedImage[2];
        optDisplayImgs = new BufferedImage[2];
        returnImgs = new BufferedImage[2];
        saveImgs = new BufferedImage[2];
        plusImgs = new BufferedImage[2];
        minusImgs = new BufferedImage[2];
        fullscreenImgs = new BufferedImage[2];
        windowedImgs = new BufferedImage[2];
        
        newGImgs[0] = ImageLoader.loadImage("/button/nbutton.png");
        newGImgs[1] = ImageLoader.loadImage("/button/nbuttonh.png");
        conGImgs[0] = ImageLoader.loadImage("/button/cbutton.png");
        conGImgs[1] = ImageLoader.loadImage("/button/cbuttonhu.png");
        optImgs[0] = ImageLoader.loadImage("/button/obutton.png");
        optImgs[1] = ImageLoader.loadImage("/button/obuttonhu.png");
        exitOptImgs[0] = ImageLoader.loadImage("/button/exitoptionb.png");
        exitOptImgs[1] = ImageLoader.loadImage("/button/exitoptionbhu.png");
        optDisplayImgs[0] = ImageLoader.loadImage("/button/display.png");
        optDisplayImgs[1] = ImageLoader.loadImage("/button/displayh.png");
        returnImgs[0] = ImageLoader.loadImage("/button/returnbutton.png");
        returnImgs[1] = ImageLoader.loadImage("/button/returnbuttonh.png");
        saveImgs[0] = ImageLoader.loadImage("/button/savebutton.png");
        saveImgs[1] = ImageLoader.loadImage("/button/savebuttonh.png");
        minusImgs[0] = ImageLoader.loadImage("/button/minus.png");
        minusImgs[1] = ImageLoader.loadImage("/button/minusH.png");
        plusImgs[0] = ImageLoader.loadImage("/button/plus.png");
        plusImgs[1] = ImageLoader.loadImage("/button/plusH.png");
        fullscreenImgs[0] = ImageLoader.loadImage("/button/fullscreen.png");
        fullscreenImgs[1] = ImageLoader.loadImage("/button/fullscreenh.png");
        windowedImgs[0] = ImageLoader.loadImage("/button/windowed.png");
        windowedImgs[1] = ImageLoader.loadImage("/button/windowedh.png");
        
        blankButtonImg = ImageLoader.loadImage("/button/blankbutton.png");

        
        res1920 = ImageLoader.loadImage("/text/res1920.png");
        res1336 = ImageLoader.loadImage("/text/res1336.png");
        res1280 = ImageLoader.loadImage("/text/res1280.png");
        res1280_800 = ImageLoader.loadImage("/text/res1280_800.png");
        res1024 = ImageLoader.loadImage("/text/res1024.png");
        
        // game images
        cursorImg = ImageLoader.loadImage("/images/cursor.png");
        
        // tiles
        tileSelectorImg = ImageLoader.loadImage("/images/tileSelector2.png");
        tilePlainsImg = ImageLoader.loadImage("/images/tile00t2.png");
        tileOceanImg = ImageLoader.loadImage("/images/tile01t.png");
        tileBeachImg = ImageLoader.loadImage("/images/tile02t.png");
        tileForestImg = ImageLoader.loadImage("/images/tile03t.png");
        tileSwampImg = ImageLoader.loadImage("/images/tile04t.png");
        tileMountainImg = ImageLoader.loadImage("/images/tile05t3.png");
        tileLakeImg = ImageLoader.loadImage("/images/tile06t.png");
        tileDesertImg = ImageLoader.loadImage("/images/tile07t.png");
        tileSnowImg = ImageLoader.loadImage("/images/tile08t.png");
        
        tileErrorImg = ImageLoader.loadImage("/images/loadErrorTile.png");
        
        // on top of tiles
        campImg = ImageLoader.loadImage("/images/camp.png");
        
        // in-game ui
        topBarImg = ImageLoader.loadImage("/ui/topBar.png");
        leftBarImg = ImageLoader.loadImage("/ui/leftBar.png");
        messageImg = ImageLoader.loadImage("/ui/message.png");
        logImg = ImageLoader.loadImage("/ui/log.png");
		
        tnr20 = FontLoader.loadFont("resources/text/tnr.ttf", 20);
        tnr24 = FontLoader.loadFont("resources/text/tnr.ttf", 24);
		tnr48 = FontLoader.loadFont("resources/text/tnr.ttf", 48);
		tnr72 = FontLoader.loadFont("resources/text/tnr.ttf", 72);
		
		/*SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		
		player_down = new BufferedImage[2];
		player_up = new BufferedImage[2];
		player_left = new BufferedImage[2];
		player_right = new BufferedImage[2];
		
		player_down[0] = sheet.crop(width * 4, 0, width, height);
		player_down[1] = sheet.crop(width * 5, 0, width, height);
		player_up[0] = sheet.crop(width * 6, 0, width, height);
		player_up[1] = sheet.crop(width * 7, 0, width, height);
		player_right[0] = sheet.crop(width * 4, height, width, height);
		player_right[1] = sheet.crop(width * 5, height, width, height);
		player_left[0] = sheet.crop(width * 6, height, width, height);
		player_left[1] = sheet.crop(width * 7, height, width, height);
		*/
	}
}
