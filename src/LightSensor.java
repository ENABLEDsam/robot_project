package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LightSensor {

    public static void main(String[] args) {

        // Light sensor
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S4);
        SampleProvider lightSample = lightSensor.getRedMode(); 
        float[] lightData = new float[lightSample.sampleSize()];

        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        
        int baseSpeed = 200; // Base speed

        
        // Main loop
        while (!Button.ESCAPE.isDown()) {

            
            // Read the light sensor
            lightSample.fetchSample(lightData, 0);
            int lightValue = (int)(lightData[0] * 100);

    
            // Line following
            if (lightValue < 30) { // dark (on the line)
                leftMotor.setSpeed(baseSpeed);
                rightMotor.setSpeed(baseSpeed);
                leftMotor.forward();
                rightMotor.forward();
            } 
            
            
            else { // light (off the line)
                leftMotor.setSpeed(baseSpeed);
                rightMotor.setSpeed(baseSpeed);
                leftMotor.backward();
                rightMotor.forward();
            }

            
            Delay.msDelay(50); // loop delay
        }

        
       
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        lightSensor.close();

    }
}