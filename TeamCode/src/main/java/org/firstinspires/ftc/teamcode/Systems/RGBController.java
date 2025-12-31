package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RGBController {
    Servo ledLeft;
    Servo ledRight;


    public static final double GREEN  = 0.277;


    public RGBController(HardwareMap hardwareMap) {
        ledLeft = hardwareMap.get(Servo.class, "Left LED");
        ledRight = hardwareMap.get(Servo.class, "Right LED");
    }

    public void setGreen() {
        ledLeft.setPosition(GREEN);
        ledRight.setPosition(GREEN);
    }

}