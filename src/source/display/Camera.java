package source.display;

import java.awt.Point;
import java.awt.Rectangle;

import source.main.Handler;

public class Camera {
	
	// handler for access
	private Handler handler;
	// offset created by the camera that creates illusion of movement. Shifts all things in the virtual world by the offset
	private double xOffset, yOffset;
	//private boolean justZoomed;
	
	// this is pulled from mouse manager
	public final double DEFAULT_ZOOM;
	
	// the speed of the camera AND the rate at which the mouse bounds move
	public final int DEFAULT_SPEED = 25;
	
	// the size of the bounds. Increasing will increase the area in which the mouse must be in in-order-to move
	public final int BOUND_SIZE = 25;
	private Rectangle leftBound;
	private Rectangle rightBound;
	private Rectangle upperBound;
	private Rectangle lowerBound;
	
	public Camera(Handler handler, float xOffset, float yOffset){
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		DEFAULT_ZOOM = handler.getMouseManager().getDefaultZoom();
		//justZoomed = false;
		
		
		leftBound = new Rectangle(0,0, BOUND_SIZE, handler.getHeight());
		rightBound = new Rectangle(handler.getWidth() - BOUND_SIZE ,0, BOUND_SIZE, handler.getHeight());
		upperBound = new Rectangle(0,0, handler.getWidth(), BOUND_SIZE);
		lowerBound = new Rectangle(0,handler.getHeight() - BOUND_SIZE, handler.getWidth(), BOUND_SIZE);
	}
	
	public void initBounds()
	{
		leftBound = new Rectangle(0,0, BOUND_SIZE, handler.getHeight());
		rightBound = new Rectangle(handler.getWidth() - BOUND_SIZE ,0, BOUND_SIZE, handler.getHeight());
		upperBound = new Rectangle(0,0, handler.getWidth(), BOUND_SIZE);
		lowerBound = new Rectangle(0,handler.getHeight() - BOUND_SIZE, handler.getWidth(), BOUND_SIZE);
	}
	
	/*public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		}else if(xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth()){
			xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();
		}
		
		if(yOffset < 0){
			yOffset = 0;
		}else if(yOffset > handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight()){
			yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
		}
	}*/
	
	/*public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}*/
	public double getZoom()
	{
		return handler.getMouseManager().getZoom();
	}
	
	public double getDefaultZoom()
	{
		return DEFAULT_ZOOM;
	}
	
	public void resetZoom()
	{
		handler.getMouseManager().resetZoom();
	}
	
	public void zoomIn()
	{
		handler.getMouseManager().zoomIn();
	}
	
	public void zoomOut()
	{
		handler.getMouseManager().zoomOut();
	}
	
	/*public boolean didJustZoom()
	{
		return justZoomed;
	}*/
	
	public int getSpeed() {
		return DEFAULT_SPEED;
	}

	public Rectangle getLeftBound() {
		return leftBound;
	}

	public Rectangle getRightBound() {
		return rightBound;
	}

	public Rectangle getUpperBound() {
		return upperBound;
	}

	public Rectangle getLowerBound() {
		return lowerBound;
	}

	public void move(double xAmt, double yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
	}
	
	public void setOffsetZoom(double xO, double yO)
	{
		xOffset = (int) xO;
		yOffset = (int) yO;
		adjustBounds((int)xO, (int)yO);
	}
	
	public void centerOnPoint(Point p)
	{
		double screenX, screenY;
		
		// make sure we don't end up with division by zero
		if (handler.getWidth() != 0)
			screenX = (handler.getWidth() / 2);
		else
			screenX = 0;
		if (handler.getHeight() != 0)
			screenY = (handler.getHeight() / 2);
		else
			screenY = 0;
		
		xOffset = (int) (p.getX() - screenX);
		yOffset = (int) (p.getY() - screenY);
		
		initBounds();
		adjustBounds((int) xOffset, (int) yOffset);
	}
	
	// OVERLOADED
	public void centerOnPoint(double x, double y)
	{
		double screenX, screenY;
		
		if (handler.getWidth() != 0)
			screenX = (handler.getWidth() / 2);
		else
			screenX = 0;
		
		if (handler.getHeight() != 0)
			screenY = (handler.getHeight() / 2);
		else
			screenY = 0;
		
		xOffset = x - screenX;
		yOffset = y - screenY;
		
		initBounds();
		adjustBounds((int) xOffset, (int) yOffset);
	}
	
	// Moving in a certain direction requires that all the bounds move in that direction
	public void moveBoundsUp()
	{
		move(0, -DEFAULT_SPEED);
		upperBound.setLocation((int) upperBound.getX(),(int) upperBound.getY() - DEFAULT_SPEED);
		lowerBound.setLocation((int) lowerBound.getX(),(int) lowerBound.getY() - DEFAULT_SPEED);
		leftBound.setLocation((int) leftBound.getX(),(int) leftBound.getY() - DEFAULT_SPEED);
		rightBound.setLocation((int) rightBound.getX(),(int) rightBound.getY() - DEFAULT_SPEED);
	}
	
	public void moveBoundsDown()
	{
		move(0, DEFAULT_SPEED);
		upperBound.setLocation((int) upperBound.getX(),(int) upperBound.getY() + DEFAULT_SPEED);
		lowerBound.setLocation((int) lowerBound.getX(),(int) lowerBound.getY() + DEFAULT_SPEED);
		leftBound.setLocation((int) leftBound.getX(),(int) leftBound.getY() + DEFAULT_SPEED);
		rightBound.setLocation((int) rightBound.getX(),(int) rightBound.getY() + DEFAULT_SPEED);
	}
	
	public void moveBoundsRight()
	{
		move(DEFAULT_SPEED, 0);
		upperBound.setLocation((int) upperBound.getX() + DEFAULT_SPEED,(int) upperBound.getY());
		lowerBound.setLocation((int) lowerBound.getX() + DEFAULT_SPEED,(int) lowerBound.getY());
		leftBound.setLocation((int) leftBound.getX() + DEFAULT_SPEED,(int) leftBound.getY());
		rightBound.setLocation((int) rightBound.getX() + DEFAULT_SPEED,(int) rightBound.getY());
	}
	
	public void moveBoundsLeft()
	{
		move(-DEFAULT_SPEED, 0);
		upperBound.setLocation((int) upperBound.getX() - DEFAULT_SPEED,(int) upperBound.getY());
		lowerBound.setLocation((int) lowerBound.getX() - DEFAULT_SPEED,(int) lowerBound.getY());
		leftBound.setLocation((int) leftBound.getX() - DEFAULT_SPEED,(int) leftBound.getY());
		rightBound.setLocation((int) rightBound.getX() - DEFAULT_SPEED,(int) rightBound.getY());
	}
	
	public void adjustBounds(double xAmt, double yAmt)
	{
		/*double rBstep = (1410) / handler.getMouseManager().getZoomFactor(); // this is 235 when width 1920 // 1410 represents where you want the bounds at max zoom
		double lBstep = (810) / handler.getMouseManager().getZoomFactor(); // this is 135 when h 1080 // 810 represents where you want the bounds at max zoom
*/		
		upperBound.setLocation((int) (upperBound.getX() + xAmt), (int) (upperBound.getY() + yAmt));
		lowerBound.setLocation((int) (lowerBound.getX() + xAmt), (int) (lowerBound.getY() + yAmt));
		leftBound.setLocation((int) (leftBound.getX() + xAmt), (int) (leftBound.getY() + yAmt));
		rightBound.setLocation((int) (rightBound.getX() + xAmt), (int) (rightBound.getY() + yAmt));
		
		/*if (xAmt > 0)
		{
			lowerBound.setLocation((int) (lowerBound.getX() + xAmt), (int) (lowerBound.getY() - lBstep));
			rightBound.setLocation((int) (rightBound.getX() - rBstep), (int) (rightBound.getY() + yAmt));
		}
		else
		{
			lowerBound.setLocation((int) (lowerBound.getX() + xAmt), (int) (lowerBound.getY() + lBstep));
			rightBound.setLocation((int) (rightBound.getX() + rBstep), (int) (rightBound.getY() + yAmt));
		}*/
	}
	
	public boolean leftContains(Point mP)
	{
		return leftBound.contains(mP);
	}
	
	public boolean rightContains(Point mP)
	{
		return rightBound.contains(mP);
	}
	
	public boolean upperContains(Point mP)
	{
		return upperBound.contains(mP);
	}
	
	public boolean lowerContains(Point mP)
	{
		return lowerBound.contains(mP);
	}

	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}

// Zoom example
/*
 * import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private double zoom = 1d;
        private BufferedImage img;

        public TestPane() {
            try {
                img = ImageIO.read(new File("..."));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            addMouseWheelListener(new MouseAdapter() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getPreciseWheelRotation() < 0) {
                        zoom -= 0.1;
                    } else {
                        zoom += 0.1;
                    }
//                  zoom += e.getPreciseWheelRotation();
                    if (zoom < 0.01) {
                        zoom = 0.01;
                    }

                    repaint();

                }
            });
        }

        public String format(double value) {
            return NumberFormat.getNumberInstance().format(value);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            double width = getWidth();
            double height = getHeight();

            double zoomWidth = width * zoom;
            double zoomHeight = height * zoom;

            double anchorx = (width - zoomWidth) / 2;
            double anchory = (height - zoomHeight) / 2;

            AffineTransform at = new AffineTransform();
            at.translate(anchorx, anchory);
            at.scale(zoom, zoom);
            at.translate(-100, -100);

            g2d.setTransform(at);
            g2d.drawImage(img, 0, 0, this);

            g2d.dispose();
        }

    }

}
 * */
