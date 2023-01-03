import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * CrazyDave is a Greenfoot Actor that shoots zombies every few seconds. It will aim at the target zombie when it is ready to shoot.
 * 
 * @author Ethan, Johnathan
 * @version April 2022
 */
public class CrazyDave extends Actor
{
    private int damage;
    private int health;
    private Zombie target;
    private ArrayList <Zombie> zombies;
    private int fireTimer;
    private boolean alive;
    private int pickSound;
    private boolean apocalypseInWorld;
    private boolean touchingZombie;
    private boolean firstTime;
    private boolean lossFinished;
    private boolean finishedNo;

    GreenfootSound daveSounds[] = new GreenfootSound[8];
    GreenfootSound daveNo = new GreenfootSound("no.wav");
    GreenfootSound gunshot = new GreenfootSound("gunshot.wav");
    GreenfootSound win = new GreenfootSound ("win2.wav");
    GreenfootSound loss = new GreenfootSound ("loss.wav");
    
    /**
     * Initializes CrazyDave's values.
     */
    public CrazyDave ()
    {
        getImage().setTransparency(0);
        fireTimer = 0;
        alive = true;
        touchingZombie = false;
        apocalypseInWorld = false;
        firstTime = true;
        lossFinished = false;
        finishedNo = false;
        
        // array of crazydave sounds
        for (int i = 0; i<daveSounds.length; i++)
        {
            daveSounds[i] = new GreenfootSound ("crazydave" + i + ".wav");
        }
        
        for (int i = 0; i<daveSounds.length; i++)
        {
            daveSounds[i].setVolume(70);
        }
        
        win.setVolume(70);
        loss.setVolume(75);
        daveNo.setVolume(80);
        gunshot.setVolume(55);
    }
    
    /**
     * CrazyDave will spawn in with his transparency increasing until he reaches to full transparency. He will then aim and shoot at zombies when his cooldown timer resets. He will randomly make noises thorughout the simulation.
     * CrazyDave also checks the state of the game - the win and loss conditions. If he is still alive after apocalypse, he will initiate the game win and a jingle. If he is dead in any point of the simulation, he will initiate the game loss and a noise.
     */
    public void act()
    {
        if (alive)
        {
            Zombie.gameOverFalse();
            SeedPacket.gameOverFalse();
            Projectile.gameOverFalse();
            PlantController.gameOverFalse();
            Plant.gameOverFalse();
            ZombieWorld.gameOverFalse();
            PlantController.apocalypseFalse();
        }

        if (getImage().getTransparency() < 255)
        {
            getImage().setTransparency(getImage().getTransparency() + 5);
        }

        if (findTarget() && alive)
        {
            if (fireTimer <= 0)
            {
                turnTowards(target.getX()-30, target.getY()+25);
                shootZombie();
                fireTimer = 90;
            }
            fireTimer--;
        }
        
        if (touchingZombie && !finishedNo)
        {
            if (!lossFinished)
            {
                loss.play();
                lossFinished = true;
            }
            
            if (!loss.isPlaying() && lossFinished)
            {
                daveNo.play();
                finishedNo = true;
            }
        }
        
        if (touchingZombie && firstTime)
        {
            setRotation (0);
            setImage ("CRAZYDAVEsmall-dead.png");
            alive = false;
            firstTime = false;
            if (getWorld().getObjects(LosingScreen.class).size() < 1)
            {
                ZombieWorld.stopMusic();
                getWorld().addObject(new LosingScreen(), 375, 225);
            }
            Zombie.gameOverTrue();
            SeedPacket.gameOverTrue();
            Projectile.gameOverTrue();
            PlantController.gameOverTrue();
            Plant.gameOverTrue();
            ZombieWorld.gameOverTrue();
        }

        if (getWorld().getObjects(Apocalypse.class).size() == 1)
        {
            apocalypseInWorld = true;
        }

        if (apocalypseInWorld)
        {
            if (getWorld().getObjects(MoreDeadZombie.class).size() < 1 && getWorld().getObjects(Zombie.class).size() < 1)
            {
                if (getWorld().getObjects(GameWinScreen.class).size() < 1)
                {
                    ZombieWorld.stopMusic();
                    win.play();
                    getWorld().addObject(new GameWinScreen(), 375, 225);
                }
                Zombie.gameOverTrue();
                SeedPacket.gameOverTrue();
                Projectile.gameOverTrue();
                PlantController.gameOverTrue();
                Plant.gameOverTrue();
                ZombieWorld.gameOverTrue();
            }
        }
        playSound();
    }
    
    /**
     * Returns his alive variable
     * 
     * @return boolean  True if CrazyDave is alive and False if not.
     */
    public boolean getAlive()
    {
        return alive;
    }
    
    /**
     * Sets his value 'touchingZombie' to the parameter's value.
     * 
     * @param touchingZombie    Whether CrazyDot (CrazyDot is in CrazyDave) is touching a zombie
     */
    public void setTouchingZombie (boolean touchingZombie)
    {
        this.touchingZombie = touchingZombie;
    }
    
    /**
     * CrazyDave will seek the nearest target and rotating towards it.
     */
    public boolean findTarget() 
    {
        double distanceToTarget;
        double closestTargetDistance;
        zombies = (ArrayList)getObjectsInRange(700, Zombie.class);
        if (zombies.size() > 0)
        {
            target = zombies.get(0);
            closestTargetDistance = ZombieWorld.getDistance(this, target);
            for (Zombie o : zombies)
            {
                if (o!=null && o.getX() > this.getX()) 
                {
                    distanceToTarget = ZombieWorld.getDistance(this, o);
                    if (distanceToTarget < closestTargetDistance)
                    {
                        target = o;
                        closestTargetDistance = distanceToTarget;
                    }
                }
            }
            
            if (target.getX() > this.getX())
            {
                return true;
            }
            else
            {
                target = null;
                setRotation(0);
                return false;
            }
        }
        else
        {
            setRotation(0);
            return false;
        }
    }

    /**
     * Shoots his gun; fires a bullet
     */
    public void shootZombie()
    {
        gunshot.play();
        getWorld().addObject(new Bullet(this.getRotation()), this.getX()+30, this.getY()-25);
    }
    
    /**
     * Randomly plays his directory of Dave sounds.
     */
    public void playSound()
    {
        if (Greenfoot.getRandomNumber(3000) == 0 && alive)
        {
            pickSound = Greenfoot.getRandomNumber(8);
            daveSounds[pickSound].play();
        }
    }
}