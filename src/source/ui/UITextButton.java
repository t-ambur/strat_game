package source.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import source.support.Text;

public class UITextButton extends UIImageButton {
	
	private String text;
	private int textX;
	private int textY;
	private boolean centered;
	
	private static Color color;
	private static Font font;
	
	public UITextButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height, images, clicker);
		init();
	}
	
	public UITextButton(Rectangle rect, BufferedImage[] images, ClickListener clicker)
	{
		super(rect, images, clicker);
		init();
	}
	
	public UITextButton(Rectangle rect, BufferedImage image1, BufferedImage image2, ClickListener clicker)
	{
		super(rect, image1, image2, clicker);
		init();
	}
	
	public void init()
	{
		centered = false;
		color = UITextHolder.DEFAULT_COLOR;
		font = UITextHolder.DEFAULT_FONT;
		textX = (int) super.x + 10;
		textY = (int) super.y + 5;
		text = "";
	}
	
	public void setText(String t)
	{
		this.text = t;
	}

	@Override
	public void render(Graphics g) {
		if (!super.hide)
		{
			if(hovering)
				g.drawImage(super.images[1], (int) x, (int) y, width, height, null);
			else
				g.drawImage(super.images[0], (int) x, (int) y, width, height, null);
			Text.drawStringSplit(g, text, textX, textY, centered, color, font);
		}
	}
}
