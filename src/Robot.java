package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
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
        int threshold = 25;
        int lineTurning = 75;

        LCD.drawString("sheer heart attack", 0, 0);
        LCD.drawString("has no weakness", 0, 1);

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
                Delay.msDelay(4500);

                // Turn right
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(700);

                // Drive forward
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(3000);

                // Turn right again
                //leftMotor.forward();
                //rightMotor.backward();
                //Delay.msDelay(100);

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

            // LINE FOLLOWING
            if (lightValue < threshold) {
                // dark → right
                //leftMotor.setSpeed(baseSpeed + lineTurning);
                //rightMotor.setSpeed(baseSpeed - lineTurning);

                // dark → left
                leftMotor.setSpeed(baseSpeed - lineTurning);
                rightMotor.setSpeed(baseSpeed + lineTurning);
            } else {
                // light → left
                //leftMotor.setSpeed(baseSpeed - lineTurning);
                //rightMotor.setSpeed(baseSpeed + lineTurning);

                // light → right
                leftMotor.setSpeed(baseSpeed + lineTurning);
                rightMotor.setSpeed(baseSpeed - lineTurning);
            }

            leftMotor.forward();
            rightMotor.forward();

            

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