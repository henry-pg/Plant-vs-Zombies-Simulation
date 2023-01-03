import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Peashooter Plant.
 * 
 * @author Henry, Ethan 
 * @version April 2022
 */
public class Peashooter extends Plant
{
    /**
     * Constructor for Peashooter. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Peashooter()
    {
        super();    
        normalImage = new GreenfootImage("peashooter.png");
        frozenImage = new GreenfootImage ("peashooter-frozen.png");
        originalImage = normalImage;
        
        imageHeight = originalImage.getHeight();
        imageWidth = originalImage.getWidth();
        initialHeight = (int)(imageHeight*0.2);
        initialWidth = (int)(imageWidth*0.2);
        
        masterShootingRate = 90;
        shootingRate = 90;
        shootingCount = shootingRate;
        
        getImage().scale(initialWidth,initialHeight);
    }
    
    /**
     * Act method for the Peashooter. Sets the shooting rate and calls upon the 
     * superclass' act method.
     */
    public void act()
    {
        if (firstTime == true){
            shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());
        }
        super.act();
    }
    
    /**
     * Method for the Peashooter to attack zombies. Spawns a Pea at set intervals.
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
        }
    }
    
    /**
     * Method to change the Peashooter's shooting rate when under the frozen effect
     */
    public void frozenEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed()) + 20;
    }

    /**
     * Method to change the Peashooter's shooting rate back to normal
     */
    public void normalEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());;
    }
}