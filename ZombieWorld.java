import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieWorld extends World
{
    private int random;
    private GreenfootSound backgroundMusic, backgroundMusic2;
    private PlantController pController;
    private int spawnRate;
    private static GreenfootSound mainSong;
    private boolean apocalypse;
    private static boolean gameOver = false;
    private GreenfootSound apocalypseSound;

    private double plantHealth;
    private double plantDamage;
    private double plantAttackSpeed;
    private double zombieHealth;
    private double zombieSpeed;
    private double zombieDamage;

    private int minZombies = 10;
    private int currentZombies = 0;

    private SimpleTimer timer = new SimpleTimer();
    private boolean spawnedApocalypse = false;
    
    /**
     * Constructor for the zombie world. This constructor sets default values, sets a paint order, and also initializes game sounds.
     * 
     * @param plantHealth           The value each plant's base health is multiplied by.
     * @param plantDamage           The value each plant's base damage is multiplied by.
     * @param plantAttackSpeed      The value each plant's base attack speed is multiplied by.
     * @param zombieHealth          The value each zombie's base health is multiplied by.
     * @param zombieSpeed           The value each zombie's base speed is multiplied by.
     * @param zombieDamage          The value each zombie's base damage is multiplied by.
     */
    public ZombieWorld(double plantHealth, double plantDamage, double plantAttackSpeed, double zombieHealth, double zombieSpeed, double zombieDamage)
    {
        //values
        super(750, 450, 1);
        apocalypse = false;
        pController = new PlantController();
        addObject(pController, 2000,2000);
        addObject(new CrazyDave(), 124, 252); 
        addObject(new CrazyDot(), 124, 252);
        mainSong = new GreenfootSound ("Ingame1.wav");
        apocalypseSound = new GreenfootSound ("zombiesAreComing.wav");
        apocalypseSound.setVolume(100);
        mainSong.setVolume(35);
        mainSong.play();

        this.plantHealth = plantHealth;
        this.plantDamage = plantDamage;
        this.plantAttackSpeed = plantAttackSpeed;
        this.zombieHealth = zombieHealth;
        this.zombieSpeed = zombieSpeed;
        this.zombieDamage = zombieDamage; 

        setPaintOrder(Button.class, GameWinScreen.class, GameOverScreen.class, LosingScreen.class, Flag.class, Apocalypse.class, Snow.class,SeedPacket.class, Projectile.class, Zombie.class, CrazyDave.class, Plant.class, CrazyDot.class);
    }
    
    /**
     * Method that plays the music when the simulation is played.
     */
    public void started ()
    {
        mainSong.playLoop();
    }
    
    /**
     * Method that stops the music when the simulation is paused. 
     */
    public void stopped ()
    {
        mainSong.stop();
    }
    
    /**
     * Method to pause the music 
     */
    public static void stopMusic ()
    {
        mainSong.stop();
    }
    
    /**
     * The Act method of the zombie world. 
     */
    public void act()
    {
        if (!gameOver)
        {
            if (timer.millisElapsed() >= 120000 && !spawnedApocalypse && getObjects(Snowstorm.class).size() < 1)
            {
                spawnedApocalypse = true;
                apocalypseSound.play();
                addObject (new Apocalypse(), 375, 225);
                apocalypse = true;
                PlantController.apocalypseTrue();
            }
            spawn();
            if (Greenfoot.getRandomNumber(4000) == 0)
            {
                if (getObjects(Effect.class).size() < 1 && !spawnedApocalypse)
                {
                    addObject (new Snowstorm(), 700, 50);
                }
            }
        }
    }

    /**
     * Static method that gets the distance between the x,y coordinates of two Actors
     * using Pythagorean Theorum. This was shamelessly taken from Mr. Cohen's bug simulation code. 
     * 
     * @param a     First Actor
     * @param b     Second Actor
     * @return float
     */
    public static float getDistance (Actor a, Actor b)
    {
        double distance;
        double xLength = a.getX() - b.getX();
        double yLength = a.getY() - b.getY();
        distance = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
        return (float)distance;
    }
    
    /**
     * This is the method that deals with spawning all of the zombies in the world. 
     */
    private void spawn () 
    {
        int spawnY = 0;
        int row = Greenfoot.getRandomNumber(5);
        int zombieType;
        
        // Chance to spawn a zombie
        if (pController.columnsOfPlants() == 0)
        {
            spawnRate = 450; 
        }
        else if (pController.columnsOfPlants() == 1)
        {
            spawnRate = 150;
        }
        else if (pController.columnsOfPlants() == 2)
        {
            spawnRate = 150; 
        }
        else if (pController.columnsOfPlants() == 3)
        {
            spawnRate = 90; 
        }
        else if (pController.columnsOfPlants() == 4)
        {
            spawnRate = 65; 
        }
        else if (pController.columnsOfPlants() == 5)
        {
            spawnRate = 60; 
        }
        

        // which row it'll spawn in
        if (row == 0)
        {
            spawnY = 100;
        }
        else if (row == 1)
        {
            spawnY = 172;
        }
        else if (row == 2)
        {
            spawnY = 245;
        }
        else if (row == 3)
        {
            spawnY = 320;
        }
        else if (row == 4)
        {
            spawnY = 385;
        }
        
        //spawns the zombies based on the columns of plants that are in the game
        //In other words, the severity of the zombies is dependant on the number of columns of plants
        if (Greenfoot.getRandomNumber(spawnRate) == 0 && !apocalypse && !spawnedApocalypse)
        {
            if (pController.columnsOfPlants() == 0)
            {
                addObject(new NormalZombie(), 750, spawnY);
            }
            else if (pController.columnsOfPlants() == 1)
            {
                zombieType = Greenfoot.getRandomNumber(2);
                if (zombieType == 0)
                {
                    addObject(new NormalZombie(), 750, spawnY);
                }
                else if (zombieType == 1)
                {
                    addObject(new ConeheadZombie(), 750, spawnY);
                }
            }
            else if (pController.columnsOfPlants() == 2)
            {
                zombieType = Greenfoot.getRandomNumber(9);
                if (zombieType == 0)
                {
                    addObject(new NormalZombie(), 750, spawnY);
                }
                else if (zombieType == 1 || zombieType == 6)
                {
                    addObject(new ConeheadZombie(), 750, spawnY);
                }
                else if (zombieType == 2)
                {
                    addObject(new BucketheadZombie(), 750, spawnY);
                }
                else if (zombieType == 3 || zombieType == 7)
                {
                    addObject(new JetpackZombie(), 750, spawnY);
                }
                else if (zombieType == 4 || zombieType == 8)
                {
                    addObject(new BalloonZombie(), 750, spawnY);
                }
            }
            else if (pController.columnsOfPlants() == 3)
            {
                zombieType = Greenfoot.getRandomNumber(12);
                if (zombieType == 0 || zombieType == 6)
                {
                    addObject(new PogostickZombie(), 750, spawnY);
                }
                else if (zombieType == 1 || zombieType == 5)
                {
                    addObject(new ConeheadZombie(), 750, spawnY);
                }
                else if (zombieType == 2 || zombieType == 8 || zombieType == 11)
                {
                    addObject(new BucketheadZombie(), 750, spawnY);
                }
                else if (zombieType == 3 || zombieType == 9)
                {
                    addObject(new JetpackZombie(), 750, spawnY);
                }
                else if (zombieType == 4 || zombieType == 10)
                {
                    addObject(new BalloonZombie(), 750, spawnY);
                }
                
            }
            else if (pController.columnsOfPlants() == 4)
            {
                zombieType = Greenfoot.getRandomNumber(9);
                if (zombieType == 1)
                {
                    addObject(new ConeheadZombie(), 750, spawnY);
                }
                else if (zombieType == 2 || zombieType == 0)
                {
                    addObject(new BucketheadZombie(), 750, spawnY);
                }
                else if (zombieType == 3 || zombieType == 4)
                {
                    addObject(new JetpackZombie(), 750, spawnY);
                }
                else if (zombieType == 5 || zombieType == 8)
                {
                    addObject(new FootballZombie(), 750, spawnY);
                }
                else if (zombieType == 6)
                {
                    addObject(new DiscoZombie(), 750, spawnY);
                }
            }
            else if (pController.columnsOfPlants() == 5)
            {
                zombieType = Greenfoot.getRandomNumber(8);
                if (zombieType == 1 || zombieType == 2)
                {
                    addObject(new BucketheadZombie(), 750, spawnY);
                }
                else if (zombieType == 7)
                {
                    addObject(new ConeheadZombie(), 750, spawnY);
                }
                else if (zombieType == 3 || zombieType == 0 || zombieType == 4)
                {
                    addObject(new JetpackZombie(), 750, spawnY);
                }
                else if (zombieType == 5)
                {
                    addObject(new FootballZombie(), 750, spawnY);
                }
                else if (zombieType == 6)
                {
                    addObject(new DiscoZombie(), 750, spawnY);
                }
            }
        }
        //this runs if the apocalypse event is present. This spawns the zombies much faster for the period that the apocalypse lasts for
        else if (apocalypse)
        {
            if (currentZombies < minZombies || Greenfoot.getRandomNumber((int)(spawnRate / 1.50)) == 0)
            {
                if (currentZombies < minZombies)
                {
                    currentZombies++;
                }
                if (pController.columnsOfPlants() == 0)
                {
                    addObject(new NormalZombie(), 750, spawnY);
                }
                else if (pController.columnsOfPlants() == 1)
                {
                    zombieType = Greenfoot.getRandomNumber(2);
                    if (zombieType == 0)
                    {
                        addObject(new NormalZombie(), 750, spawnY);
                    }
                    else if (zombieType == 1)
                    {
                        addObject(new ConeheadZombie(), 750, spawnY);
                    }
                }
                else if (pController.columnsOfPlants() == 2)
                {
                    zombieType = Greenfoot.getRandomNumber(9);
                    if (zombieType == 0)
                    {
                        addObject(new NormalZombie(), 750, spawnY);
                    }
                    else if (zombieType == 1 || zombieType == 6)
                    {
                        addObject(new ConeheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 2)
                    {
                        addObject(new BucketheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 3 || zombieType == 7)
                    {
                        addObject(new JetpackZombie(), 750, spawnY);
                    }
                    else if (zombieType == 4 || zombieType == 8)
                    {
                        addObject(new BalloonZombie(), 750, spawnY);
                    }
                }
                else if (pController.columnsOfPlants() == 3)
                {
                    zombieType = Greenfoot.getRandomNumber(12);
                    if (zombieType == 0 || zombieType == 6)
                    {
                        addObject(new PogostickZombie(), 750, spawnY);
                    }
                    else if (zombieType == 1)
                    {
                        addObject(new ConeheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 2 || zombieType == 8 || zombieType == 11)
                    {
                        addObject(new BucketheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 3 || zombieType == 9)
                    {
                        addObject(new JetpackZombie(), 750, spawnY);
                    }
                    else if (zombieType == 4 || zombieType == 10)
                    {
                        addObject(new BalloonZombie(), 750, spawnY);
                    }
                    else if (zombieType == 5)
                    {
                        addObject(new FootballZombie(), 750, spawnY);
                    }
                }
                else if (pController.columnsOfPlants() == 4)
                {
                    zombieType = Greenfoot.getRandomNumber(9);
                    if (zombieType == 1)
                    {
                        addObject(new ConeheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 2 || zombieType == 0)
                    {
                        addObject(new BucketheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 3 || zombieType == 4)
                    {
                        addObject(new JetpackZombie(), 750, spawnY);
                    }
                    else if (zombieType == 5 || zombieType == 8)
                    {
                        addObject(new FootballZombie(), 750, spawnY);
                    }
                    else if (zombieType == 6)
                    {
                        addObject(new DiscoZombie(), 750, spawnY);
                    }
                }
                else if (pController.columnsOfPlants() == 5)
                {
                    zombieType = Greenfoot.getRandomNumber(8);
                    if (zombieType == 1 || zombieType == 2)
                    {
                        addObject(new BucketheadZombie(), 750, spawnY);
                    }
                    else if (zombieType == 3 || zombieType == 0 || zombieType == 4 || zombieType == 7)
                    {
                        addObject(new JetpackZombie(), 750, spawnY);
                    }
                    else if (zombieType == 5)
                    {
                        addObject(new FootballZombie(), 750, spawnY);
                    }
                    else if (zombieType == 6)
                    {
                        addObject(new DiscoZombie(), 750, spawnY);
                    }
                }
            }
        }
    }
    
    /**
     * A method that sets the apocalypse boolean to its parameter.
     * 
     * @param apocalypse Parameter that apocalypse boolean is set to.
     */
    public void setApocalypse (boolean apocalypse)
    {
        this.apocalypse = apocalypse;
    }
    
    /**
     * Static method that sets the gameOver boolean to true
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }
    
    /**
     * Static method that sets the gameOver boolean to false
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
    
    /**
     * Method that returns the plantHealth multiplier
     * 
     * @return double Returns plantHealth multiplier
     */
    public double getPlantHealth()
    {
        return plantHealth;
    }
    
    /**
     * Method that returns the plantDamage multiplier
     * 
     * @return double Returns plantDamage multiplier
     */
    public double getPlantDamage()
    {
        return plantDamage;
    }
    
    /**
     * Method that returns the attackSpeed multiplier
     * 
     * @return double Returns attackSpeed multiplier
     */
    public double getPlantAttackSpeed()
    {
        return plantAttackSpeed;
    }
    
    /**
     * Method that returns the zombieHealth multiplier
     * 
     * @return double Returns zombieHealth multiplier
     */
    public double getZombieHealth()
    {
        return zombieHealth;
    }
    
    /**
     * Method that returns the zombieDamage multiplier
     * 
     * @return double Returns zombieDamage multiplier
     */
    public double getZombieDamage()
    {
        return zombieDamage;
    }
    
    /**
     * Method that returns the zombieSpeed multiplier
     * 
     * @return double Returns zombieSpeed multiplier
     */
    public double getZombieSpeed()
    {
        return zombieSpeed;
    }
}