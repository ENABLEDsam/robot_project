package src;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class SensorsCombined {

    
    public static void main(String[] args) {

        
        
        // Ultrasonic sensor
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        
        
        // Light sensor
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getRedMode(); 
        float[] lightData = new float[lightSample.sampleSize()];



        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        int normalSpeed = 200;
        int turnSpeed = 125;

        float passDistance = 0.50f;

        
        
        
        // Main loop
        while (!Button.ESCAPE.isDown()) {

            
            // Read ultrasonic sensor
            distanceSample.fetchSample(distanceData, 0);
            float distance = distanceData[0];

            
            // Read light sensor
            lightSample.fetchSample(lightData, 0);
            int lightValue = (int)(lightData[0] * 100);

            
            
            // Obstacle detection
            if (distance < passDistance) {
                //sets the left motor's speed down so it turns to the left
                rightMotor.setSpeed(turnSpeed);
                leftMotor.setSpeed(normalSpeed);
                leftMotor.forward();
                rightMotor.forward();
            }

           

            else {

                
                // Line following
                if (lightValue < 30) { // dark (on the line)
                    leftMotor.setSpeed(normalSpeed);
                    rightMotor.setSpeed(normalSpeed);
                    leftMotor.forward();
                    rightMotor.forward();
                } 
    


                else { // light (off the line)
                    leftMotor.setSpeed(normalSpeed);
                    rightMotor.setSpeed(normalSpeed);
                    leftMotor.backward();
                    rightMotor.forward();
                }
            }

            
            
            Delay.msDelay(50); // loop delay
        }

        
        
        
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        usSensor.close();
        lightSensor.close();
    
    
    
    
    
    }







}