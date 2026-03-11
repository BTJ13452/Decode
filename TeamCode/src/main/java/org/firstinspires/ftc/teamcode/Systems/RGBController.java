package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RGBController {
    Servo ledLeft;
    Servo ledRight;

    public static final double GREEN  = 0.45;
    public static final double ORANGE  = 0.33;

    public static final double OFF = 0.1;
    public RGBController(HardwareMap hardwareMap) {
        ledLeft = hardwareMap.get(Servo.class, "Left LED");
        ledRight = hardwareMap.get(Servo.class, "Right LED");
    }

    public void setColorGreen() {
        ledLeft.setPosition(GREEN);
        ledRight.setPosition(GREEN);
    }
    public void setColorOrange() {
        ledLeft.setPosition(ORANGE);
        ledRight.setPosition(ORANGE);
    }

    public  void turnOff() {
        ledLeft.setPosition(OFF);
        ledRight.setPosition(OFF);
    }

}