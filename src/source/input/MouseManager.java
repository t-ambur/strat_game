package source.input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import source.main.Handler;
import source.ui.UIManager;


public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private boolean leftPressed, rightPressed, middlePressed;
	
	// Zoom parameters
	private double zoom;
	private final double DEFAULT_ZOOM = 1.0; // the starting zoom for the game
	private final double ZOOM_AMOUNT = 0.5; // the set amount of the zoom, default += and default -= amount
	private final double MAX_MAGNIFICATION = 3; // the max zoom is default*max_mag
	private final int ZOOM_MOVE_X; // was 330, adjusted to 320
	private final int ZOOM_MOVE_Y; // was 180
	private final int ZOOM_FACTOR = (int) Math.round(MAX_MAGNIFICATION / ZOOM_AMOUNT); // this is 6 on 1920-1080
	
	// mouse location in x/y
	private int mouseX, mouseY;
	private double vMouseX, vMouseY;
	
	private UIManager uiManager;
	
	// to move the mouse
	private Robot robot;
	private Point center;
	
	private Handler handler;
	
	public MouseManager(Handler handler){
		this.handler = handler;
		ZOOM_MOVE_X = 200;
		ZOOM_MOVE_Y = 100;
		vMouseX = 0;
		vMouseY = 0;
		zoom = DEFAULT_ZOOM;
		
		init();
	}
	
	public void init()
	{
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("Error loading mouse Robot");
			e.printStackTrace();
		}
		
		center = new Point(handler.getWidth()/2, handler.getHeight()/2);
	}
	
	public void setUIManager(UIManager uiManager){
		this.uiManager = uiManager;
	}
	
	// Getters
	
	public boolean isLeftPressed(){
		return leftPressed;
	}
	
	public boolean isRightPressed(){
		return rightPressed;
	}
	
	public boolean isMiddlePressed(){
		return middlePressed;
	}
	
	public double getDefaultZoom()
	{
		return DEFAULT_ZOOM;
	}
	
	public double getZoomMoveX()
	{
		return ZOOM_MOVE_X;
	}
	
	public double getZoomMoveY()
	{
		return ZOOM_MOVE_Y;
	}
	
	public int getZoomFactor()
	{
		return ZOOM_FACTOR;
	}
	
	public double getZoom()
	{
		return zoom;
	}
	
	public void resetZoom()
	{
		zoom = DEFAULT_ZOOM;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
	public double getVMouseX()
	{
		return vMouseX;
	}
	
	public double getVMouseY()
	{
		return vMouseY;
	}
	
	public Robot getRobot()
	{
		return robot;
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	// Implemented methods
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON2)
			middlePressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON2)
			middlePressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
		
		if(uiManager != null)
			uiManager.onMouseRelease(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if (uiManager != null)
			uiManager.onMouseMove(e);
		
		if (handler != null) // to avoid null pointer exceptions at start
		{
			if (handler.getCamera() != null) // to avoid null pointer exceptions at start
			{
				// set the "virtual" (in-use, in-game) mouse positions. It adjusts based on the camera offset 
				vMouseX = mouseX + handler.getCamera().getxOffset();
				vMouseY = mouseY + handler.getCamera().getyOffset();
				//System.out.println("x: " + mouseX + " ,y: " + mouseY);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void mouseWheelMoved(MouseWheelEvent e) {
		
		int xz = (int) (ZOOM_MOVE_X / zoom);
		int yz = (int) (ZOOM_MOVE_Y / zoom);
		
        if (e.getWheelRotation() < 0)
        {
        	if (zoom <= MAX_MAGNIFICATION)
        	{
        		zoom += ZOOM_AMOUNT;
        		handler.getCamera().move(xz, yz);
        		handler.getCamera().adjustBounds(xz,yz);
        		handler.getWorld().getUI().hideAll();
        		//robot.mouseMove((int)(handler.getCamera().getxOffset()), (int) (handler.getCamera().getyOffset()));
        	}
        }
        if (e.getWheelRotation() > 0)
        {
        	if (zoom > DEFAULT_ZOOM)
        	{
        		zoom -= ZOOM_AMOUNT;
        		handler.getCamera().move(-xz, -yz);
        		handler.getCamera().adjustBounds(-xz,-yz);
        		if (handler.getCamera().getZoom() == handler.getCamera().getDefaultZoom())
        			handler.getWorld().getUI().showAll();
        		//robot.mouseMove((int)(handler.getCamera().getxOffset()), (int) (handler.getCamera().getyOffset()));
        	}
        }
    }
	
	public void zoomIn()
	{
		int xz = (int) (ZOOM_MOVE_X / zoom);
		int yz = (int) (ZOOM_MOVE_Y / zoom);
		
		if (zoom <= MAX_MAGNIFICATION)
    	{
    		zoom += ZOOM_AMOUNT;
    		handler.getCamera().move(xz, yz);
    		handler.getCamera().adjustBounds(xz,yz);
    		//robot.mouseMove((int)center.getX(), (int) center.getY());
    	}
	}
	
	public void zoomOut()
	{
		int xz = (int) (ZOOM_MOVE_X / zoom);
		int yz = (int) (ZOOM_MOVE_Y / zoom);
		
		if (zoom > DEFAULT_ZOOM)
    	{
    		zoom -= ZOOM_AMOUNT;
    		handler.getCamera().move(-xz, -yz);
    		handler.getCamera().adjustBounds(-xz,-yz);
    		//robot.mouseMove((int)center.getX(), (int) center.getY());
    	}
	}
}
