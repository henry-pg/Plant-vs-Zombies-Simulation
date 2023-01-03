import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Refer to the Zombie super class. FootballZombie is a Greenfoot Actor that is a tanky zombie and charges in when it spawns. The charge speed accelerates over time until it reaches the capped speed. 
 * The charge insta kills any plant in the way, but it only works on a single plant. After the initial charge, it will start behaving like a normal zombie, walking and chewing.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class FootballZombie extends Zombie
{
    private double acceleration = 1;
    private double cappedSpeed = 7;
    private double initialSpeed = 1;
    private double noEffectsSpeed;
    private boolean charging = true;
    private int rotation;
    
    /**
     * Constructor for FootballZombie's values
     */
    public FootballZombie()
    {
        super(1, 325, 25);
        rotation = 0;
    }
    
    /**
     * Refer to the Zombie act method. Will charge until it hits a plant or reaches x = 200 (or lower) of which it will start walking.
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
            initialSpeed = speed;
            noEffectsSpeed = speed;
            cappedSpeed = cappedSpeed * (((ZombieWorld) getWorld()).getZombieSpeed());
            initialSpeed = initialSpeed * (((ZombieWorld) getWorld()).getZombieSpeed());
            noEffectsSpeed = noEffectsSpeed * (((ZombieWorld) getWorld()).getZombieSpeed());
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
            if (charging)
            {
                if (!snowy && !snowPeaSlowed)
                {
                    speed = noEffectsSpeed;
                }
                else
                {
                    if (snowy && !snowPeaSlowed)
                    {
                        speed = noEffectsSpeed / 1.55;
                    }
                    else if (!snowy && snowPeaSlowed)
                    {
                        speed = noEffectsSpeed / 1.75;
                    }
                    else if (snowy && snowPeaSlowed)
                    {
                        speed = noEffectsSpeed / 2.25;
                    }
                    setImage (getName() + "-frozen.png");
                }
            }
            else
            {
                if (!snowy && !snowPeaSlowed)
                {
                    speed = initialSpeed;
                }
                else
                {
                    if (snowy && !snowPeaSlowed)
                    {
                        speed = initialSpeed / 1.55;
                    }
                    else if (!snowy && snowPeaSlowed)
                    {
                        speed = initialSpeed / 1.75;
                    }
                    else if (snowy && snowPeaSlowed)
                    {
                        speed = initialSpeed / 2.25;
                    }
                    setImage (getName() + "-frozen.png");
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
                speed = noEffectsSpeed;
                setImage (getName() + "-frozen.png");
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
            
            // will charge/walk when there is no plants around or the plant has not spawned in yet. Will chew otherwise
            if (!isTouching(Plant.class) && charging && getX() > 200)
            {
                setRotation (-25);
                walk (speed);
            }
            else if (!isTouching(Plant.class) && !charging && getX() > 200)
            {
                walk (speed);
            }
            else if (getX() > 200 && charging && getOneIntersectingObject(Plant.class) != null && ((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible())
            {
                setRotation (-25);
                walk (speed);
            }
            else if (getX() > 200 && !charging && getOneIntersectingObject(Plant.class) != null && ((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible())
            {
                walk (speed);
            }
            else if (isTouching(Plant.class) && charging && !(((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible()))
            {
                removeTouching (Plant.class);
                charging = false;
            }
            else if (!charging && isTouching(Plant.class) && actCounter % 60 == 0 && !(((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible()))
            {
                chew ((Plant)getOneIntersectingObject(Plant.class), damagePerChew);
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
        if (alive && getX() <= 210)
        {
            if (firstTime)
            {
                charging = false;
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
     * Allows the zombie to walk in a janky way.
     * 
     * @param speed     The speed of which the zombie will move
     */
    public void walk (double speed)
    {
        if (charging)
        {
            if (actCounter % 15 == 0)
            {
                if (noEffectsSpeed < cappedSpeed)
                {
                    noEffectsSpeed += acceleration;
                    this.speed += acceleration;
                }
            }
            setLocation (getX() - speed, getY());
        }
        else
        {
            super.walk(initialSpeed);
        }
    }
    
    /**
     * Will slow the zombie's attack speed and movement speed and will replace the poison status effect with the slow.
     */
    public void slowMeDown()
    {
        speed = noEffectsSpeed / 1.75;  
        snowPeaSlowed = true;
        snowHit = true;
        if (poisoned)
        {
            poisoned = false;
            poisonTimerStarted = false;
        }
    }
    
    /**
     * Will apply the poison status effect onto the zombie which will take damage over time. It will replace the slow debuff with the poison.
     * 
     * @param poisonDmg     The amount of poison damage taken per tick
     */
    public void poison (int poisonDmg)
    {
        poisoned = true;
        this.poisonDmg = poisonDmg;
        poisonHit = true;
        if (snowPeaSlowed)
        {
            snowPeaSlowed = false;
            speed = noEffectsSpeed;
            snowTimerStarted = false;
        }
    }
    
    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public String getName()
    {
        return "footballzombie";
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
            z.addObject (new MoreDeadFootball(), getX(), getY() + 20);
            alive = false;
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Multiplier values for speed
     * 
     * @param multiplier    The multiplier for the speed
     */
    public void setSpeed (double multiplier)
    {
        cappedSpeed = cappedSpeed * multiplier;
        initialSpeed = initialSpeed * multiplier;
        noEffectsSpeed = noEffectsSpeed * multiplier;
        maxSpeed = maxSpeed * multiplier;
        speed = speed * multiplier;
    }
}