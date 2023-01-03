import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a visual that flashes an image. This class was not made by group 5. This code was borrowed from previous semester students.
 * 
 * @author Isaac, Kenneth 
 * @version 1
 */
public class Play extends Actor
{
    private SimpleTimer fadeTimer;
    private boolean fadeOut = true;
    private boolean fadeIn = false;
    private GreenfootImage play;
    private int transparency;
    
    // Fading play button seen on TitleScreen
    public Play()
    {
        play = new GreenfootImage("text3.png");
        //play.scale(384*2, 192*2);
        setImage(play);
        transparency = 255;
        fadeTimer = new SimpleTimer();
    }
    
    public void act()
    {
        // If 1/10 of second has passed
        if (fadeTimer.millisElapsed() >= 100)
        {
            if (fadeOut) // if currently fading out play button
            {
                transparency -= 51; // make more transparent
                if (play.getTransparency() <= 0) // if not 0
                {
                    transparency = 0; // Set to prevent negative
                    fadeOut = false;
                    fadeIn = true; // start fading in play button
                }
            }
            else if (fadeIn)
            {
                transparency += 51; // increase transparency
                if (play.getTransparency() >= 255)
                {
                    transparency = 255; // prevent over 255
                    fadeOut = true; // start fading out play button
                    fadeIn = false; 
                }
            }
            fadeTimer.mark(); // Reset timer
            play.setTransparency(transparency); // set transparency
        }
    }
}