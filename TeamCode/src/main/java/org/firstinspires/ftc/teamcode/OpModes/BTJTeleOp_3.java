package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
@Disabled

public class BTJTeleOp_3 extends OpMode {

    boolean isParking = false;
    final int LONG_PRESS_MILLISECONDS = 500;
    final double SHOOTER_DIFFERENTIAL_SPEED = 0.05;
    final double closeShooterSpeed = 0.7;

    final double midShooterSpeed = 0.8;
    final double farShooterSpeed = 1;


    Drive drive;
    Intake intake;
    Parking parking;
    Shooter shooter;

    int c = 0;
    Intake rigthActivateMidIntake;
    Intake leftActivateMidIntake;
    Intake deactivateAllIntakeButSheiva;

    Thread waitForLongXPress;

    @Override
    public void init() {
        telemetry.addLine("0");
        telemetry.update();

        drive = new Drive(hardwareMap, 0);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);

    }

    @Override
    public void loop() {
        c++;
        telemetry.addData("c = " ,c);
        telemetry.update();

        drive.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        telemetry.addLine("1");
        telemetry.update();


        if (gamepad1.leftStickButtonWasPressed()) {
            if (drive.isFildoOn())
                drive.cancelFildo();
            else {
                drive.resetIMU();
                drive.activateFildo();
            }
        }

        telemetry.addLine("2");
        telemetry.update();

        if (gamepad1.yWasPressed()) {
            parking.raiseRobot();
            isParking = true;
        }

        telemetry.addLine("3");
        telemetry.update();


    }

    public double getCloseShooterSpeed() {
        return closeShooterSpeed;
    }
}