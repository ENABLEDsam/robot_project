package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LightTester {

    

    public static void main(String[] args) {
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getRedMode(); 
        float[] lightData = new float[lightSample.sampleSize()];

        

        while (!Button.ESCAPE.isDown()) {
            lightSample.fetchSample(lightData, 0);
            int lightValue = (int)(lightData[0] * 100);
            String valueToText = "light level: " + Integer.toString(lightValue);
        
            LCD.drawString(valueToText,0,0);
            Delay.msDelay(50);
        }

        lightSensor.close();
    }
}
