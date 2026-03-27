package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorTest {
    public static void main(String[] args) {
        // creating motor objects
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        

        // move motors forward
        leftMotor.setSpeed(360);   // degrees per second
        rightMotor.setSpeed(360);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(2000);       // 2 secs
        

        // spin
        leftMotor.backward();
        Delay.msDelay(1000);
        System.out.println("Reached halfway, returning to base");


        //going back
        leftMotor.forward();
        Delay.msDelay(2000); 

        // Stop motors
        leftMotor.stop();
        rightMotor.stop();

        // Close motors
        leftMotor.close();
        rightMotor.close();

        // Move motors backward
        /*leftMotor.backward();
        rightMotor.backward();
        Delay.msDelay(2000);       

        // Stop motors
        leftMotor.stop();
        rightMotor.stop();

        // Close motors
        leftMotor.close();
        rightMotor.close();

        System.out.println("test completed. Motors shoulve been working");*/
    }
}