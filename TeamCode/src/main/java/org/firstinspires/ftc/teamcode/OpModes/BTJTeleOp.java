package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.message.redux.ReceiveGamepadState;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
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
        if (gamepad1.leftStickButtonWasPressed()) {
            if (drive.isFildoOn()) {
                drive.cancelFildo();
            } else {
                drive.resetIMU();
                drive.activateFildo();
            }
        }

        if (gamepad1.dpadUpWasPressed()) {
            shooter.autoSpeed(Shooter.Distance.FAR, voltageSensor.getVoltage());
        }

        if (gamepad1.dpadRightWasPressed()) {
            shooter.autoSpeed(Shooter.Distance.MID, voltageSensor.getVoltage());
        }

        if (gamepad1.dpadLeftWasPressed()) {
            shooter.autoSpeed(Shooter.Distance.CLOSE, voltageSensor.getVoltage());
        }

        if (gamepad1.dpadDownWasPressed()) {
            shooter.setPower(0);
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
                intake.firstStageIntake(Intake.Direction.FORWARD);
                intake.secondStageTransport(Intake.Direction.REVERSE);
            }
            waitForLongXPress.interrupt();
            waitForLongXPress.start();
        }

        if (gamepad1.right_trigger > 0.5 && !gamepad1RightTriggerWasPressed && !parking.isRobotRaised()) {
            gamepad1RightTriggerWasPressed = true;

            if (parking.isRobotLocked()){
                parking.releaseRobot();
            }else{
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



