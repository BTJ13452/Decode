package org.firstinspires.ftc.teamcode.OpModes.Tests;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
@Config
public class TestShooter extends OpMode {

    public double targetVelocity;
    public double preTargetVelocity;
    public double error = 70;
    public double kfError = 10;
    public double kp = 0.00001;
    public double kd = 0.0001;
    public double kf = 400;
    boolean targetNull = true;


    Shooter shooter;
    Intake intake;
    VoltageSensor voltageSensor;
    LLResult llResult;
    Limelight3A limelight;


    @Override
    public void init() {

        shooter = new Shooter(hardwareMap);
//        telemetry.addLine("Int complete");

        intake = new Intake(hardwareMap);
        intake.startAll(Intake.Direction.FORWARD);
        shooter.runByPidf();


        voltageSensor = hardwareMap.voltageSensor.iterator().next();
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(1);
        limelight.start();


    }

    @Override
    public void loop() {
        if (llResult != null) {
            llResult = limelight.getLatestResult();
            double ta = llResult.getTa();
////            targetVelocity = (shooter.ShooterByDistance(ta));
        }
        shooter.setVelocity(targetVelocity);

        shooter.runByPidf();

//        if (shooter.getVelocity() >= targetVelocity + error) {
//            shooter.setVelocity(kfError);
//            telemetry.addLine("shooter stabilisation");
//
//        } else targetNull = true;

        if (gamepad1.dpadUpWasPressed()) {
            targetVelocity += 50;
        }

        if (gamepad1.dpadRightWasPressed()) {
            targetVelocity += 25;
        }

        if (gamepad1.dpadDownWasPressed()) {
            targetVelocity -= 50;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            targetVelocity -= 25;
        }

        telemetry.addData("Velocity", shooter.getVelocity());
        telemetry.addData("Target Velocity", targetVelocity);
        telemetry.addData("null", llResult == null);
        if (llResult != null) {
            telemetry.addData("ta", llResult.getTa());

        }

        telemetry.update();

    }


    @Override
    public void stop() {
        shooter.setVelocity(0);
    }
}

