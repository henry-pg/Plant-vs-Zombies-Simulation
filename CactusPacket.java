import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for cactus packet.
 */
public class CactusPacket extends SeedPacket
{
    private Cactus c;
    
    /**
     * Initializes cactus packet
     */
    public CactusPacket(int finalY)
    {
        super(finalY);
        c = new Cactus();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(c, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        c.setIsInvisible();
    }
}