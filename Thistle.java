import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * This is the Projectile that the HomingThistle shoots.
 * 
 * @author Ethan
 * @version April 2022
 */
public class Thistle extends Projectile
{
    // Declare variables
    protected ArrayList <Zombie> zombie;
    protected Zombie target;
    protected int initialDirectionTimer;

    /**
     * Constructor for Thistle. Sets the Thistle's speed, damage, and initial rotation.
     */
    public Thistle ()
    {
        speed = 2;
        damage = 20;
        this.setRotation(270);
        initialDirectionTimer = 10;
        firstTime = false;
    }

    /**
     * Act method for Thistle. The thistle initially travels vertically as it 
     * exits the Homing Thistle and then turns after 10 acts. It then chooses
     * a Zombie as a target and tracks it. When it hits its target, it deals
     * damage to it and removes itself.
     */
    public void act()
    {
        // Set the damage for the Thistle
        if (firstTime == false){
            damage = (int)(damage *((ZombieWorld)(getWorld())).getPlantDamage());
            firstTime = true;
        }
        
        if (!gameOver)
        {
            // Chooses a Zombie to track and follows it
            if (initialDirectionTimer == 0)
            {
                trackZombie();
                super.act();
            }
            // Travels vertically when it first exits the Homing Thistle
            else
            {
                move(speed);
                initialDirectionTimer--;
            }
        }
    }

    /**
     * Method to choose a Zombie to track and move towards it.
     */
    protected void trackZombie ()
    {
        // Declare variables
        double closestTargetDistance;
        double distanceToTarget;

        // Create an arrayList to identify and store all zombies around
        zombie = (ArrayList)getObjectsInRange(700, Zombie.class);

        if (zombie.size() > 0)
        {
            // Chooses an initial target and gets the distance from the target
            target = zombie.get(0);
            closestTargetDistance = ZombieWorld.getDistance(this, target);
            
            // Goes through each zombie in the arrayList and checks the distance
            // If the zombie is closer than the current target, begin to track
            // the new target
            for (Zombie o : zombie)
            {
                if (o!=null && o.getX() > this.getX()) {
                    distanceToTarget = ZombieWorld.getDistance(this, o);
                    if (distanceToTarget < closestTargetDistance)
                    {
                        target = o;
                        closestTargetDistance = distanceToTarget;
                    }
                } 
            }
            
            // Turn towards the target and move
            turnTowards(target.getX(), target.getY());
            move(speed);
        }
    }
}