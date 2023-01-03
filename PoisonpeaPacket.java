import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for peashooter packet.
 */
public class PoisonpeaPacket extends SeedPacket
{
    private Poisonpea p;
    
    /**
     * Initializes poisonpea packet
     */
    public PoisonpeaPacket(int finalY)
    {
        super(finalY);
        p = new Poisonpea();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(p, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        p.setIsInvisible();
    }
}