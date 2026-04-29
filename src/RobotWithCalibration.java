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

public class RobotWithCalibration {

    public static void main(String[] args) {

        // SENSORIT
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightSample = lightSensor.getAmbientMode();
        float[] lightData = new float[lightSample.sampleSize()];

        EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSample = usSensor.getDistanceMode();
        float[] distanceData = new float[distanceSample.sampleSize()];

        // MOOTTORIT
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        int baseSpeed = 150;
        int turnSpeed = 125;
        float passDistance = 0.20f;

        // 🔵 KALIBROINTI
        LCD.clear();
        LCD.drawString("Place on WHITE", 0, 0);
        LCD.drawString("Press ENTER", 0, 1);
        Button.ENTER.waitForPress();

        lightSample.fetchSample(lightData, 0);
        int white = (int)(lightData[0] * 100);

        LCD.clear();
        LCD.drawString("WHITE: " + white, 0, 0);
        Delay.msDelay(1000);

        LCD.clear();
        LCD.drawString("Place on BLACK", 0, 0);
        LCD.drawString("Press ENTER", 0, 1);
        Button.ENTER.waitForPress();

        lightSample.fetchSample(lightData, 0);
        int black = (int)(lightData[0] * 100);

        LCD.clear();
        LCD.drawString("BLACK: " + black, 0, 0);
        Delay.msDelay(1000);

        // 🔵 AUTOMAATTINEN THRESHOLD
        int threshold = (white + black) / 2;

        LCD.clear();
        LCD.drawString("THRESHOLD:", 0, 0);
        LCD.drawString("" + threshold, 0, 1);
        Delay.msDelay(2000);

        // 🔁 PÄÄLOOPPI
        while (!Button.ESCAPE.isDown()) {

            lightSample.fetchSample(lightData, 0);
            distanceSample.fetchSample(distanceData, 0);

            int lightValue = (int)(lightData[0] * 100);
            float distance = distanceData[0];

            // NÄYTTÖ
            LCD.drawString("Light: " + lightValue + "%   ", 0, 0);

            // LINE FOLLOW
            if (lightValue < threshold) {
                leftMotor.setSpeed(baseSpeed + 50);
                rightMotor.setSpeed(baseSpeed - 50);
            } else {
                leftMotor.setSpeed(baseSpeed - 50);
                rightMotor.setSpeed(baseSpeed + 50);
            }

            leftMotor.forward();
            rightMotor.forward();

            // OBSTACLE
            if (distance < passDistance) {

                leftMotor.setSpeed(turnSpeed);
                rightMotor.setSpeed(turnSpeed);
                leftMotor.backward();
                rightMotor.forward();
                Delay.msDelay(700);

                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(3000);

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

                while (true) {

                    lightSample.fetchSample(lightData, 0);
                    lightValue = (int)(lightData[0] * 100);

                    LCD.drawString("Light: " + lightValue + "%   ", 0, 0);

                    if (lightValue < threshold) {
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
        lightSensor.close();
        usSensor.close();
    }
}