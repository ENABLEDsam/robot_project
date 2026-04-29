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
            float red = colorData[0];
            float green = colorData[1];
            float blue = colorData[2];
            String valueToText = "R: " + Float.toString(red) + "G: " + Float.toString(green) + "B: " + Float.toString(blue);
        
            LCD.drawString(valueToText,0,0);
            Delay.msDelay(50);
        }

        colorSensor.close();
    }
}
