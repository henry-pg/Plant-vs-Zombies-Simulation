import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to super class. Class for wallnut packet.
 */
public class WallnutPacket extends SeedPacket
{
    private Wallnut w;
    
    /**
     * Initializes wallnut packet
     */
    public WallnutPacket(int finalY)
    {
        super(finalY);
        w = new Wallnut();
    }
    
    /**
     * Will only add 1 respective plant in
     */
    public void act()
    {
        super.act();
        if(!runOnce)
        {
            getWorld().addObject(w, getX(),finalY);
            runOnce = true;
        }
    }
    
    /**
     * Makes plant invisible until the packet hits the final y-coords
     */
    public void spawnPlant()
    {
        w.setIsInvisible();
    }
}