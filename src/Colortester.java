package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Colortester {

    

    public static void main(String[] args) {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
        float[] colorData = new float[colorSensor.getRGBMode().sampleSize()];
        

        while (!Button.ESCAPE.isDown()) {
            colorSensor.getRGBMode().fetchSample(colorData, 0);
            float red = colorData[0] * 10;
            float green = colorData[1] * 10;
            float blue = colorData[2] * 10;
            String one = "R: " + Float.toString(red);
            String two = "G: " + Float.toString(green);
            String three = "B: " + Float.toString(blue);
            LCD.drawString(one,0,0);
            LCD.drawString(two,0,1);
            LCD.drawString(three,0,2);
            Delay.msDelay(50);
        }

        colorSensor.close();
    }
}
