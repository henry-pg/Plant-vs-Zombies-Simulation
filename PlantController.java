import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * This class controls all of the plants spawning in the scenario. It spawns plants by columns starting from the left to the right.
 * This class uses a 2D array to store the vancancy of grid positions. A 0 in the array means that the spot is empty, while a 1 means
 * that the spot is full. 
 * 
 * @author Henry 
 * @version April 2022
 */
public class PlantController extends Actor
{
    private int[] yPositions; 
    private int[] xPositions;

    private int[][] plantGrid; 
    private Integer[] gridColumn;
    private int plantSpawnRate;
    
    private static boolean gameOver = false;
    private static boolean apocalypse = false;
 
    /**
     * Constructor that initializes all of the X and Y locations that plants should be spawned into as well as the plant grid. 
     */
    public PlantController()
    {
        //values
        yPositions = new int[]{100, 172, 245, 320, 385};
        xPositions = new int[] {215, 270, 330, 390, 450, 510};
        plantGrid = new int[6][5];
        gridColumn = new Integer[] {0,1,2,3,4};
        
        //fills the plantGrid 2D array with 0
        //0 means that the particular plant grid is empty
        for (int i = 0; i<6; i++)
        {
            for (int j = 0;j<5; j++)
            {
                plantGrid[i][j]= 0;
            }
        }

        //set spawn rate somewhere
        plantSpawnRate = 250;
    }

    /**
     * The act method that spawns plants based on a spawn rate. If the apocalypse event is present in the game, the plants 
     * do not spawn anymore.
     */
    public void act()
    {
        if (!gameOver && !apocalypse)
        {
            plantSpawnRate --;

            if (plantSpawnRate == 0)
            {
                plantSpawnRate = 250;
                spawnPlant();
            }
        }
        
        if (apocalypse)
        {
            plantSpawnRate = 0;
        }
    }

    /**
     * This is the method that contains all of the logic with plant spawning. Plants are only spawned into empty grids. Plants
     * spawn starting from the left most column, to the right columns. While the column that the plant spawns into uses logic, 
     * the row that the plant spawns into is randomized. 
     */
    public void spawnPlant()
    {
        //values
        boolean rowClear;
        int randomLocation;
        boolean broke = false;
        int randomPlant;
        int xPos;
        int yPos;
        int loopCounter;
    
        //nested for loop that checks to see which grid to spawn plant into 
        for (int i = 0; i < 6; i++)
        {
            //more values
            List<Integer> randomizedColumn =Arrays.asList(gridColumn);
            Collections.shuffle(randomizedColumn);
            randomizedColumn.toArray(gridColumn);
            xPos = xPositions[i];
            ArrayList<Plant> plantsArray;
            
            //this for loop checks to see if a grid in a column is empty, then spawns a random plant into it if it is.
            for (Integer j: randomizedColumn)
            {
                yPos = yPositions[j];
                if (plantGrid[i][j] == 0)
                {
                    randomPlant = Greenfoot.getRandomNumber(7);

                    if(randomPlant == 0)
                    {
                        spawnPeaShooter(xPos, yPos);
                    }
                    else if (randomPlant == 1)
                    {
                        spawnSnowPea(xPos, yPos);
                    }
                    else if (randomPlant == 2)
                    {
                        spawnCactus(xPos, yPos);
                    }
                    else if (randomPlant == 3)
                    {
                        spawnThistle(xPos, yPos);
                    }
                    else if (randomPlant == 4)
                    {
                        spawnWalnut(xPos, yPos);
                    }
                    else if (randomPlant == 5)
                    {
                        spawnPoisonPea(xPos, yPos);
                    }
                    else if (randomPlant == 6)
                    {
                        spawnRepeater(xPos, yPos);
                    }

                    //breaks out of loop if condition is satisfied
                    plantGrid[i][j] = 1;
                    broke = true;
                    break;
                }
                
                //if a plant is eaten and removed, this conditional updates the plant grid about its new vacancy
                if (getWorld().getObjectsAt(xPos, yPos, Plant.class).isEmpty())
                {
                    emptyGridPosition(convertToGridX(xPos), convertToGridY(yPos));
                }
            }

            if (broke)
            {
                break;
            }

        }
    }

    /**
     * Method to add PeaShooterPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnPeaShooter(int x, int y)
    {
        getWorld().addObject(new PeashooterPacket(y), x, 0);
    }

    /**
     * Method to add SnowPeaPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnSnowPea(int x, int y)
    {
        getWorld().addObject(new SnowpeaPacket(y), x, 0);
    }

    /**
     * Method to add CactusPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnCactus(int x, int y)
    {
        getWorld().addObject(new CactusPacket(y), x, 0);
    }

    /**
     * Method to add HomingThistlePacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnThistle(int x, int y)
    {
        getWorld().addObject(new HomingThistlePacket(y), x, 0);
    }

    /**
     * Method to add WallnutPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnWalnut(int x, int y)
    {
        getWorld().addObject(new WallnutPacket(y), x, 0);
    }

    /**
     * Method to add PoisonpeaPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnPoisonPea(int x, int y)
    {
        getWorld().addObject(new PoisonpeaPacket(y), x, 0);
    }

    /**
     * Method to add RepeaterPacket into world
     * @param x X-location to drop plant packet
     * @param y Y-location that plant packet will end up
     */
    public void spawnRepeater(int x, int y)
    {
        getWorld().addObject(new RepeaterPacket(y), x, 0);
    }

    /**
     * Method to empty a grid position
     * @param indexX The x value of the plant grid that needs to be emptied
     * @param indexY The y value of the plant grid that needs to be emptied
     */
    public void emptyGridPosition(int indexX, int indexY)
    {
        plantGrid[indexX][indexY] = 0;
    }

    /**
     * Method to convert x-world coordinates into plant grid array coordinates
     * @coordX The x-world coordinate that needs to be converted
     * @return int returns the plant grid array coordinate
     */
    public int convertToGridX(int coordX)
    {
        for (int i = 0; i < 6; i ++)
        {
            if (xPositions[i] == coordX)
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Method to convert y-world coordinates into plant grid array coordinates
     * @coordX The y-world coordinate that needs to be converted
     * @return int Returns the plant grid array coordinate
     */
    public int convertToGridY(int coordY)
    {
        for (int i = 0; i < 5; i ++)
        {
            if (yPositions[i] == coordY)
            {
                return i;
            }
        }

        return -1;

    }

    /**
     * Method to check how many columns of plants are present in the world. The zombie controller uses this info
     * to see how many zombies it should spawn and how strong the zombies should be. The severity of the zombies is 
     * proportional to the amount of plants using this method. 
     * 
     * @return int Returns an integer that specifies the amount of rows of plants.
     */
    public int columnsOfPlants()
    {
        //values
        boolean fullRow;
        boolean breakOut = false;
        int columns = 0;
        
        //checks each row in the plant grid to see if it is full. If yes, it counts that row as a complete row of plants. 
        for (int i = 0; i < plantGrid.length; i++)
        {
            fullRow = false;
            for (int integer: plantGrid[i])
            {
                if (integer == 1)
                {
                    fullRow = true;
                }
                else 
                {
                    fullRow = false;
                    breakOut = true;
                    break;
                }
            }

            if (fullRow)
            {
                columns = i;
            }

            if (breakOut == true)
            {
                break;
            }

        }
        return columns;
    }
    
    /**
     * method that sets gameOver variable to true
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }
    
    /**
     * method that sets gameOver to false
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
    
    /**
     * method that sets apocalypse boolean to true
     */
    public static void apocalypseTrue()
    {
        apocalypse = true;
    }
    
    /**
     * method that sets apocalypse boolean to false
     */
    public static void apocalypseFalse()
    {
        apocalypse = false;
    }
}