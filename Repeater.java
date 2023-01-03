import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Repeater Plant.
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class Repeater extends Plant
{
    /**
     * Constructor for Repeater. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Repeater()
    {
        super(); 
        normalImage = new GreenfootImage("repeater2.png");
        frozenImage = new GreenfootImage ("repeater2-frozen.png");
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
     * Act method for the Repeater. Sets the shooting rate and calls upon the 
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
     * Method for the Cactus to attack zombies. Spawns Peas at set intervals.
     */
    public void attack()
    {
        // Subtract the shooting timer
        shootingCount --;
        if (shootingCount == 0)
        {
            // Add a Pea to the world
            getWorld().addObject(new PeaShot(), getX()+23, getY()-12);
            
            // Reset the shooting timer
            shootingCount = shootingRate;
            
            //shooting sound
            shootSound();
        } else if (shootingCount == 10)
        {
            // Add a Pea to the world slightly earlier
            getWorld().addObject(new PeaShot(), getX()+23, getY()-12);
            
            //shooting sound
            shootSound();
        }
    }
    
    /**
     * Method to change the Repeater's shooting rate when under the frozen effect
     */
    public void frozenEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed()) + 20;
    }

    /**
     * Method to change the Repeater's shooting rate back to normal
     */
    public void normalEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());;
    }
}