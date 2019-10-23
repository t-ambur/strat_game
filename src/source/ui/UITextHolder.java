package source.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import source.main.Handler;
import source.support.Assets;
import source.support.Text;

public class UITextHolder extends UIObject {
	
	private BufferedImage img;
	private ClickListener clicker;
	
	private String text;
	private int textX;
	private int textY;
	private boolean centered;
	
	private boolean hasTitle;
	private String title;
	private int titleX;
	private int titleY;
	private boolean tCentered;
	
	public static final Color DEFAULT_COLOR = Color.black;
	private static Color color;
	public static final Font DEFAULT_FONT = Assets.tnr24;
	private static Font font;
	
	public UITextHolder(Rectangle rect, BufferedImage img, Handler handle, ClickListener click)
	{
		super(rect, handle);
		this.img = img;
		this.clicker = click;
		
		text = "";
		this.textX = (int) super.getX();
		this.textY = (int) super.getY();
		centered = false;
		color = DEFAULT_COLOR;
		font = DEFAULT_FONT;
		
		title = "";
		titleX = 0;
		titleY = 0;
		tCentered = false;
		hasTitle = false;
	}
	
	public UITextHolder(Rectangle rect, BufferedImage img, Point textPos, boolean center, Handler handle, ClickListener click)
	{
		super(rect, handle);
		this.img = img;
		this.clicker = click;
		
		text = "";
		this.textX = (int) textPos.getX();
		this.textY = (int) textPos.getY();
		centered = center;
		color = DEFAULT_COLOR;
		font = DEFAULT_FONT;
		
		title = "";
		titleX = 0;
		titleY = 0;
		tCentered = false;
		hasTitle = false;
	}
	
	@Override
	public void update() {
		if (!super.hide)
		{
			
		}
	}

	@Override
	public void render(Graphics g) {
		if (!super.hide)
		{
			g.drawImage(img, (int) x, (int) y, width, height, null);
			if (hasTitle)
				Text.drawString(g, title, titleX, titleY, tCentered, color, font);
			Text.drawStringSplit(g, text, textX, textY, centered, color, font);
		}
	}
	

	@Override
	public void onClick() {
		System.err.println("yuppers");
		if (!super.hide)
			clicker.onClick();
	}
	
	public void setText(String t)
	{
		text = t;
	}
	
	public void setTitle(String t)
	{
		hasTitle = true;
		title = t;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTextPos(int x, int y)
	{
		textX = x;
		textY = y;
	}
	
	public void setTitlePos(int x, int y)
	{
		titleX = x;
		titleY = y;
	}
	
	public Point getTextPos()
	{
		return new Point(textX, textY);
	}
	
	public Point getTitlePos()
	{
		return new Point(titleX, titleY);
	}
	
	public void setCentered(boolean center)
	{
		centered = center;
	}
	
	public void setCenteredT(boolean c)
	{
		tCentered = c;
	}
	
	public boolean isCentered()
	{
		return centered;
	}
	
	public boolean isTitleCentered()
	{
		return tCentered;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void setFont(Font f)
	{
		font = f;
	}
	
	public Color getTextColor()
	{
		return color;
	}
	
	public Font getFont()
	{
		return font;
	}
}
