import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for repeater packet.
 */
public class RepeaterPacket extends SeedPacket
{
    private Repeater r;
    
    /**
     * Initializes repeater packet
     */
    public RepeaterPacket(int finalY)
    {
        super(finalY);
        r = new Repeater();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(r, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        r.setIsInvisible();
    }
}