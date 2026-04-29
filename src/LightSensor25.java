package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LightSensor25 {

    
    public static void main(String[] args) {

        
        
        // Light sensor
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getRedMode(); 
        float[] lightData = new float[lightSample.sampleSize()];

        
        
        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        
        int baseSpeed = 150;

        
        
        // Main loop
        
        while (!Button.ESCAPE.isDown()) {

            
            
            // Read sensor
            lightSample.fetchSample(lightData, 0);
            int lightValue = (int)(lightData[0] * 100);
            
            
            if (lightValue < 25) { 
                // dark color = turn slightly right
                leftMotor.setSpeed(baseSpeed + 50);
                rightMotor.setSpeed(baseSpeed - 50);
            } 
            
            
            else { 
                // light color = turn slightly left
                leftMotor.setSpeed(baseSpeed - 50);
                rightMotor.setSpeed(baseSpeed + 50);
            }

            
            
            
            // motors always moves forward
            leftMotor.forward();
            rightMotor.forward();

            Delay.msDelay(50);
        
        
        
        
        }

        
        
        
        
        
        // Stop
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        lightSensor.close();
    
    
    }

    



}

