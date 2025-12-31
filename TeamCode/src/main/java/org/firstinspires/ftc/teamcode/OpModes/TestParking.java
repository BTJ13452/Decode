package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Parking;

@TeleOp
//@Disabled
public class TestParking extends OpMode {

    Parking parking;
    int num = 1;

    @Override
    public void init() {
        parking = new Parking(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.yWasPressed()) {
            parking.raiseRobot();
            num = 0;
        }
        if (num == 0 && gamepad1.yWasPressed()) {
            parking.lowerRobot();
            num = 1;

        }
    }
}
