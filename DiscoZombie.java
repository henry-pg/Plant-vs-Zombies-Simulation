import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Refer to the Zombie super class. DiscoZombie is a Greenfoot Actor that is a tanky zombie. It spawns in DancerZombies through its groovy moves. It will stop for a second to spawn in the Dancers. The spawn Dancer dance is random.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class DiscoZombie extends Zombie
{
    private SimpleTimer spawnDancersTimer = new SimpleTimer ();
    private SimpleTimer cooldownTimer = new SimpleTimer();
    private boolean spawningDancers = false;
    private boolean spawnedDancers = false;
    private boolean timerStarted = false;
    
    /**
     * Constructor for DiscoZombie's values
     */
    public DiscoZombie()
    {
        super(0.5, 125, 25);
    }
    
    /**
     * Refer to the Zombie act method. It has a chance to spawn Dancer Zombies. Once it spawns the zombies, there is a cooldown before it can spawn another set. 
     */
    public void act()
    {
        // adds in the multiplier values 
        if (firstTime2)
        {
            firstTime2 = false;
            maxSpeed *= ((ZombieWorld) getWorld()).getZombieSpeed();
            speed = maxSpeed;
            maxHealth = (int) (maxHealth * (((ZombieWorld) getWorld()).getZombieHealth()));
            health = maxHealth;
            damagePerChew = (int) (damagePerChew * ((ZombieWorld) getWorld()).getZombieDamage());
        }
        
        // if it is still alive and the game is not over
        actCounter++;
        if (alive && !gameOver)
        {
            // snowstorm will end after x seconds
            if (snowStorm && snowyTimer.millisElapsed() < 7000)
            {
                snowy = true;
            }
            else
            {
                snowy = false;
            }
            
            if (snowyTimer.millisElapsed() > 7000)
            {
                snowStorm = false;
            }
            
            // changes the speed based on its status effects 
            if (!snowy && !snowPeaSlowed && !spawningDancers)
            {
                speed = maxSpeed;
            }
            else if (!spawningDancers)
            {
                if (snowy && !snowPeaSlowed)
                {
                    speed = maxSpeed / 1.55;
                }
                else if (!snowy && snowPeaSlowed)
                {
                    speed = maxSpeed / 1.75;
                }
                else if (snowy && snowPeaSlowed)
                {
                    speed = maxSpeed / 2.25;
                }
            }
            
            // counts the time until the slow debuff is gone
            if (snowPeaSlowed && !snowTimerStarted && snowHit)
            {
                snowTimer.mark();    
                snowTimerStarted = true;
                snowHit = false;
            }
            else if (snowPeaSlowed && snowTimerStarted && snowHit)
            {
                snowTimer.mark();
                snowHit = false;
            }
 
            if (snowPeaSlowed && snowTimer.millisElapsed() >= 1000 && snowTimerStarted)
            {
                snowPeaSlowed = false;
                speed = maxSpeed;
                snowTimerStarted = false;
            }
            
            // counts the time until the poison debuff is gone and counts the time of the poison tick damage
            if (poisoned && !poisonTimerStarted && poisonHit)
            {
                poisonTimer.mark();
                poisonTick.mark();
                poisonTimerStarted = true;
                poisonHit = false;
            }
            else if (poisoned && poisonTimerStarted && poisonHit)
            {
                poisonTimer.mark();
                poisonHit = false;
            }
 
            if (poisoned && (int)(poisonTick.millisElapsed() / 500) == 1 && poisonTimerStarted)
            {
                poisonTick.mark();
                takeDamage (poisonDmg);
            }
 
            if (poisoned && poisonTimer.millisElapsed() >= 1500 && poisonTimerStarted)
            {
                poisoned = false;
                poisonTimerStarted = false;
            }
 
            // will walk when there is no plants around or the plant has not spawned in yet. Will chew otherwise
            if (!isTouching(Plant.class) && getX() > 200) //getOneIntersectingObject(Plant.class) == null
            {
                walk (speed);
            }
            else if (getX() > 200 && getOneIntersectingObject(Plant.class) != null && ((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible())
            {
                walk (speed);
            }
            else if (isTouching(Plant.class) && actCounter % 60 == 0 && !(((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible()))
            {
                chew ((Plant)getOneIntersectingObject(Plant.class), damagePerChew);
            }
            
            // spawns dancers
            if (getX() > 300 && Greenfoot.getRandomNumber (200) == 1 && !spawnedDancers)
            {
                stop();
                spawnedDancers = true;
            }
 
            if (spawningDancers && spawnDancersTimer.millisElapsed() >= 1000)
            {
                spawnDancers();
                speed = maxSpeed;
            }
 
            if (spawnedDancers && !timerStarted)
            {
                cooldownTimer.mark();
                timerStarted = true;
            }
            
            // has a 8 sec cooldown before it can spawn in another set
            if (cooldownTimer.millisElapsed() >= 8000 && spawnedDancers)
            {
                spawnedDancers = false;
                timerStarted = false;
            }
            
            // changes the images based on the effects
            if (poisoned)
            {
                setImage (getName() + "-poisoned.png");
            }
            else if (snowy || snowPeaSlowed)
            {
                setImage (getName() + "-frozen.png");
            }
            else
            {
                setImage (getName() + ".png");
            }
            die();
        }
        
        // will head towards the door after clearing the whole row
        if (alive && getX() <= 200)
        {
            if (firstTime)
            {
                if (getY() <= 130)
                {
                    zombieLaneNumber = 1;
                }
                else if (getY() <= 192 && getY() >= 152)
                {
                    zombieLaneNumber = 2;
                }
                else if (getY() >= 225 && getY() <= 265)
                {
                    zombieLaneNumber = 3;
                }
                else if (getY() >= 300 && getY() <= 340)
                {
                    zombieLaneNumber = 4;
                }
                else if (getY() >= 365 && getY() <= 405)
                {
                    zombieLaneNumber = 5;
                }
                firstTime = false;
            }
 
            if (actCounter % 3 == 0)
            {
                if (actCounter % 10 == 0)
                {
                    if (rotateRight)
                    {
                        setRotation(10);
                        rotateRight = false;
                    }
                    else
                    {
                        setRotation(-10);
                        rotateRight = true;
                    }
                }
                if (zombieLaneNumber == 1)
                {
                    if (getX() > 124)
                    {
                        setLocation (getX() - 1, getY());
                    }
                    else if (getY() < 252)
                    {
                        setLocation (getX(), getY() + 1);
                    }
                }
                else if (zombieLaneNumber == 2)
                {
                    if (getX() > 124)
                    {
                        setLocation (getX() - 1, getY());
                    }
                    else if (getY() < 252)
                    {
                        setLocation (getX(), getY() + 1);
                    }
                }
                else if (zombieLaneNumber == 3)
                {
                    if (getX() > 124)
                    {
                        setLocation (getX() - 1, getY());
                    }
                    else if (getY() < 252)
                    {
                        setLocation (getX(), getY() + 1);
                    }
                }
                else if (zombieLaneNumber == 4)
                {
                    if (getX() > 124)
                    {
                        setLocation (getX() - 1, getY());
                    }
                    else if (getY() > 252)
                    {
                        setLocation (getX(), getY() - 1);
                    }
                }
                else if (zombieLaneNumber == 5)
                {
                    if (getX() > 124)
                    {
                        setLocation (getX() - 1, getY());
                    }
                    else if (getY() > 252)
                    {
                        setLocation (getX(), getY() - 1);
                    }
                }
            }
        }
    }
    
    /**
     * Spawns dancers. Checks which lane DiscoZombie is in and determines whether it will spawn 3 or 4 Dancers (due to the adjacent lanes not being there in the top and bottom row).
     */
    public void spawnDancers()
    {
        spawningDancers = false;
        ZombieWorld world = (ZombieWorld)getWorld();
        if (getY() >= 80 && getY() <= 120)
        {
            world.addObject(new DancerZombie(), getX(), getY() + 70);
        }
        else if (getY() >= 365 && getY() <= 405)
        {
            world.addObject(new DancerZombie(), getX(), getY() - 70);
        }
        else
        {
            world.addObject(new DancerZombie(), getX(), getY() - 70);
            world.addObject(new DancerZombie(), getX(), getY() + 70);
        }
        world.addObject(new DancerZombie(), getX() - 70, getY());
        world.addObject(new DancerZombie(), getX() + 70, getY());
    }
    
    /**
     * Stops the DiscoZombie from moving and changes the sprite to a DancingDisco
     */
    public void stop()
    {
        speed = 0;
        spawnDancersTimer.mark();
        spawningDancers = true;
        setRotation (0);
        setImage ("discoZombie2.png");
    }
    
    /**
     * Allows the zombie to walk in a janky way.
     * 
     * @param speed     The speed of which the zombie will move
     */
    public void walk (double speed)
    {
        if (speed != 0)
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
                    setRotation(-10);
                    rotateRight = true;
                }
            }
            move (-speed);
        }
    }
    
    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public String getName()
    {
        if (spawningDancers)
        {
            return "discoZombie2";
        }
        else
        {
            return "discozombie";
        }
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
            alive = false;
            if (spawningDancers)
            {
                z.addObject (new MoreDeadDisco2(), getX(), getY() + 20);
            }
            else
            {
                z.addObject (new MoreDeadDisco(), getX(), getY() + 20);
            }
            getWorld().removeObject(this);
        }
    }
}