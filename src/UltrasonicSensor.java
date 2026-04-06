package src;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;



public class UltrasonicSensor {

    public static void main(String[] args) {

        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S1);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        int baseSpeed = 200;

        // distance at which the robot stops 0.20f = 20 cm.
        float stopDistance = 0.20f;


        while (!Button.ESCAPE.isDown()) {
            distanceSample.fetchSample(distanceData, 0);
            float distance = distanceData[0];

                if (distance < stopDistance) {
                    leftMotor.stop(true);
                    rightMotor.stop();
                }
                else {
                    leftMotor.setSpeed(baseSpeed);
                    rightMotor.setSpeed(baseSpeed);
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