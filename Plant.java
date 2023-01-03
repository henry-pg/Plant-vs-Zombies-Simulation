import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * This is the super class for all of the plant subclasses. This super class contains all of the essential traits and behaviours.
 * 
 * @author (Henry, Ethan, Johnathan)
 * @version (April 2022)
 */
public abstract class Plant extends Actor
{
    protected int health;
    protected int ageCounter;
    protected int attackRate;
    protected GreenfootImage plantImage;
    protected GreenfootImage originalImage;
    protected int imageHeight;
    protected int imageWidth;
    protected int initialHeight;
    protected int initialWidth;
    protected boolean isInvisible;
    protected int shootingCount;
    protected StatBar healthStat;
    protected boolean firstTime;
    protected GreenfootSound growing;
    public static int maturedAge = 60;
    protected GreenfootImage frozenImage;
    protected GreenfootImage normalImage;
    protected int shootingRate;
    protected static boolean gameOver = false;
    protected int masterShootingRate;
    protected GreenfootSound plantShot;
    
    /**
     * Constructor for plants. Sets up universal traits that are necessary for all plants. 
     */
    public Plant()
    {
        //values and objects
        health = 100;
        ageCounter = 0;
        isInvisible = true;
        getImage().setTransparency(0);
        healthStat = new StatBar (health, health, this, 44, 5, 38, Color.GREEN, Color.RED, true);
        firstTime = true;
        
        //sounds
        plantShot = new GreenfootSound("shot2.wav");
        plantShot.setVolume(70);
        growing = new GreenfootSound ("plantgrow.wav");
        growing.setVolume (65);
    }
    
    /**
     * Another constructor for plants. However, this one enables the ability to set a health value in the parameter.
     * 
     * @param health The health of the plant is set to this parameter. 
     */
    public Plant(int health)
    {
        this.health = health;
        ageCounter = 0;
        isInvisible = true;
        getImage().setTransparency(0);
        growing = new GreenfootSound ("plantgrow.wav");
        growing.setVolume (65);
        firstTime = true;
        plantShot = new GreenfootSound("shot.wav");
        plantShot.setVolume(80);
    }
    
    /**
     * Act method for plants that deals with growing, taking damage, and attacking.
     */
    public void act()
    {
        //runs if the plant is not invisible and game is not over
        if (!isInvisible && !gameOver)
        {
            // we had to do this because addedToWorld method was not working properly for us
            // initializes some values that can only be initialized when object is in the world
            if (firstTime) 
            {
                health = (int)(health *((ZombieWorld)(getWorld())).getPlantHealth());
                healthStat = new StatBar (health, health, this, 44, 5, 38, Color.GREEN, Color.RED, true);
                getWorld().addObject(healthStat, 0, 0);
                firstTime = false;
            }
            
            getImage().setTransparency(255);
            ageCounter++;
            
            //this where the normal behaviour of the plant is
            if (ageCounter > 60)
            {
                // if a zombie is detected in the lane, the plant attacks and a shooting sound is played
                if (detectZombieInLane())
                {
                    attack();
                }
            }
            else
            {
                grow(); //plant grows if it is less than 60 acts old
            }
            
            //updates health
            if (ageCounter % 10 == 0)
            {
                healthStat.update(health);
            }
            
            //plants die if health is less than 0
            if (health <= 0)
            {
                die();
            }
        }
    }
 
    /**
     * Method for plants to visually grow after seed packet is dropped onto the ground. This works by constantly rescaling the plant
     * image bigger and bigger until it is the right size.
     */
    public void grow()
    {
        if (initialHeight < imageHeight)
        {
            initialHeight ++;
        }
        growing.play();
        if (initialWidth < imageWidth)
        {
            initialWidth ++;
        }
        setImage(new GreenfootImage(originalImage));
        getImage().scale(initialWidth,initialHeight);
    }
    
    /**
     * This method removes the plant from the world.
     */
    public void die()
    {
        getWorld().removeObject(this);
    }
    
    /**
     * abstract method for plants to attack.
     */
    public abstract void attack();
    
    /**
     * Method for plants to take damage.
     */
    public void munched(int damage)
    {
        health -= damage;
    }
    
    /**
     * A setter method to set the attack speed of the plant.
     * 
     * @param newAttackSpeed The attack speed of the plant is set to this parameter value.
     */
    public void setAttackSpeed(int newAttackSpeed)
    {
        attackRate = newAttackSpeed;
    }
    
    /**
     * A method to set the isInvisible boolean to false.
     */
    public void setIsInvisible()
    {
        isInvisible = false;
    }
    
    /**
     * A method to return whether the isInvisible boolean is true or false.
     * 
     * @return boolean Returns the isInvisible boolean.
     */
    public boolean getIsInvisible()
    {
        return isInvisible;
    }
    
    /**
     * A method to detect if there is a zombie in the lane. This method is an altered version of Mr. Cohen's code 
     * from the vehicle simulation project local collision detection slides. 
     * 
     * @return boolean returns true if a zombie is in the lane, false if there are no zombies.
     */
    public boolean detectZombieInLane()
    {
        int distanceToEnd = getWorld().getWidth() - getX();
        boolean isThereZombie = false;
        
        for (int i = getImage().getWidth()/2;i < distanceToEnd;i+=5)
        {
            Zombie z = (Zombie)getOneObjectAtOffset(i, 0, Zombie.class);
            
            if (z != null)
            {
                isThereZombie = true;
            }
        }
        return isThereZombie;
    }
    
    /**
     * Method to set the frozen image of the plant.
     */
    public void setFrozenImage()
    {
        originalImage = frozenImage;
        if (ageCounter > 60)
        {
             setImage(originalImage);
        }
    }
    
    /**
     * Method to set the default image of the plant.
     */
    public void setNormalImage()
    {
        originalImage = normalImage;
        if (ageCounter > 60)
        {
            setImage(originalImage);
        }
    }
    
    /**
     * Abstract method for changes to plant traits because of the snow storm.
     */
    public abstract void frozenEffect();
    
    /**
     * Abstract method for changes to plant traits back to default after the snow storm.
     */
    public abstract void normalEffect();
    
    /**
     * Static method to set gameOver to true.
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }
    
    /**
     * Static method to set gameOver to true.
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
    
    /**
     * method that plays the shootingSound.
     */
    public void shootSound()
    {
        plantShot.play();
    }
}