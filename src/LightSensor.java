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





/* package src;

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

        
        // Initialize Light Sensor
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S4);
        SampleProvider lightSample = lightSensor.getRedMode(); 
        float[] lightData = new float[lightSample.sampleSize()]; // Array to store light values

        
        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        int baseSpeed = 200; // Base motor speed

      

        // Main loop
        while (!Button.ESCAPE.isDown()) { // Run until ESCAPE button is pressed

            
            
            // Read light sensor value
            lightSample.fetchSample(lightData, 0);
            int lightValue = (int)(lightData[0] * 100); // Convert to percentage

         

            // Check if robot is on the line
            if (lightValue < 30) { // Dark = on the line
                leftMotor.setSpeed(baseSpeed);
                rightMotor.setSpeed(baseSpeed);
                leftMotor.forward();  // Move both motors forward
                rightMotor.forward();
            } 

            // Line lost, need to search
            else { 
               
                boolean foundLine = false;

                
                // Sweep movement to find line
                for (int i = 0; i < 3 && !foundLine; i++) { // Try 3 times

                    // Turn right slightly
                    leftMotor.forward();  
                    rightMotor.backward(); 
                    Delay.msDelay(300);    // Small delay for rotation

                    
                    
                    
                    // Check light sensor after right turn
                    lightSample.fetchSample(lightData, 0);
                    lightValue = (int)(lightData[0] * 100);
                    if (lightValue < 30) { 
                        foundLine = true;
                        break; // Exit sweep loop
                    }

                    
                    // Turn left slightly
                    leftMotor.backward();  
                    rightMotor.forward();  
                    Delay.msDelay(600);    // Larger delay to sweep left

                
                
                    
                    // Check light sensor after left turn
                    lightSample.fetchSample(lightData, 0);
                    lightValue = (int)(lightData[0] * 100);
                    
                    if (lightValue < 30) {
                        foundLine = true;
                        break;
                    }
                }

                
                // Move forward slowly until line found if still lost
                while (lightValue >= 30) { 
                    leftMotor.forward();
                    rightMotor.forward();

                    lightSample.fetchSample(lightData, 0);
                    lightValue = (int)(lightData[0] * 100);
                }
            }

          
            Delay.msDelay(50);
        }

      
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        lightSensor.close();

      



    }}   */


