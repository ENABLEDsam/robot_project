package src;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class RobotWithColor {

    public static void main(String[] args) {

        // COLOR SENSOR (changed)
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);

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

        while (!Button.ESCAPE.isDown()) {

            // READ SENSORS
            distanceSample.fetchSample(distanceData, 0);
            float distance = distanceData[0];

            int color = colorSensor.getColorID();

            // LINE FOLLOWING (using color instead of light)
            if (color == Color.BLACK) {
                // black → right
                leftMotor.setSpeed(baseSpeed + 50);
                rightMotor.setSpeed(baseSpeed - 50);
            } else {
                // not black → left
                leftMotor.setSpeed(baseSpeed - 50);
                rightMotor.setSpeed(baseSpeed + 50);
            }

            leftMotor.forward();
            rightMotor.forward();

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

                // Drive forward until line (black) is found
                leftMotor.forward();
                rightMotor.forward();

                while (true) {

                    color = colorSensor.getColorID();

                    // If black line found → break
                    if (color == Color.BLACK) {
                        break;
                    }

                    Delay.msDelay(10);
                }
            }

            Delay.msDelay(50);
        }

        // STOP
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.close();
        rightMotor.close();
        colorSensor.close();
        usSensor.close();
    }
}