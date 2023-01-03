import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * TitleScreen is a subclass of the World class that contains the values to be passed on to the ZombieWorld class.
 * It also contains music that plays and stops when the world or another world is called.
 * 
 * @author Charis, Jonathan, Henry
 * @version April 2022
 */
public class TitleScreen extends World
{
    //Declaring variables
    private boolean button1;
    private boolean button2;
    private static GreenfootSound startMusic;
    private double plantHealth;
    private double plantDamage;
    private double plantAttackSpeed;
    private double zombieHealth;
    private double zombieSpeed;
    private double zombieDamage;
    private ButtonPlay buttonPlay;
    
    /**
     * Main constructor for the TitleScreen that contains the main menu music. It also holds 
     * the changed or unchanged parameters values to pass on to the ButtonPlay class.
     * 
     * @param plantHealth           The value each plant's base health is multiplied by.
     * @param plantDamage           The value each plant's base damage is multiplied by.
     * @param plantAttackSpeed      The value each plant's base attack speed is multiplied by.
     * @param zombieHealth          The value each zombie's base health is multiplied by.
     * @param zombieSpeed           The value each zombie's base speed is multiplied by.
     * @param zombieDamage          The value each zombie's base damage is multiplied by.
     */
    public TitleScreen(double plantHealth, double plantDamage, double plantAttackSpeed, double zombieHealth, double zombieSpeed, double zombieDamage)
    {
        super(750, 450, 1);
        button1 = false;
        button2 = false;
        
        startMusic = new GreenfootSound ("Main menu.wav");
        startMusic.setVolume (30);
        startMusic.play();
        
        this.plantHealth = plantHealth;
        this.plantDamage = plantDamage;
        this.plantAttackSpeed = plantAttackSpeed;
        this.zombieHealth = zombieHealth;
        this.zombieSpeed = zombieSpeed;
        this.zombieDamage = zombieDamage;
        
        buttonPlay = new ButtonPlay(plantHealth, plantDamage, plantAttackSpeed, zombieHealth, zombieSpeed, zombieDamage);
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
     * Button1 and button2 is set to true to prevent the world from adding buttons indefinitely.
     */
    public void act () 
    {
        //Checks to see if the first button is added.
        if(!button1)
        {
            addObject(buttonPlay, 479, 148);
            button1 = true;
        }
        //Checks to see if the second button is added.
        if(!button2)
        {
            addObject(new ButtonSettings(plantHealth, plantDamage, plantAttackSpeed, zombieHealth, zombieSpeed, zombieDamage), 49, 384);
            button2 = true;
        }
    }
    
    /**
     * Stops the music when method is called.
     */
    public static void stopMusic ()
    {
        startMusic.stop();
    }
}