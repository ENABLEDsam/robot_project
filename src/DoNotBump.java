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
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        int normalSpeed = 200;
        int turnSpeed = 125;

        float passDistance = 0.50f;

        while (!Button.ESCAPE.isDown()) {
            distanceSample.fetchSample(distanceData, 0);
            float distance = distanceData[0];
            
            if (distance < passDistance) {
                //sets the left motor's speed down so it turns to the left
                rightMotor.setSpeed(turnSpeed);
            }
            else {
                //otherwise go forward, still need to find a way to make it go back on course afterwards
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
