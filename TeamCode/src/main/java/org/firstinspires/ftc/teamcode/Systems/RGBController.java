package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RGBController {

    Servo ledLeft;
    Servo ledRight;

    public static final double GREEN = 0.45;
    public static final double ORANGE = 0.33;
    public static final double WHITE = 1.0;
    public static final double BLUE = 0.61;
    public static final double OFF = 0.1;

    // טיימר להבהוב
    private long lastBlinkTime = 0;

    // מצב נוכחי
    private boolean blueState = true;

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

    public void setColorBlue() {
        ledLeft.setPosition(BLUE);
        ledRight.setPosition(BLUE);
    }

    public void setColorWhite() {
        ledLeft.setPosition(WHITE);
        ledRight.setPosition(WHITE);
    }

    public void turnOff() {
        ledLeft.setPosition(OFF);
        ledRight.setPosition(OFF);
    }

    // הבהוב כחול-לבן
    public void blinkBlueWhite() {

        long currentTime = System.currentTimeMillis();

        // מחליף צבע כל 500ms
        if (currentTime - lastBlinkTime > 500) {

            lastBlinkTime = currentTime;

            blueState = !blueState;

            if (blueState) {
                setColorBlue();
            } else {
                setColorWhite();
            }
        }
    }
}