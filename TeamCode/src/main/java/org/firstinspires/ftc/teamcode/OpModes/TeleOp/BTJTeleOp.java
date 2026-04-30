package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static android.os.SystemClock.sleep;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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
    final int CONST_SHOOTER_SPEED = 1300;
    final double PRESS = 0.1;
    double power;
    double forward, strafe, rotate;
    public double targetVelocity;
    public double preTargetVelocity;
    boolean seenBasket = false;
    boolean wasRightStickButon = false;


    VoltageSensor voltageSensor;
    Drive drive;
    Intake intake;
    Shooter shooter;
    Parking parking;
    RGBController LEDs;
    AutoAline autoAline;
    LLResult llResult;
    Limelight3A limelight;
    Thread waitForLongXPress = new Thread(new Runnable() {
        @Override
        public void run() {
            gamepad1.xWasReleased();
            sleep(LONG_PRESS_MILLISECONDS);
            if (!gamepad1.xWasReleased()) {
                intake.startAll(Intake.Direction.REVERSE);
                intake.firstStageIntake(Intake.Direction.FORWARD);
                intake.secondStageTransport(Intake.Direction.REVERSE);
                intake.thirdStageTransport(Intake.Direction.REVERSE);
            }
        }
    });


    @Override
    public void init() {
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        drive = new Drive(hardwareMap, 0, false);
        intake = new Intake(hardwareMap);

        shooter = new Shooter(hardwareMap);
        parking = new Parking(hardwareMap);
        LEDs = new RGBController(hardwareMap);

        power = 0;

        shooter.runByPidf();
        preTargetVelocity = 1300;

        limelight.pipelineSwitch(1);
        limelight.start();

    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_x;
        strafe = -gamepad1.left_stick_y;
        rotate = gamepad1.right_trigger > PRESS ? autoAline.rotationForAlignmentFar(getRuntime(), gamepad1.right_stick_x) : gamepad1.right_stick_x;

        drive.drive(forward, strafe, rotate);

        llResult = limelight.getLatestResult();
        shooter.runByPidf();


            if (llResult != null) {
                llResult = limelight.getLatestResult();
                double ta = llResult.getTa();
                targetVelocity = (shooter.shooterByDistance(ta));
                preTargetVelocity = (shooter.getVelocity());
                shooter.setVelocity(targetVelocity);
                seenBasket = true;
                wasRightStickButon = true;

            }
            if (llResult.getTa() == 0) {
                llResult = limelight.getLatestResult();
                shooter.setVelocity(CONST_SHOOTER_SPEED);

            }
        shooter.runByPidf();

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


        if (gamepad1.yWasPressed()) {
            if (parking.isRobotRaised()) {
                parking.lowerRobot();
            } else {
                parking.raiseRobot();
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
        if (gamepad1.leftStickButtonWasPressed()){
            intake.shootVolley();
        }
        if (!parking.isRobotRaised() && !intake.isShootVolleyAlive()) {
            parking.stayClosed();
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
        telemetry.addData("target velocity", shooter.getVelocity());
        telemetry.addData("pre target veocity",preTargetVelocity);
        if (llResult != null) {
            telemetry.addData("ta", llResult.getTa());
        }


        telemetry.update();
    }

}