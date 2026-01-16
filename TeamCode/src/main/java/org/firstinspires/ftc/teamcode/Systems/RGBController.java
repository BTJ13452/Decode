package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RGBController {
    Servo ledLeft;
    Servo ledRight;


    public static final double RED  = 0.28;
    public static final double PURPLE = 0.85;
    public static final double GREEN  = 0.45;

    public static final double OFF = 0.1;
    public RGBController(HardwareMap hardwareMap) {
        ledLeft = hardwareMap.get(Servo.class, "Left LED");
        ledRight = hardwareMap.get(Servo.class, "Right LED");
    }

    public void setGreen() {
        ledLeft.setPosition(GREEN);
        ledRight.setPosition(GREEN);
    }

    public  void  setRed() {
        ledLeft.setPosition(RED);
        ledRight.setPosition(RED);
    }

    public  void  setOff() {
        ledLeft.setPosition(OFF);
        ledRight.setPosition(OFF);
    }



}