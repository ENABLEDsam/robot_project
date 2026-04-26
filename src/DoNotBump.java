package src;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

//
public class DoNotBump {
public static void main(String[] args) {

        // Ultrasonic Senor
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        // Motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        int normalSpeed = 200;
        int turnSpeed = 125;
        float passDistance = 0.20f;

        while (!Button.ESCAPE.isDown()) {

            distanceSample.fetchSample(distanceData, 0);
            float distance = distanceData[0];
            
            // If obstacle is close = Dodge to the left
            if (distance < passDistance) {
                
                // Change "Delay.msDelay();" for testing 
                // Turn left
                leftMotor.setSpeed(turnSpeed);
                rightMotor.setSpeed(turnSpeed);
                leftMotor.backward();
                rightMotor.forward();
                Delay.msDelay(700);

                // Drive forward
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(3000);

                // Turn right to return to the line
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(700);

                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(2000);

                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(700);

                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(3000);

                leftMotor.backward();
                rightMotor.forward();
                Delay.msDelay(700);
            }
            else {
                // If there is no obstacle = Drive straight
                leftMotor.setSpeed(normalSpeed);
                rightMotor.setSpeed(normalSpeed);
                leftMotor.forward();
                rightMotor.forward();

            }
            Delay.msDelay(50);
        }

        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        usSensor.close();
    }
}
