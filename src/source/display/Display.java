package source.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	private boolean fullScreen;
	
	//protected static Dimension mapSize;
	
	public Display(String title, int width, int height, boolean fullscreen){
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullScreen = fullscreen;
		init();
	}
	
	private void init(){
		frame = new JFrame(title);
		if (fullScreen)
		{
			frame.setUndecorated(true);
		}
		else
		{
			frame.setSize(width, height);
			frame.setLocationRelativeTo(null);
		}
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.setBackground(Color.black);
		
		// make the default cursor blank
		//BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        //Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
        //canvas.setCursor(blankCursor);
		
		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public boolean isFullScreen()
	{
		return fullScreen;
	}
	
	public void setFullScreen(boolean fs)
	{
		fullScreen = fs;
	}
}
