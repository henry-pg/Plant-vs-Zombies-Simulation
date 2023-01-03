import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Wallnut plant. It serves as a shield for other plants
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class Wallnut extends Plant
{
    /**
     * Constructor for Wallnut. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Wallnut()
    {
        super(500);
        normalImage = new GreenfootImage("wallnut2.png");
        frozenImage = new GreenfootImage ("wallnut2-frozen.png");
        originalImage = normalImage;
        
        imageHeight = originalImage.getHeight();
        imageWidth = originalImage.getWidth();
        initialHeight = (int)(imageHeight*0.2);
        initialWidth = (int)(imageWidth*0.2);
        getImage().scale(initialWidth,initialHeight);
    }
    
    /**
     * Act method for the Wallnut. Calls upon the superclass' act method.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * The attack method is not nessecary because the Wallnut does not attack
     */
    public void attack()
    {
        //wallnut does nothing so this is empty
    }
    
    /**
     * Frozen effect is not nessecary because the Wallnut does not attack
     */
    public void frozenEffect()
    {
        
    }
    
    /**
     * Normal effect is not nessecary because the Wallnut does not attack
     */
    public void normalEffect()
    {
        
    }
    
    
    
    
}