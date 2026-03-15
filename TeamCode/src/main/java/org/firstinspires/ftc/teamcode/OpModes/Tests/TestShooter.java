package org.firstinspires.ftc.teamcode.OpModes.Tests;


import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
@Config
public class TestShooter extends OpMode {
    public DcMotorEx shooterMotor;
    double curTargetVelocity = velocity;


//    double F = 0;
//    double P = 300;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.0001};

    int stepIndex = 1;


//    Shooter shooter;
//    Intake intake;
//    Limelight3A limelight;
//    VoltageSensor voltageSensor;


    public static double velocity = 1000;
    public static double error = 70;
    public static double kfError = 10;
    public static double kp = 0.00001;
    public static double kd = 0.0001;
    public static double kf = 700;
    public static long timeBetweenUpdates = 20;


    Shooter shooter;
    Intake intake;
    VoltageSensor voltageSensor;


    @Override
    public void init() {

        shooter = new Shooter(hardwareMap);
        telemetry.addLine("Int complete");

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kp, 0, kd, kf);
        shooter.shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        intake = new Intake(hardwareMap);
        intake.startAll(Intake.Direction.FORWARD);
//
        voltageSensor = hardwareMap.voltageSensor.iterator().next();
//
//
//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        telemetry.setMsTransmissionInterval(11);
//
//        limelight.pipelineSwitch(0);
//        limelight.start();


    }

    @Override
    public void loop() {
        if (shooter.getVelocity() >= velocity + error){
            shooter.setVelocity(kfError);
            sleep( timeBetweenUpdates);
        } else shooter.setVelocity(velocity);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kp, 0, kd, kf);
        shooter.shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);


        double curVelocity = shooter.getVelocity();
        double error = curTargetVelocity - curVelocity;


        if (gamepad1.dpadUpWasPressed()) {
            velocity += 50;
        }

        if (gamepad1.dpadRightWasPressed()) {
            velocity += 50;
        }

        if (gamepad1.dpadDownWasPressed()) {
            velocity -= 50;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            velocity -= 50;
        }

        telemetry.addData("VOLTAGE", voltageSensor.getVoltage());
        telemetry.addData("Velocity", shooter.getVelocity());
        telemetry.addData("Target Velocity", velocity);
        telemetry.update();


    }


    @Override
    public void stop() {
        shooter.setVelocity(0);
    }


}
