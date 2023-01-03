import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * LosingScreen is a class that informs the user that they have lost.
 * It slowly fades out before adding the GameOverScreen class.
 * 
 * @author Charis 
 * @version April 2022
 */
public class LosingScreen extends Actor
{
    private int transparency;
    
    /**
     * Constructor for the LosingScreen class.
     * Sets the actCounter variable to 0.
     */
    public LosingScreen()
    {
        transparency = 255;    
    }
    
    /**
     * Act method is called when the the code is running.
     * Changes the transparency of the image ot make it slowly fade out every act.
     * Removes object when it adds the GameoverScreen object and its transparency is 0.
     */
    public void act()
    {
        this.getImage().setTransparency(transparency - 1);
        transparency--;
        if(transparency <= 0)
        {
            getWorld().addObject(new GameOverScreen(), 400, 224);
            getWorld().removeObject(this);
        }
    }
}