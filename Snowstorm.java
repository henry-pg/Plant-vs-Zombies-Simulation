import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Snow storm class for the events. Slows down all zombies and plants shooting when spawned into world. This class 
 * also spawns in snow objects that simulate the typical snowfall in a snow storm. A snowflake on the top right corner 
 * is there to indicate the occurence of this event.
 * 
 * @author (Johnathan, Henry)
 * @version April 2022
 */
public class Snowstorm extends Effect
{
    private SimpleTimer timer = new SimpleTimer ();
    private boolean started = false;
    private int frequency;
    
    /**
     * Constructor that calls the super class' constructor
     */
    public Snowstorm()
    {
        super(480);
        frequency = 5; // the frequency at which the snow objects spawn;
    }
    
    /**
     * Act method that gets all plants and zombies objects and slows all of them down.
     * Also spawns snow objects based on the frequency. 
     */
    public void act()
    {
        
        if (!started)
        {
            timer.mark();
            started = true;
        }
        
        //slows down all plants
        ArrayList<Plant> plants = (ArrayList<Plant>) getWorld().getObjects (Plant.class);
        for (Plant p : plants)
        {
            p.setFrozenImage();
            p.frozenEffect();
        }
        
        //spawns snow Objects
        frequency--;
        if (frequency == 0)
        {
            drawSnow();
            frequency = 12;
        }
        
        //if 7 seconds have passed, all zombies and plants in world return to normal speed.
        if (timer.millisElapsed() > 7000)
        {
            for (Plant p: plants)
            {
                p.setNormalImage();
                p.normalEffect();
            }
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Added to world method that sets zombie methods
     * 
     * @param w This is an argument for the world.
     */
    public void addedToWorld (World w)
    {
        ArrayList<Zombie> zombies = (ArrayList<Zombie>) w.getObjects (Zombie.class);
        
        Zombie.startSnowTimer();
        Zombie.setSnowStorm (true);
    }
    
    /**
     * Generates the snow randomly
     */
    public void drawSnow ()
    {
        for (int i = 0; i< 8;i++)
        {
            getWorld().addObject(new Snow(),i*100 + Greenfoot.getRandomNumber(100)-80,0 + Greenfoot.getRandomNumber(30));
        }
    }
}
