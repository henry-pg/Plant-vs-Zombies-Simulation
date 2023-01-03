import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * StartingScreen is the subclass of the world that the code first calls on when it runs.
 * It contains the starting music and some text that informs the user how to move to the next screen.
 * 
 * @author Charis, Jonathan 
 * @version April 2022
 */
public class StartingScreen extends World
{
    //Declaring variables
    private boolean button1;
    private GreenfootSound pressed;
    private GreenfootSound startMusic;
    
    /**
     * The main constructor of the Starting Screen class that initializes the variables and sounds.
     */
    public StartingScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(750, 450, 1); 
        button1 = true;
        pressed = new GreenfootSound ("plant2.wav");
        pressed.setVolume(65);
        startMusic = new GreenfootSound ("Main menu.wav");
        startMusic.setVolume (30);
    }
    
    /**
     * Starts music when code is running.
     */
    public void started ()
    {
        startMusic.playLoop();
    }
    
    /**
     * Stops the music when the code is paused.
     */
    public void stopped ()
    {
        startMusic.stop();
    }
    
    /**
     * Act method is called when the the code is running.
     * Button1 is set to true to prevent the world from adding buttons indefinitely.
     * Changes the world when spacebar is pressed.
     */
    public void act()
    {
        //Checks to see if there is a button added.
        if (button1)
        {
            addObject (new Play(), 375, 130);
            button1 = false;
        }
        //Checks to see if the spacebar is pressed.
        if (Greenfoot.isKeyDown("space"))
        {
            pressed.play();
            startMusic.stop();
            Greenfoot.setWorld (new TitleScreen(1,1,1,1,1,1));
        }
    }
}