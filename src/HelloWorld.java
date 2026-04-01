package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld
{
    static int nykyinenRivi = 0;

    public static void main(String[] args)
    {
        LCD.clear();

        tulostus("Welcome");
        Delay.msDelay(1000);

        tulostus("This is my 1st LEGO code.");
        Delay.msDelay(2000);

        tulostus("Make me autonomous");
        tulostus("Press any button to Stop.");

        Button.waitForAnyPress();
    }

    public static void tulostus(String teksti)
    {
        LCD.drawString(teksti, 0, nykyinenRivi);
        nykyinenRivi++;
    }
}