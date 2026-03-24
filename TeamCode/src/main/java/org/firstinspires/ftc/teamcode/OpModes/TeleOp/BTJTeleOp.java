package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.AutoAline;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Parking;
import org.firstinspires.ftc.teamcode.Systems.RGBController;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

public class BTJTeleOp extends OpMode {
    protected static AutoAline.AllianceColor AllianceColor;
    final int LONG_PRESS_MILLISECONDS = 500;
    final double BIG_OFFSET_POWER = 0.1;
    final double SMALL_OFFSET_POWER = 0.01;
    final double PRESS = 0.1;
    final int WAIT_BETWEEN_UPDATE_SHOOTER_CLOSE = 2000;
    final int WAIT_BETWEEN_UPDATE_SHOOTER_MID = 1;
    double power;
    double forward, strafe, rotate;
    boolean ShootingFromClose = false;
    boolean ShootingFromFar = false;


    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
    Shooter shooter;
    Parking parking;
    RGBController LEDs;
    AutoAline autoAline;
    Thread waitForLongXPress = new Thread(new Runnable() {
        @Override
        public void run() {
            gamepad1.xWasReleased();
            sleep(LONG_PRESS_MILLISECONDS);
            if (!gamepad1.xWasReleased()) {

                intake.startAll(Intake.Direction.REVERSE);
                intake.firstStageIntake(Intake.Direction.REVERSE);
                intake.secondStageTransport(Intake.Direction.REVERSE);
                intake.thirdStageTransport(Intake.Direction.REVERSE);
            }
        }
    });
    Thread UpdateShooterFromClose = new Thread(new Runnable() {
        public void run() {
            synchronized (shooter) {
                shooter.setVelocity(Shooter.SPEED_FROM_CLOSE);
                if (shooter.getVelocity() >= Shooter.SPEED_FROM_CLOSE + Shooter.error) {
                    shooter.setVelocity(Shooter.kfError);
                    sleep(Shooter.timeBetweenUpdates);
                } else shooter.setVelocity(Shooter.SPEED_FROM_CLOSE);
                sleep(WAIT_BETWEEN_UPDATE_SHOOTER_MID);

            }
        }
    });
    Thread UpdateShooterFromMID = new Thread(new Runnable() {
        public void run() {
            synchronized (shooter) {
                shooter.setVelocity(Shooter.SPEED_FROM_MID);
                if (shooter.getVelocity() >= Shooter.SPEED_FROM_MID + Shooter.error) {
                    shooter.setVelocity(Shooter.kfError);
                    sleep(Shooter.timeBetweenUpdates);
                } else shooter.setVelocity(Shooter.SPEED_FROM_MID);
                sleep(WAIT_BETWEEN_UPDATE_SHOOTER_MID);
            }
        }
    });


    @Override
    public void init() {
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        drive = new Drive(hardwareMap, 0, false);
        intake = new Intake(hardwareMap);

        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);
        LEDs = new RGBController(hardwareMap);
        power = 0;
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_x;
        strafe = -gamepad1.left_stick_y;
        rotate = gamepad1.right_trigger > PRESS ? autoAline.rotationForAlignment(getRuntime(), gamepad1.right_stick_x) : gamepad1.right_stick_x;

        drive.drive(forward, strafe, rotate);

        shooter.RunByPidf();


        if (gamepad1.dpadRightWasPressed()) {
            shooter.setPower(shooter.SHOOTER_POWERS[shooter.MID_CELL_IN_POWERS][Shooter.voltageFindRange(voltageSensor.getVoltage())] + shooter.powerOffset);
        }

        if (gamepad1.dpadLeftWasPressed()) {
            shooter.setPower(shooter.SHOOTER_POWERS[shooter.FAR_CELL_IN_POWERS][Shooter.voltageFindRange(voltageSensor.getVoltage())] + shooter.powerOffset);
        }

        if (gamepad1.dpadDownWasPressed()) {
            shooter.setPower(shooter.SHOOTER_POWERS[shooter.CLOSE_CELL_IN_POWERS][Shooter.voltageFindRange(voltageSensor.getVoltage())] + shooter.powerOffset);
        }
        if (gamepad1.left_trigger > PRESS) {
            shooter.setPower(0);
        }


        if (gamepad2.dpadUpWasPressed()) {
            shooter.powerOffset += BIG_OFFSET_POWER;
        }


        if (gamepad2.dpadDownWasPressed()) {
            shooter.powerOffset -= BIG_OFFSET_POWER;
        }

        if (gamepad2.dpadRightWasPressed()) {
            shooter.powerOffset += SMALL_OFFSET_POWER;
        }

        if (gamepad2.dpadLeftWasPressed()) {
            shooter.powerOffset -= SMALL_OFFSET_POWER;
        }

        if (gamepad1.xWasPressed()) {
            if (intake.isIntakeActive()) {
                intake.firstStageIntake(Intake.Direction.STOP);
                intake.secondStageTransport(Intake.Direction.STOP);
                intake.thirdStageTransport(Intake.Direction.STOP);
            } else {
                intake.firstStageIntake(Intake.Direction.FORWARD);
                intake.secondStageTransport(Intake.Direction.REVERSE);
                intake.thirdStageTransport(Intake.Direction.REVERSE);
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

        if (gamepad1.rightBumperWasReleased() || gamepad1.leftBumperWasReleased()) {
            intake.thirdStageTransport(Intake.Direction.REVERSE);
        }
        if (gamepad1.leftStickButtonWasPressed()) {
            ShootingFromFar = false;
            ShootingFromClose = true;


        }
        if (gamepad1.rightStickButtonWasPressed()) {
            ShootingFromClose = false;
            ShootingFromFar = true;


        }
        if (ShootingFromClose && !UpdateShooterFromMID.isAlive() && !UpdateShooterFromClose.isAlive()) {
            UpdateShooterFromClose.start();

        }
        if (ShootingFromFar && !UpdateShooterFromClose.isAlive() && !UpdateShooterFromMID.isAlive()) {
            UpdateShooterFromMID.start();
        }

        if (gamepad1.yWasPressed()) {
            if (parking.isRobotRaised()) {
                parking.lowerRobot();
            } else {
                parking.raiseRobot();
                ShootingFromClose = false;
                ShootingFromFar = false;
                shooter.setPower(0);
                intake.stop();

            }
        }
        if (gamepad1.bWasPressed()) {
            intake.shootVolley();
        }
        if (gamepad1.aWasPressed()) {
            intake.shootVolleyMid();
        }
        if (!parking.isRobotRaised() && !intake.isShootVolleyAlive()) {
            parking.stayClosed();
        }

        if (gamepad2.aWasPressed()) {
            autoAline.switchAlliance();
        }

        if (gamepad2.bWasPressed()) {
            shooter.setVelocity(Shooter.SPEED_FROM_MID);

        }


        if (intake.validateAreThreeIn(75)) {
            LEDs.setColorGreen();
        } else if (intake.validateAreBallStuck(75)){
            LEDs.setColorOrange();
        } else if (intake.validateAreNotThreeIn(75)) {
            LEDs.turnOff();
        }

        printTelemetry();

    }
    @Override
    public void stop() {
        if (waitForLongXPress.isAlive()) {
            waitForLongXPress.interrupt();
        }
        parking.stop();
        autoAline.stop();
        intake.stop();
    }

    public void printTelemetry() {
        telemetry.addData("voltage", voltageSensor.getVoltage());
        telemetry.addData("rotate", rotate);
        telemetry.addData("velocity", shooter.getVelocity());

        telemetry.update();
    }

}