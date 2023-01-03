import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * ButtonSettings is the subclass of Button that contains a sound as well as
 * multiple other parameters and variables. It changes to other worlds while also storing and passing
 * on parameters to the worlds.
 * 
 * @author Charis, Jonathan, Henry
 * @version April 2022
 */
public class ButtonSettings extends Button
{
    private double plantHealth;
    private double plantDamage;
    private double plantAttackSpeed;
    private double zombieHealth;
    private double zombieSpeed;
    private double zombieDamage;
    
    /**
     * Main constructor for the ButtonSettings class. ButtonSettings is the subclass of Button that 
     * takes in the changed/unchanged parameters from the settings screen.
     * 
     * @param plantHealth           The value each plant's base health is multiplied by.
     * @param plantDamage           The value each plant's base damage is multiplied by.
     * @param plantAttackSpeed      The value each plant's base attack speed is multiplied by.
     * @param zombieHealth          The value each zombie's base health is multiplied by.
     * @param zombieSpeed           The value each zombie's base speed is multiplied by.
     * @param zombieDamage          The value each zombie's base damage is multiplied by.
     */
    public ButtonSettings(double plantHealth, double plantDamage, double plantAttackSpeed, double zombieHealth, double zombieSpeed, double zombieDamage)
    {
        pressed = new GreenfootSound ("seedlift.wav");
        pressed.setVolume (65);
        this.plantHealth = plantHealth;
        this.plantDamage = plantDamage;
        this.plantAttackSpeed = plantAttackSpeed;
        this.zombieHealth = zombieHealth;
        this.zombieSpeed = zombieSpeed;
        this.zombieDamage = zombieDamage;
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
            setImage ("settings-pressed.png");
            TitleScreen.stopMusic();
            timer.mark();
            pressed.play();
            timerStarted = true;
        }
        else
        {
            setImage ("settings.png");
        }
 
        if(timer.millisElapsed() > 100 && timerStarted)
        {
            Greenfoot.setWorld (new SettingsScreen(plantHealth, plantDamage, plantAttackSpeed, zombieHealth, zombieSpeed, zombieDamage));
        }
    }
}