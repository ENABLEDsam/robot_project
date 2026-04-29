package src;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;




public class SensorsCombined {

    public static void main(String[] args) {

      

        
        // Sensors
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getRedMode();
        float[] lightData = new float[lightSample.sampleSize()];

        
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        
        
       

        
        
        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        
        int baseSpeed = 200;
        float passDistance = 0.20f;

      
        
        

        
        // MAIN LOOP
        while (!Button.ESCAPE.isDown()) {

            
            
            
            // READ SENSORS
            lightSample.fetchSample(lightData, 0);
            distanceSample.fetchSample(distanceData, 0);

            int lightValue = (int)(lightData[0] * 100);
            float distance = distanceData[0];

            
            
            
            
            
            
            // OBSTACLE DETECTION
           
            if (distance < passDistance) {

                // stop
                leftMotor.stop(true);
                rightMotor.stop();

                // turn left
                leftMotor.setSpeed(125);
                rightMotor.setSpeed(125);
                leftMotor.backward();
                rightMotor.forward();
                Delay.msDelay(600);

                // drive forward
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(600);

                // turn right back to line
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(600);

                continue;
            }

            
            
            
            // LINE FOLLOWING
            

            if (lightValue < 30) {
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

       

        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        lightSensor.close();
        usSensor.close();
    }
}