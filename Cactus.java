import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Cactus Plant.
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class Cactus extends Plant
{
    /**
     * Constructor for Cactus. Calls upon the superclass' constructor and sets
     * other important variables.
     */
    public Cactus()
    {
        super();

        normalImage = new GreenfootImage("cactus2.png");
        frozenImage = new GreenfootImage ("cactus2-frozen.png");
        originalImage = normalImage;//new GreenfootImage("cactus2.png");

        imageHeight = originalImage.getHeight();
        imageWidth = originalImage.getWidth();
        initialHeight = (int)(imageHeight*0.2);
        initialWidth = (int)(imageWidth*0.2);

        getImage().scale(initialWidth,initialHeight);

        masterShootingRate = 60;
        shootingRate = 60;
        shootingCount = shootingRate;
    }

    /**
     * Act method for the Cactus. Sets the shooting rate and calls upon the 
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
            // Add a Spike to the world
            getWorld().addObject(new Spike(), getX() + 30, getY() - 6);
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