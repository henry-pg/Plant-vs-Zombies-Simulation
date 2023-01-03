import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Seedpacket is a Greenfoot Actor that will demonstrate where a plant will spawn. It will fall from the sky, land on a lawn tile and slowly lose transparency over the course of a few seconds. 
 * 
 * @author Johnathan, Henry 
 * @version April 2022
 */
public abstract class SeedPacket extends SuperSmoothMover
{
    protected double speed;
    protected boolean inWorld;
    protected int finalY;
    protected int actCounter;
    protected GreenfootImage image;
    protected boolean runOnce;
    protected static boolean gameOver = false;
    
    /**
     * Initializes seedpacket. 
     * 
     * @param finalY     The y-value it will stop itself and slowly removes the transparency.
     */
    public SeedPacket(int finalY)
    {
        speed = 1 + Math.random();
        inWorld = true;
        actCounter = 150;
        this.finalY = finalY;
        runOnce = false;
    }
    
    /**
     * It will contrinue to drop until its final y destination is reached unless the game is over
     */
    public void act()
    {
        if (inWorld && !gameOver)
        {   
            drop();
        }
    }
    
    /**
     * Checks if the current y-coord is above the final y-coord. If it is, it will continue to drop, else, it will stop and slowly lose transparency
     */
    public void drop()
    {
        //where we dropping bois??
        if (getY() < finalY && getY() + speed <= finalY)
        {
            setLocation(getX(), getY() + speed);
        }
        else// if (getY() >= finalY)
        {
            if (actCounter == 70)
            {
                spawnPlant();
            }

            if (actCounter > 0 && getImage().getTransparency() > 2)
            {
                actCounter--;
                this.getImage().setTransparency(getImage().getTransparency() - 2);
            }
            else
            {
                ZombieWorld m = (ZombieWorld)getWorld();
                m.removeObject(this);
            }
        }
    }

    /**
     * A contract to spawn the respective plant.
     */
    public abstract void spawnPlant();
    
    /**
     * Sets game over to true.
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }
    
    /**
     * Sets game over to false.
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
}