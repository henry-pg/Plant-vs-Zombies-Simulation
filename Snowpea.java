import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Snowpea Plant.
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class Snowpea extends Plant
{
    /**
     * Constructor for Snowpea. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Snowpea()
    {
        super();    
        normalImage = new GreenfootImage("icepeashooter.png");
        frozenImage = new GreenfootImage ("icepeashooter.png");
        originalImage = normalImage;
        
        imageHeight = originalImage.getHeight();
        imageWidth = originalImage.getWidth();
        initialHeight = (int)(imageHeight*0.2);
        initialWidth = (int)(imageWidth*0.2);
       
        masterShootingRate = 100;
        shootingRate = 100;
        shootingCount = shootingRate;
        
        getImage().scale(initialWidth,initialHeight);
    }
    
    /**
     * Act method for the Snowpea. Sets the shooting rate and calls upon the 
     * superclass' act method.
     */
    public void act()
    {
        if (firstTime == true)
        {
            shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());
        }
        super.act();
    }
    
    /**
     * Method for the Cactus to attack zombies. Spawns a Spike at set intervals.
     */
    public void attack()
    {
        // Subtract the shooting timer
        shootingCount --;
        if (shootingCount == 0)
        {
            // Add a SnowPeaShot to the world
            getWorld().addObject(new SnowPeaShot(), getX()+23, getY()-10);
            // Reset the shooting rate
            shootingCount = shootingRate;
            
            //shooting sound
            shootSound();
        }
    }
    
    /**
     * Frozen effect is not nessecary because the Snowpea is not affected by Snowstorms
     */
    public void frozenEffect()
    {
        
    }
    
    /**
     * Normal effect is not nessecary because the Snowpea is not affected by Snowstorms
     */
    public void normalEffect()
    {
        
    }
}