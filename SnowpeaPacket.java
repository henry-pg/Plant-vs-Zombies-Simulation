import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for repeater packet.
 */
public class SnowpeaPacket extends SeedPacket
{
    private Snowpea s;
    
    /**
     * Initializes snowpea packet
     */
    public SnowpeaPacket(int finalY)
    {
        super(finalY);
        s = new Snowpea();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(s, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        s.setIsInvisible();
    }
}