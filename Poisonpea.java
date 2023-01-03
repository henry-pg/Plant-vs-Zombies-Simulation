import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Poisonpea Plant.
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class Poisonpea extends Plant
{
    /**
     * Constructor for Poisonpea. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Poisonpea()
    {
        super();    
        normalImage = new GreenfootImage("poisonpea.png");
        frozenImage = new GreenfootImage ("poisonpea-frozen.png");
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
     * Act method for the Poisonpea. Sets the shooting rate and calls upon the 
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
     * Method for the Cactus to attack zombies. Spawns a PoisonPeaShot at set intervals.
     */
    public void attack()
    {
        // Subtract the shooting timer
        shootingCount --;
        if (shootingCount == 0)
        {
            // Add a PoisonPeaShot to the world
            getWorld().addObject(new PoisonPeaShot(), getX()+23, getY()-10);
            
            // Reset the shooting timer
            shootingCount = shootingRate;
            
            //shooting sound
            shootSound();
        }
    }
    
    /**
     * Method to change the Cactus' shooting rate when under the frozen effect
     */
    public void frozenEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed()) + 20;
    }

    /**
     * Method to change the Cactus' shooting rate back to normal
     */
    public void normalEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());;
    }
}