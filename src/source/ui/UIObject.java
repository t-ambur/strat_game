package source.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import source.main.Handler;

public abstract class UIObject implements ClickListener {
	
	Handler handler;
	
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected Rectangle gameBounds;
	protected boolean hovering = false;
	protected boolean hide = false;
	
	public UIObject(float x, float y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
		topInit();
	}
	
	public UIObject(Rectangle rect){
		this.x = (float) rect.getX();
		this.y = (float) rect.getY();
		this.width = (int) rect.getWidth();
		this.height = (int) rect.getHeight();
		bounds = new Rectangle((int) x, (int) y, width, height);
		topInit();
	}
	
	public UIObject(Rectangle rect, Handler handler){
		this.x = (float) rect.getX();
		this.y = (float) rect.getY();
		this.width = (int) rect.getWidth();
		this.height = (int) rect.getHeight();
		bounds = new Rectangle((int) x, (int) y, width, height);
		
		this.handler = handler;
		topInit();
	}
	
	public void topInit()
	{
		// nothing
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e){
		if (handler == null)
		{
			if(bounds.contains(e.getX(), e.getY()))
				hovering = true;
			else
				hovering = false;
		}
		else
		{
			gameBounds = new Rectangle((int) (x - handler.getCamera().getxOffset()) , (int) (y - handler.getCamera().getyOffset()),width,height);
			if(bounds.contains(handler.getMouseManager().getVMouseX(), handler.getMouseManager().getVMouseY()))
				hovering = true;
			else
				hovering = false;
		}
	}
	
	public void moveBoundsVert(int amt)
	{
		bounds.setLocation((int) bounds.getX(),(int) bounds.getY() + amt);
	}
	
	public void moveBoundsHort(int amt)
	{
		bounds.setLocation((int) bounds.getX() + amt,(int) bounds.getY());
	}
	
	public void setBounds(int xAmt, int yAmt)
	{
		bounds.setLocation((int) bounds.getX() + xAmt,(int) bounds.getY() + yAmt);
	}
	
	public void onMouseRelease(MouseEvent e){
		if(hovering)
			onClick();
	}
	
	// Getters and setters

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
	public void toggleHide()
	{
		hide = !hide;
	}
	
	public void turnOff()
	{
		hide = true;
	}
	
	public void turnOn()
	{
		hide = false;
	}
	
	public boolean isHidden()
	{
		return hide;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
}