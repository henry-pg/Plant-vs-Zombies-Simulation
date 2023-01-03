import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * ButtonTitleScreen is the subclass of Button that contains a sound and images.
 * 
 * @author Charis, Jonathan
 * @version April 2022
 */
public class ButtonTitleScreen extends Button
{
    /**
     * Main constructor for the ButtonTitleScreen class. ButtonTitleScreen is the subclass of Button.
     * Check the superclass.
     */
    public ButtonTitleScreen()
    {
        super();
    }
 
    /**
     * Act method is called when the the code is running.
     * Checks to see if the actor has been clicked on by a mouse.
     * Changes the image when clicked for a brief moment.
     * It also passes on the variable's values as well as changes worlds.
     */
    public void act()
    {
        if (Greenfoot.mousePressed(this))
        {
            setImage ("TitleScreenButton-pressed.png");
            ZombieWorld.stopMusic();
            timer.mark();
            pressed.play();
            timerStarted = true;
        }
        else
        {
            setImage ("TitleScreenButton.png");
        }
 
        if(timer.millisElapsed() > 100 && timerStarted)
        {
            Greenfoot.setWorld (new TitleScreen(1,1,1,1,1,1));
        }
    }
}