package src;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robot {

    public static void main(String[] args) {

        // LIGHT SENSOR
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getRedMode();
        float[] lightData = new float[lightSample.sampleSize()];

        // ULTRASONIC SENSOR
        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        // MOTORS
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        // PARAMETERS
        int baseSpeed = 150;
        int turnSpeed = 125;
        float passDistance = 0.20f;
        int threshold = 45;

        while (!Button.ESCAPE.isDown()) {

            // READ SENSORS
            lightSample.fetchSample(lightData, 0);
            distanceSample.fetchSample(distanceData, 0);

            int lightValue = (int)(lightData[0] * 100);
            float distance = distanceData[0];

            // OBSTACLE AVOIDANCE
            if (distance < passDistance) {

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

                // Turn right
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(700);

                // Drive forward
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(2000);

                // Turn right again
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(700);

                // Drive forward until line is found
                leftMotor.forward();
                rightMotor.forward();

                // Wait until line is found
                while (true) {

                    lightSample.fetchSample(lightData, 0);
                    lightValue = (int)(lightData[0] * 100);

                // If line is found, break the loop and return to line following
                    if (lightValue < threshold) {
                        break;
                    }

                    Delay.msDelay(10);
                }

            }
            else {

                // LINE FOLLOWING
                if (lightValue < threshold) {
                    // dark → right
                    leftMotor.setSpeed(baseSpeed + 50);
                    rightMotor.setSpeed(baseSpeed - 50);
                } else {
                    // light → left
                    leftMotor.setSpeed(baseSpeed - 50);
                    rightMotor.setSpeed(baseSpeed + 50);
                }

                leftMotor.forward();
                rightMotor.forward();
            }

            Delay.msDelay(50);
        }

        // STOP
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        lightSensor.close();
        usSensor.close();
    }
}