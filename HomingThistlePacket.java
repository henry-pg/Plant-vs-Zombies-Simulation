import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for homing thistle packet.
 */
public class HomingThistlePacket extends SeedPacket
{
    private HomingThistle h;
    
    /**
     * Initializes homing thistle packet
     */
    public HomingThistlePacket (int finalY)
    {
        super(finalY);
        h = new HomingThistle();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(h, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        h.setIsInvisible();
    }
}