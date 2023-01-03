import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is a button for the textbox class so that users can change the variables/ values of plants and zombies
 * in the simulation
 * 
 * @author (Henry) 
 * @version (April 2022)
 */
public class MultiplierButton extends Actor
{
    private GreenfootImage image1;
    private GreenfootImage image2;
    private int[] xPoints;
    private int[] yPoints;
    
    /**
     * Constructor that initializes the button and draws it. 
     * 
     * @param isRight This is a boolean that determines if the class draws a left facing button or a right facing button.
     */
    public MultiplierButton(boolean isRight)
    {
        image1 = new GreenfootImage(20, 20);
        image2 = new GreenfootImage (20,20);

        if (isRight == true)
        {
            xPoints = new int[] { 0, 20, 0 };
            yPoints = new int[] { 20, 10, 0 };
        }
        else if (isRight == false)
        {
            xPoints = new int[] { 0, 20, 20 };
            yPoints = new int[] { 10, 0, 20};
        }
        
        image1.drawPolygon(xPoints, yPoints, 3);
        image1.setColor(Color.GRAY);
        image1.fillPolygon(xPoints, yPoints, 3);

        image2.drawPolygon(xPoints, yPoints, 3);
        image2.setColor(Color.LIGHT_GRAY);
        image2.fillPolygon(xPoints, yPoints, 3);

        setImage(image1);
    }
    
    /**
     * Method that checks to see if the button is clicked
     * 
     * @return boolean Returns true if the button is clicked, False otherwise. 
     */
    public boolean isClicked()
    {
        if (Greenfoot.mouseClicked(this))
        {
            setImage(image2);
            return true;
        }
        setImage(image1);
        return false;
    }
}
