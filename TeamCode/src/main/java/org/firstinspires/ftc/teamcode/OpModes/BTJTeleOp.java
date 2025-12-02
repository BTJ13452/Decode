package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
public class BTJTeleOp extends OpMode {

    final int LONG_PRESS_MILLISECONDS = 500;
    final double SHOOTER_DIFFERENTIAL_SPEED = 0.05;


    Drive drive;
    Parking parking;
    Shooter shooter;
    Intake intake;

    Intake rigthActivateMidIntake;
    Intake leftActivateMidIntake;

    Thread waitForLongXPress;

    @Override
    public void init() {
        drive = new Drive(hardwareMap, 0);
       intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);



        waitForLongXPress = new Thread(new Runnable() {
            @Override
            public void run() {
                gamepad1.xWasReleased();
                sleep(LONG_PRESS_MILLISECONDS);
                if (!gamepad1.xWasReleased()) {
                    intake.activateEjection();
                    while (gamepad1.x) {
                    }
                    intake.deactivateAllIntake();
                }
            }
        });

        // shooter = new Shooter(hardwareMap);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.leftStickButtonWasPressed()) {
            if (drive.isFildoOn())
                drive.cancelFildo();
            else {
                drive.resetIMU();
                drive.activateFildo();
            }
        }


    if (gamepad1.yWasPressed()){
            parking.raiseRobot();
        }
        if(gamepad1.bWasPressed()){
            shooter.setMotorSpeed(0.8);
        }

        if(gamepad1.dpadUpWasPressed()){
            shooter.setMotorSpeed(shooter.getMotorSpeed()+SHOOTER_DIFFERENTIAL_SPEED);
        } else if (gamepad1.dpadDownWasPressed()) {
            shooter.setMotorSpeed(shooter.getMotorSpeed()-SHOOTER_DIFFERENTIAL_SPEED
            );
        }
        if (gamepad1.rightStickButtonWasPressed()){
            shooter.setMotorSpeed(0);
        }





        telemetry.addData("Shooter speed", shooter.getMotorSpeed());
        telemetry.update();


        if (gamepad1.xWasPressed()) {
            if (intake.isActive()) {
                intake.deactivateIntake();
            } else {
                intake.activateIntake();
            }


            waitForLongXPress.interrupt();
            waitForLongXPress.start();

        }



        if (gamepad1.rightBumperWasPressed()) {
            if (intake.isMidRightActive()) {
                intake.deMidRightActivateIntake();
                intake.deactivateWheel();


            }
            else {
                intake.rightActivateMidIntake();
                intake.ActivateWheel();
            }
        }


        if (gamepad1.leftBumperWasPressed()){
            if (intake.isMidLeftActive()) {
                intake.deMidLeftActivateIntake();
                intake.deactivateWheel();

            }
            else {
                intake.leftActivateMidIntake();
                intake.ActivateWheel();
            }
        }
    }
    }



