import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the base of all zombie classes. The zombies' goals are to reach the door on the other side.
 * They will eat plants who get in their way. After taking enough damage, they will die. It can be affected by both poison and frozen debuffs which do tick damage over time and slows down their attack and walk speed respectively over the course of time affected.
 * If the zombie is affected by poison and gets frozened, the poison will be removed. If the zombie is affected by frozen and gets hit by poison, the frozen will be removed. The frozen debuff can be stacked with the Snowstorm slow which will reduce their walk speed more.
 * If the zombie gets hit by the status effect they currently have, their effect gets renewed. Zombie sometimes would make some groans. If the game is over, it will stop doing whatever it is doing unless it's x-coord is similar to CrazyDave's.
 * <p>
 * @author Johnathan, Charis
 * @version April 2022
 */
public abstract class Zombie extends SuperSmoothMover
{
    protected double speed;
    protected double maxSpeed;
    protected boolean alive;
    protected int health;
    protected int maxHealth;
    protected int damagePerChew;
    protected int actCounter;
    protected SimpleTimer snowTimer = new SimpleTimer();
    protected SimpleTimer poisonTimer = new SimpleTimer();
    protected SimpleTimer poisonTick = new SimpleTimer();
    protected boolean snowPeaSlowed = false;
    protected boolean snowTimerStarted = false;
    protected boolean poisonTimerStarted = false;
    protected boolean poisoned = false;
    protected int poisonDmg = 0;
    protected boolean rotateRight;
    protected boolean chewRotationLeft = false;
    protected boolean snowy = false;
    protected int snowActsLeft = 0;
    protected static int snowActsLeft2 = 0;
    protected static SimpleTimer snowyTimer = new SimpleTimer();
    protected static boolean snowStorm = false;
    protected int zombieLaneNumber = -1;
    protected boolean firstTime = true;
    protected boolean firstTime2 = true;
    protected static boolean gameOver = false;
    protected boolean gotHit;
    protected boolean poisonHit = false;
    protected boolean snowHit = false;
    
    GreenfootSound zombSound1 = new GreenfootSound("brains1.wav");
    GreenfootSound zombSound2 = new GreenfootSound("brains2.wav");
    GreenfootSound zombSound3 = new GreenfootSound("groan.wav");
    GreenfootSound munchSound1 = new GreenfootSound("munch1.wav");
    GreenfootSound munchSound2 = new GreenfootSound("munch2.wav");
    GreenfootSound munchSound3 = new GreenfootSound("munch3.wav");

    /**
     * Main constructor for the Zombie class
     */
    public Zombie(double maxSpeed, int maxHealth, int damagePerChew)
    {
        alive = true;
        this.maxSpeed = maxSpeed;// * (((ZombieWorld) getWorld()).getZombieSpeed());
        this.speed = this.maxSpeed;
        this.maxHealth = maxHealth;// * (((ZombieWorld) getWorld()).getZombieHealth()));
        this.health = this.maxHealth;
        this.damagePerChew = damagePerChew;// * (((ZombieWorld) getWorld()).getZombieDamage()));
        gotHit = false;

        // The zombie will either start on its back foot or the front
        if (Greenfoot.getRandomNumber(2) == 1)
        {
            rotateRight = true;            
        }
        else
        {
            rotateRight = false;
        }
        actCounter = 0;
        
        zombSound1.setVolume(80);
        zombSound2.setVolume(80);
        zombSound3.setVolume(35);
        munchSound1.setVolume(65);
        munchSound2.setVolume(65);
        munchSound3.setVolume(75);
    }

    /**
     * Makes the zombie walk, chew, and take damage. It has status effects, and its 
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
            if (!snowy && !snowPeaSlowed)
            {
                speed = maxSpeed;
            }
            else
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
                setImage (getName() + ".png");
                poisonTimerStarted = false;
            }

            // will walk when there is no plants around or the plant has not spawned in yet. Will chew otherwise
            if (!isTouching(Plant.class) && getX() > 160) 
            {
                walk (speed);
            }
            else if (getX() > 160 && getOneIntersectingObject(Plant.class) != null && ((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible())
            {
                walk (speed);
            }
            else if (isTouching(Plant.class) && actCounter % 60 == 0 && !(((Plant)getOneIntersectingObject(Plant.class)).getIsInvisible()))
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
            makeZombieSound(1);
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

            //turnTowards (100, 246);
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
     * For every Zombie subclass, it should check if their health is below or equal to 0 and will remove the 
     * zombie from the world if it fulfills the requirement.
     */
    public abstract void die();

    /**
     * Allows the zombie to walk in a janky way.
     * 
     * @param speed     The speed of which the zombie will move
     */
    public void walk (double speed)
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
        //setLocation (getX() - speed, getY());
        move (-speed);
    }

    /**
     * Chews the plant. It will rock back and forth almost like it is taking a big munch every time. The zombie will cause the plant's health to diminish.
     * 
     * @param plant         The plant that will be taking the damage
     * @param chewDamage    The amount of damage per chew
     */
    public void chew (Plant plant, int chewDamage)
    {
        if (!snowPeaSlowed)
        {
            if (actCounter % 15 == 0)
            {
                if (!chewRotationLeft)
                {
                    setRotation(-25);
                    makeZombieSound(2);
                    plant.munched (chewDamage);
                    chewRotationLeft = true;
                }
                else
                {
                    setRotation(0);
                    chewRotationLeft = false;
                }
            }
        }
        else
        {
            if (actCounter % 25 == 0)
            {
                if (!chewRotationLeft)
                {
                    setRotation(-25);
                    makeZombieSound(2);
                    plant.munched (chewDamage);
                    chewRotationLeft = true;
                }
                else
                {
                    setRotation(0);
                    chewRotationLeft = false;
                }
            }
        }
    }

    /**
     * Will make the zombie take damage
     * 
     * @param damage    The amount of damage taken
     */
    public void takeDamage (int damage)
    {
        if (alive)
        {
            health -= damage;
            gotHit = true;
        }
    }

    /**
     * Will slow the zombie's attack speed and movement speed and will replace the poison status effect with the slow.
     */
    public void slowMeDown()
    {
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
            speed = maxSpeed;
            snowTimerStarted = false;
        }
    }

    /**
     * Changes the value of snowy.
     * 
     * @param snowy     Changes the value of snowy in the instance
     */
    public void setSnowy (boolean snowy)
    {
        this.snowy = snowy;
    }

    /**
     * Sets the length of the snowstorm.
     * 
     * @param acts      The length of the snowstorm
     */
    public static void setSnowActs (int acts)
    {
        snowActsLeft2 = acts;
    }

    /**
     * Starts the snowstorm timer.
     */
    public static void startSnowTimer ()
    {
        snowyTimer.mark();
    }

    /**
     * Decreases the snow acts.
     */
    public static void decreaseSnowActs ()
    {
        snowActsLeft2--;
    }

    /**
     * Changes the value of snowStorm.
     * 
     * @param snowstorm     The value of snowStorm 
     */
    public static void setSnowStorm (boolean snowstorm)
    {
        snowStorm = snowstorm;
    }

    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public abstract String getName();
    
    /**
     * Sets gameOver to true
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }

    /**
     * Sets gameOver to false
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
    
    /**
     * Randomly makes a zombie sound
     * 
     * @param soundType     1 is zombie groans and 2 is zombie eat sounds
     */
    public void makeZombieSound(int soundType)
    {
        if (soundType == 1)
        {
            int soundPicker = Greenfoot.getRandomNumber(10000);
            if (soundPicker == 0)
            {
                zombSound1.play();
            }
            else if (soundPicker == 1)
            {
                zombSound2.play();
            }
            else if (soundPicker == 2)
            {
                zombSound3.play();
            }
        } 
        else if (soundType == 2)
        {
            int soundPicker = Greenfoot.getRandomNumber(3);
            if (soundPicker == 0)
            {
                munchSound1.play();
            }
            else if (soundPicker == 1)
            {
                munchSound2.play();
            }
            else if (soundPicker == 2)
            {
                munchSound3.play();
            }
        }
    }
}