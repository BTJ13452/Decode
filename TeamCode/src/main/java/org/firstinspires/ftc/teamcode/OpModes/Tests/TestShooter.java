package org.firstinspires.ftc.teamcode.OpModes.Tests;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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


    Shooter shooter;
    Intake intake;
    LLResult llResult;
    Limelight3A limelight;


    @Override
    public void init() {

        shooter = new Shooter(hardwareMap);

        intake = new Intake(hardwareMap);
        intake.startAll(Intake.Direction.FORWARD);
        shooter.runByPidf();

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(1);
        limelight.start();


    }

    @Override
    public void loop() {
        llResult = limelight.getLatestResult();

        shooter.runByPidf();
        shooter.setVelocity(targetVelocity);

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

