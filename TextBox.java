import greenfoot.*; 

/**
 * This is the class for the textbox objects that have an editable and displayable value. 
 * This is used for users to change plant and zombie values. 
 * 
 * @author (Henry, Jonathan) 
 * @version (April 2022)
 */
public class TextBox extends Actor
{
    private double value;
    private MultiplierButton leftButton;
    private MultiplierButton rightButton;
    private boolean runOnce;
    private String valueString;
    private GreenfootImage textbox;
    private GreenfootImage text;
    private GreenfootSound[] clickSounds;
    private int clickSoundIndex = 0;
    private GreenfootSound[] noFurtherSounds;
    private int noFurtherSoundIndex = 0;
    
    /**
     * Constructor that initializes all of the values and also creates the left button and right button for the textbox.
     * 
     * @param v This argument is to set a value for the textbox to display.
     */
    public TextBox(double v)
    {
        //sets up values
        this.value = v;
        valueString = String.valueOf(value);
        textbox = new GreenfootImage (30,30);
        text = new GreenfootImage(valueString, 24, Color.WHITE, null);
        runOnce = true;
        
        //initialize left button
        leftButton = new MultiplierButton(false);
        
        //initialize right value
        rightButton = new MultiplierButton(true);
        
        //click sounds 
        clickSounds = new GreenfootSound [20];
        for (int i = 0; i < clickSounds.length; i++)
        {
            clickSounds [i] = new GreenfootSound ("plant2.wav");
            clickSounds [i].setVolume(65);
        }
        
        noFurtherSounds = new GreenfootSound [20];
        for (int i = 0; i < noFurtherSounds.length; i++)
        {
            noFurtherSounds [i] = new GreenfootSound ("shovel.wav");
            noFurtherSounds [i].setVolume(70);
        }
    }
    
    /**
     * This is the act method. It checks to see if any buttons are being pressed and it updates itself accordingly. 
     * Sounds are also played accordingly. 
     */
    public void act()
    {      
        textbox.setTransparency(0);
        if (runOnce)
        {
            getWorld().addObject(leftButton, getX() - 30, getY());
            getWorld().addObject(rightButton, getX() + 30, getY());
            runOnce = false;
        }
        
        if (leftButton.isClicked())
        {
            decreaseValue();
        }
        
        if (rightButton.isClicked())
        {
            increaseValue();
        }
        
        textbox = new GreenfootImage (30,30);
        text = new GreenfootImage(valueString, 24, Color.GRAY, null);
        
        setImage (textbox);
        textbox.drawImage(text, 1, 2);
    }
    
    /**
     * Method to decrease the value of that the textbox displays. Sounds are also played accordingly. 
     */
    public void decreaseValue()
    {
        if (value > 0.1)
        {
            value -= 0.1;
            clickSounds [clickSoundIndex].play();
            clickSoundIndex++;
            if (clickSoundIndex > clickSounds.length - 1)
            {
                clickSoundIndex = 0;
            }
        }
        else
        {
            noFurtherSounds [noFurtherSoundIndex].play();
            noFurtherSoundIndex++;
            if (noFurtherSoundIndex > noFurtherSounds.length - 1)
            {
                noFurtherSoundIndex = 0;
            }
        }
        value = Math.round(value*100.0)/100.0;
        valueString = String.valueOf(value);
    }
    
    /**
     * Method to increase the value of that the textbox. Sounds are also played accordingly. 
     */
    public void increaseValue()
    {
        if (value < 2)
        {
            value += 0.1;
            clickSounds [clickSoundIndex].play();
            clickSoundIndex++;
            if (clickSoundIndex > clickSounds.length - 1)
            {
                clickSoundIndex = 0;
            }
        }
        else
        {
            noFurtherSounds [noFurtherSoundIndex].play();
            noFurtherSoundIndex++;
            if (noFurtherSoundIndex > noFurtherSounds.length - 1)
            {
                noFurtherSoundIndex = 0;
            }
        }
        value = Math.round(value*100.0)/100.0;
        valueString = String.valueOf(value);
    }
    
    /**
     * Method to return value of textbox.
     * 
     * @return double Returns the value of the textbox.
     */
    public double getValue()
    {
        return value;
    }
    
    /**
     * Method to set value of textbox.
     * 
     * @param v Value that textbox will be set to.
     */
    public void setValue(double v)
    {
        value = v;
    }
}
