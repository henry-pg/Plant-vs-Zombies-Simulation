import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * This is the Homing Thistle Plant.
 * 
 * @author Henry, Ethan
 * @version April 2022
 */
public class HomingThistle extends Plant
{
    private ArrayList <Zombie> zombie;
    
    /**
     * Constructor for HomingThistle. Calls upon the superclass' constructor 
     * and sets other important variables.
     */
    public HomingThistle()
    {
        super();    
        
        normalImage = new GreenfootImage("homingthistle3.png");
        frozenImage = new GreenfootImage ("homingthistle3-frozen.png");
        originalImage = normalImage;
        
        imageHeight = originalImage.getHeight();
        imageWidth = originalImage.getWidth();
        initialHeight = (int)(imageHeight*0.2);
        initialWidth = (int)(imageWidth*0.2);
        
        masterShootingRate = 150;
        shootingRate = 150;
        shootingCount = shootingRate;
        getImage().scale(initialWidth,initialHeight);
    }
    
    /**
     * Act method for the HomingThistle. Sets the shooting rate and calls upon the 
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
     * Method for the HomingThistle to attack zombies. Spawns a Thistle at set intervals.
     */
    public void attack()
    {
        // Subtract the shooting timer
        shootingCount --;
        if (shootingCount == 0){
            // Add a Thistle to the world
            getWorld().addObject(new Thistle(), getX(), getY()-30);
            // Reset the shooting timer
            shootingCount = shootingRate;
        }
    }
    
    /**
     * Method to change the HomingThistle's shooting rate when under the frozen effect
     */
    public void frozenEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed()) + 20;
    }

    /**
     * Method to change the HomingThistle's shooting rate back to normal
     */
    public void normalEffect()
    {
        shootingRate = (int)(masterShootingRate /((ZombieWorld)(getWorld())).getPlantAttackSpeed());;
    }
}