package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.teamcode.Systems.Shooter;



@TeleOp
public class BTJTeleOp extends OpMode {

    final int LONG_PRESS_MILLISECONDS = 500;

    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
//    Parking parking;
    Shooter shooter;
    RGBController LEDs;


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

    @Override
    public void init() {
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        drive = new Drive(hardwareMap, 0);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
//        parking = new Parking(hardwareMap);
        LEDs = new RGBController(hardwareMap);
        LEDs.setGreen();
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

//        if (gamepad2.dpadUpWasPressed()) {
//            shooter.powerOffset += 0.1;
//        }
//
//        if (gamepad2.dpadRightWasPressed()) {
//            shooter.powerOffset += 0.01;
//        }
//
//        if (gamepad2.dpadDownWasPressed()) {
//            shooter.powerOffset -= 0.1;
//        }
//
//        if (gamepad2.dpadLeftWasPressed()) {
//            shooter.powerOffset -= 0.01;
//        }


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
//                intake.secondStageTransport(Intake.Direction.REVERSE);
            }
            waitForLongXPress.interrupt();
            waitForLongXPress.start();
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

//        if (gamepad1.yWasPressed()) {
//            if (parking.isRobotRaised()) {
//                parking.lowerRobot();
//            } else {
//                parking.raiseRobot();
//            }
//        }

        printTelemetry();
    }

    @Override
    public void stop() {
        if (waitForLongXPress.isAlive()) {
            waitForLongXPress.interrupt();
        }
//        parking.stop();
    }

    public void printTelemetry() {
        telemetry.addData("Shooter speed", shooter.getPower());
        telemetry.addData("Power offset",shooter.powerOffset);

//
        telemetry.update();
    }
}



