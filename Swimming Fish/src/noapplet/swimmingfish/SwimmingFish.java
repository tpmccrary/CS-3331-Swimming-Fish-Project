package noapplet.swimmingfish;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import noapplet.swimmingfish.NoApplet;
/** A class that draws a fish that goes back and forth on screen.
 *  Makeup exam for CS 3331.
 * 
 * @author Timothy P. McCrary
 *
 */
@SuppressWarnings("serial")
public class SwimmingFish extends NoApplet
{
	/** Directory for image files: src/image in Eclipse. */
	private final static String IMAGE_DIR = "/image/";
	
	/** The image used for the fish. */
	private Image fish;
	/** The image used for the background. */
	private Image background;
	
	/** The x dimension for the fish image. */
	private int fishDimX = 128;
	/** The y dimension for the fish image. */
	private int fishDimY = 128;
	
	/** The x coordinate of where the fish will be. Top left of image is reference point. */
	private int x;
	/** The y coordinate of where the fish will be. Top left of image is reference point. */
	private int y;
	
	/** The offset of how the fish will move on the x coordinate. */
	private int offsetX = 2;
	/** The offset of how the fish will move on the y coordinate. */
	private int offsetY = -1;
	/** Stores the offset value of offsetY. */
	private int tempOffsetY = offsetY;
	
	/** The distance the fish must travel in order to move again alone the y axis. */
	private int distance = 10;
	/** Store the value of distance. */
	private int distanceCount = distance;
	
	private static int windowWidth = 1280;
	private static int windowHeight = 720;
	private Dimension dim;
	
	private Timer timer;
	/** Delay of screen refreshes(in milliseconds). */
	private int delay = 12;
	
	public SwimmingFish(String[] params) 
	{
		super(params);
    }
	
	@Override
	public void init()
	{
		
		String param = getParameter("delay");
		if(param != null)
		{
			delay = Integer.parseInt(param);
		}
		// Get size of window.
		dim = getSize();
		
		// Sets where fish will be on screen.
		x = 0;
		y = (dim.height / 2) - (fishDimY / 2);
		
		fish = getImage("fish.png");
		background = getImage("FishTank.jpg");
		
		timer = new Timer(delay, e -> repaint());
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, dim.width, dim.height, this);
		
		// Move the fish by changing its position in (x, y) by the x and y offset.
		x += offsetX;
		y += offsetY;
		
		moveFishY();
		
		moveFishX();
		
		
		// If the fish is moving moving right, draw the image of the fish facing right.
		if (offsetX > 0)
		{
			g.drawImage(fish, x, y, fishDimX, fishDimY, this);
		}
		// If the fish is moving left, draw the image of the fish facing left.
		else if (offsetX < 0)
		{
			g.drawImage(fish, x, y, -(fishDimX), fishDimY, this);
		}
	}

	
	/** Moves the fish image to the right and left at constant speed. */
	public void moveFishX()
	{
		// If the fish is off screen to the right, makes the fish start moving to the left.
		if (x > dim.width && offsetX > 0)
		{
			x = dim.width + fishDimX;
			offsetX = -(offsetX);
		}
		// If the fish is off screen to the left, makes the fish start moving right.
		else if (x < 0 && offsetX < 0)
		{
			x = -(fishDimX);
			offsetX = -(offsetX);
		}
	}
	
	/** Moves the fish image up and down. */
	public void moveFishY()
	{
		if(y < ((dim.height / 2) - (fishDimY / 2)) - fishDimY / 2)
		{
			offsetY = 0;
			if(distanceCount != 0)
			{
				distanceCount--;
			}
			else
			{
				offsetY = -(tempOffsetY);
				distanceCount = distance;
			}
		}
		else if(y > ((dim.height / 2) - (fishDimY / 2)) + fishDimY / 2)
		{
			offsetY = 0;
			if(distanceCount != 0)
			{
				distanceCount--;
			}
			else
			{
				offsetY = tempOffsetY;
				distanceCount = distance;
			}
		}
	}
	
	/** Return the image stored in the given file. */
    public Image getImage(String file) {
        try {
        	URL url = new URL(getClass().getResource(IMAGE_DIR), file);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /** Start the animation. */
    @Override
    public void start() 
    {
        timer.start();
    }

    /** Stop the animation. */
    @Override
    public void stop() 
    {
        timer.stop();
    }
	
	public static void main(String[] args)
	{
		// Sets the window size.
		new SwimmingFish(new String[] {"width=" + windowWidth, "height=" + windowHeight}).run();
	}
}
