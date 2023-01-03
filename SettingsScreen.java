import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * The SettingsScreen is a subclass of the world that contains actor classes that allow the user
 * to edit some of the parameters of the simulation. It contains the settings music as well as a way to switch worlds.
 * 
 * @author Charis, Jonathan, Henry
 * @version April 2022
 */
public class SettingsScreen extends World
{
    private boolean button3;
    private static GreenfootSound music;
    
    private TextBox plantHealth;
    private TextBox plantDamage;
    private TextBox plantAttackSpeed;
    
    private TextBox zombieHealth;
    private TextBox zombieSpeed;
    private TextBox zombieDamage;
    
    private ButtonOk buttonOk;
    
    /**
     * A simple constructor that contains and initializes multiple variables 
     */
    public SettingsScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(750, 450, 1);
        button3 = false;
        music = new GreenfootSound ("Ingame2.wav");
        music.setVolume (45);
        music.play();
        
        plantHealth = new TextBox(1);
        addObject(plantHealth, 350,125);
        
        plantDamage = new TextBox(1);
        addObject(plantDamage, 350,183);
        
        plantAttackSpeed = new TextBox(1);
        addObject(plantAttackSpeed, 350,244);
        
        zombieHealth = new TextBox(1);
        addObject(zombieHealth, 588, 125);
        
        zombieDamage = new TextBox(1);
        addObject(zombieDamage, 588, 183);
        
        zombieSpeed = new TextBox(1);
        addObject(zombieSpeed, 588, 244);
        
        buttonOk = new ButtonOk(plantHealth.getValue(), plantDamage.getValue(),plantAttackSpeed.getValue(),zombieHealth.getValue(), zombieSpeed.getValue(), zombieDamage.getValue());
    }
    
    /**
     * The constructor that contains and initializes multiple variables.
     * 
     * @param plantHealth           The value each plant's base health is multiplied by.
     * @param plantDamage           The value each plant's base damage is multiplied by.
     * @param plantAttackSpeed      The value each plant's base attack speed is multiplied by.
     * @param zombieHealth          The value each zombie's base health is multiplied by.
     * @param zombieSpeed           The value each zombie's base speed is multiplied by.
     * @param zombieDamage          The value each zombie's base damage is multiplied by.
     */
    public SettingsScreen(double plantHealth, double plantDamage, double plantAttackSpeed, double zombieHealth, double zombieSpeed, double zombieDamage)
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(750, 450, 1); 
        music = new GreenfootSound ("Ingame2.wav");
        music.setVolume (45);
        music.play();
        
        this.plantHealth = new TextBox(plantHealth);
        addObject(this.plantHealth, 350,125);
        
        this.plantDamage = new TextBox(plantDamage);
        addObject(this.plantDamage, 350,183);
        
        this.plantAttackSpeed = new TextBox(plantAttackSpeed);
        addObject(this.plantAttackSpeed, 350,244);
        
        this.zombieHealth = new TextBox(zombieHealth);
        addObject(this.zombieHealth, 588, 125);
        
        this.zombieDamage = new TextBox(zombieDamage);
        addObject(this.zombieDamage, 588, 183);
        
        this.zombieSpeed = new TextBox(zombieSpeed);
        addObject(this.zombieSpeed, 588, 244);
        
        buttonOk = new ButtonOk(this.plantHealth.getValue(), this.plantDamage.getValue(),this.plantAttackSpeed.getValue(),this.zombieHealth.getValue(), this.zombieSpeed.getValue(), this.zombieDamage.getValue());
        
    }
    
    /**
     * Starts music when code is running.
     */
    public void started ()
    {
        music.playLoop();
    }
    
    /**
     * Stops the music when the code is paused.
     */
    public void stopped ()
    {
        music.stop();
    }
    
    /**
     * Act method is called when the the code is running.
     * Button3 is set to true to prevent the world from adding buttons indefinitely.
     */
    public void act () 
    {
        //checks if a button has been added
        if(!button3)
        {
            //make buttonOk an referencable object
            addObject(buttonOk, 374, 360);
            button3 = true;
        }
        
        buttonOk.update(plantHealth.getValue(), plantDamage.getValue(),plantAttackSpeed.getValue(),zombieHealth.getValue(), zombieSpeed.getValue(), zombieDamage.getValue());
    }
    
    /**
     * Stops the music when method is called.
     */
    public static void stopMusic()
    {
        music.stop();
    }
}