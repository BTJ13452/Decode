package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Drive;

@TeleOp
@Disabled


public class TestDrive extends OpMode {

    Drive drive;

    @Override
    public void init() {
        drive = new Drive(hardwareMap, -90 , false);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if(gamepad1.leftStickButtonWasPressed()){
            if(drive.isFildoOn())
                drive.cancelFildo();
            else {
                drive.resetIMU();
                drive.activateFildo();
            }
        }
    }
}
