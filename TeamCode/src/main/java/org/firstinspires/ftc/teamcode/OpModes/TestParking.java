package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Parking;

@TeleOp
public class TestParking extends OpMode {

    Parking parking;

    @Override
    public void init() {
        parking = new Parking(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.yWasPressed()){
            parking.raiseRobot();
        }
    }
}
