import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The GameWinScreen class contains a button that lets the user return
 * to the TitleScreen. It is called on when the simulation is over.
 * 
 * @author Charis
 * @version April 2022
 */
public class GameWinScreen extends Actor
{
    private boolean button1;

    /**
     * Constructor for the GameWinScreen class.
     * Changes the button1 variable to false so that its possible to
     * add the specified button.
     */
    public GameWinScreen()
    {
        button1 = false;
    }

    /**
     * Act method is called when the the code is running.
     * Button1 is set to true to prevent the world from adding buttons indefinitely.
     */
    public void act()
    {
        if(!button1)
        {
            getWorld().addObject(new ButtonTitleScreen(), 379, 332);
            button1 = true;
        }
    }
}