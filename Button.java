import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button is the superclass of the buttons.
 * It contains the sound that most buttons use.
 * 
 * @author Charis, Jonathan 
 * @version April 2022
 */
public abstract class Button extends Actor
{
    protected SimpleTimer timer = new SimpleTimer();
    protected boolean timerStarted = false;
    protected GreenfootSound pressed;
    
    /**
     * The main constructor for the Button class.
     * It contain the esound for most buttons.
     */
    public Button()
    {
        pressed = new GreenfootSound ("shovel.wav");   
        pressed.setVolume(65);
    }
}