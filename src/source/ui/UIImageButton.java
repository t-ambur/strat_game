package source.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {

	private BufferedImage[] images;
	private ClickListener clicker;
	
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}
	
	public UIImageButton(Rectangle rect, BufferedImage[] images, ClickListener clicker)
	{
		super(rect);
		this.images = images;
		this.clicker = clicker;
	}
	
	public UIImageButton(Rectangle rect, BufferedImage image1, BufferedImage image2, ClickListener clicker)
	{
		super(rect);
		images = new BufferedImage[2];
		images[0] = image1;
		images[1] = image2;
		this.clicker = clicker;
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
			if(hovering)
				g.drawImage(images[1], (int) x, (int) y, width, height, null);
			else
				g.drawImage(images[0], (int) x, (int) y, width, height, null);
		}
	}

	@Override
	public void onClick() {
		if (!super.hide)
			clicker.onClick();
	}
}
