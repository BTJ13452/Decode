package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import android.util.Log;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorLimelight3A;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
public class BTJTeleOp extends OpMode {

    final int LONG_PRESS_MILLISECONDS = 500;
    final Double CONTENT_SHOOTER_SPEED = 0.7;
    double power;


    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
    Shooter shooter;
    Parking parking;
    RGBController LEDs;
    Limelight3A limelight;
    SensorLimelight3A sensorLimelight3A;
    LLResult llResult;


    Thread waitForLongXPress = new Thread(new Runnable() {
        @Override
        public void run() {
            gamepad1.xWasReleased();
            sleep(LONG_PRESS_MILLISECONDS);
            if (!gamepad1.xWasReleased()) {
                intake.startAll(Intake.Direction.REVERSE);
                while (gamepad1.x) {
                }
                intake.startAll(Intake.Direction.STOP);
            }
        }
    });


    boolean gamepad1RightTriggerWasPressed = false;

    @Override
    public void init() {
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        drive = new Drive(hardwareMap, 0);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);
        LEDs = new RGBController(hardwareMap);
        power = 0;
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();
    }

    @Override
    public void loop() {

        drive.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
//        if (gamepad1.leftStickButtonWasPressed()) {
//            if (drive.isFildoOn()) {
//                drive.cancelFildo();
//            } else {
//                drive.resetIMU();
//                drive.activateFildo();
//            }
//        }
//        if (!shooter.isActive()) {
//            shooter.setPower(CONTENT_SHOOTER_SPEED);
//        }

        if (gamepad1.dpadUpWasPressed()) {
            shooter.setPower(shooter.SHOOTER_SPEED_FOUR + shooter.powerOffset);
        }

        if (gamepad1.dpadRightWasPressed()) {
            shooter.setPower(shooter.SHOOTER_SPEED_THREE + shooter.powerOffset);
        }

        if (gamepad1.dpadLeftWasPressed()) {
            shooter.setPower(shooter.SHOOTER_SPEED_TWO + shooter.powerOffset);
        }

        if (gamepad1.dpadDownWasPressed())
        {
            if(shooter.getPower()==shooter.SHOOTER_SPEED_ONE ) {
                shooter.setPower(0);
            }
            else {
                shooter.setPower(shooter.SHOOTER_SPEED_ONE + shooter.powerOffset);
            }


        }
        if (gamepad2.dpadUpWasPressed()) {
            shooter.powerOffset += 0.1;
        }


        if (gamepad2.dpadRightWasPressed()) {
            shooter.powerOffset += 0.01;
        }

        if (gamepad2.dpadDownWasPressed()) {
            shooter.powerOffset -= 0.1;
        }

        if (gamepad2.dpadLeftWasPressed()) {
            shooter.powerOffset -= 0.01;
        }

        if (gamepad1.rightStickButtonWasPressed()) {
            shooter.setPower(0);
            intake.secondStageTransport(Intake.Direction.REVERSE);
            intake.thirdStageTransport(Intake.Direction.STOP);
        }

        if (gamepad1.xWasPressed()) {
            if (intake.isIntakeActive()) {
                intake.firstStageIntake(Intake.Direction.STOP);
                intake.secondStageTransport(Intake.Direction.STOP);
                intake.thirdStageTransport(Intake.Direction.STOP);
            } else {
                intake.firstStageIntake(Intake.Direction.REVERSE);
                intake.secondStageTransport(Intake.Direction.REVERSE);
                intake.thirdStageTransport(Intake.Direction.REVERSE);
            }
            waitForLongXPress.interrupt();
            waitForLongXPress.start();
        }

        if (gamepad1.right_trigger > 0.5 && !gamepad1RightTriggerWasPressed && !parking.isRobotRaised()) {
            gamepad1RightTriggerWasPressed = true;

            if (parking.isRobotLocked()) {
                parking.releaseRobot();
            } else {
                parking.lockRobot();
            }

        } else if (gamepad1.right_trigger <= 0.5) {
            gamepad1RightTriggerWasPressed = false;
        }


        if (gamepad1.leftBumperWasPressed()) {
            intake.transportArtifactToShooter(Intake.Cell.LEFT);
        }
        if (gamepad1.leftBumperWasReleased()) {
            intake.secondStageTransport(Intake.Direction.STOP);
            intake.thirdStageTransport(Intake.Direction.STOP);
        }


        if (gamepad1.rightBumperWasPressed()) {
            intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        }
        if (gamepad1.rightBumperWasReleased()) {
            intake.secondStageTransport(Intake.Direction.STOP);
            intake.thirdStageTransport(Intake.Direction.STOP);
        }

        if (gamepad1.yWasPressed()) {
            if (parking.isRobotRaised()) {
                parking.lowerRobot();
            } else {
                parking.raiseRobot();
            }
        }


        if (gamepad1.bWasPressed()) {
            intake.shootVolley();
        }


//        if (shooter.isActive()) {
//            updateShooter();
//        }

        if (gamepad1.aWasPressed()) {
            if (shooter.isActive()) {
                shooter.setPower(0);
            } else {
                updateShooter();
            }
        }


            if (intake.areThreeIn()) {
                LEDs.setGreen();
            } else {
                LEDs.setOff();
            }


        if (!parking.isRobotRaised()) {
            parking.stayClosed();
        }

        printTelemetry();
    }

    @Override
    public void stop() {
        if (waitForLongXPress.isAlive()) {
            waitForLongXPress.interrupt();
        }
        parking.stop();
        limelight.stop();
        intake.stop();
    }

    public void printTelemetry() {
        telemetry.addData("power", shooter.getPower());
        telemetry.addData("voltage", voltageSensor.getVoltage());

        telemetry.update();
    }

    public void updateShooter() {
        llResult = limelight.getLatestResult();
        for (int i = 0; i < 100; i++) {
            if (llResult != null && llResult.isValid()) {
                shooter.setPowerByDistance(llResult.getTa(), voltageSensor.getVoltage());
                telemetry.addData("shooter power", shooter.getPower());
                telemetry.addData("ta", llResult.getTa());
                telemetry.addData("voltage", voltageSensor.getVoltage());
                telemetry.update();
                break;
            }
        }
    }
}



