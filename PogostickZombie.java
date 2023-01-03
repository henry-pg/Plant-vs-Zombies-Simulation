import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Zombie super class. PogostickZombie is a Greenfoot Actor that is a quick zombie.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class PogostickZombie extends Zombie
{
    private boolean jumpedUp = false;
    private GreenfootSound[] boingSounds;
    private int boingSoundIndex = 0;
    
    /**
     * Constructor for BucketheadZombie's values
     */
    public PogostickZombie()
    {
        super(2, 175, 25);
        boingSounds = new GreenfootSound [20];
        for (int i = 0; i < 20; i++)
        {
            boingSounds[i] = new GreenfootSound("boing2.wav");
            boingSounds[i].setVolume(65);
        }
    }
    
    /**
     * Refer to the Zombie act method.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Allows the zombie to walk in a janky way.
     * 
     * @param speed     The speed of which the zombie will move
     */
    public void walk (double speed)
    {
        if (actCounter % 15 == 0)
        {
            if (rotateRight)
            {
                setRotation(10);
                rotateRight = false;
            }
            else
            {
                boingSounds [boingSoundIndex].play();
                boingSoundIndex++;
                if (boingSoundIndex > boingSounds.length - 1)
                {
                    boingSoundIndex = 0;
                }
                setRotation(-10);
                rotateRight = true;
            }
        }

        move (-speed);
    }
    
    /**
     * Chews the plant. It will jump up and down almost like it is stomping on the plant every time. The zombie will cause the plant's health to diminish.
     * 
     * @param plant         The plant that will be taking the damage
     * @param chewDamage    The amount of damage per chew
     */
    public void chew (Plant plant, int chewDamage)
    {
        if (!snowPeaSlowed)
        {
            if (actCounter % 5 == 0)
            {
                setRotation(0);
                if (jumpedUp)
                {
                    setLocation (getX(), getY() + 20);
                    plant.munched (chewDamage);
                    jumpedUp = false;
                }
                else
                {
                    setLocation (getX(), getY() - 20);
                    boingSounds [boingSoundIndex].play();
                    boingSoundIndex++;
                    if (boingSoundIndex > boingSounds.length - 1)
                    {
                        boingSoundIndex = 0;
                    }
                    jumpedUp = true;
                }
            }
        }
        else
        {
            if (actCounter % 8 == 0)
            {
                setRotation(0);
                if (jumpedUp)
                {
                    setLocation (getX(), getY() + 20);
                    plant.munched (chewDamage);
                    jumpedUp = false;
                }
                else
                {
                    setLocation (getX(), getY() - 20);
                    boingSounds [boingSoundIndex].play();
                    boingSoundIndex++;
                    if (boingSoundIndex > boingSounds.length - 1)
                    {
                        boingSoundIndex = 0;
                    }
                    jumpedUp = true;
                }
            }
        }
    }
    
    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public String getName()
    {
        return "pogozombie";
    }

    /**
     * Checks if its health is below or equal to 0 and will remove the 
     * zombie from the world if it fulfills the requirement.
     */
    public void die()
    {
        if (health <= 0)
        {
            ZombieWorld z = (ZombieWorld)getWorld();
            z.addObject (new MoreDeadPogo(), getX(), getY() + 30);
            alive = false;
            getWorld().removeObject(this);
        }
    }
}