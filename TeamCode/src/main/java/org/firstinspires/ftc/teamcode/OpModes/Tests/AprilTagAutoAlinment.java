package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Systems.Drive;

@TeleOp
@Disabled
public class AprilTagAutoAlinment extends OpMode {

    Limelight3A limelight;
    Drive drive;

    double kp = 0.02;
    double error = 0;
    double lastError = 0;
    double goalTx = 0;
    double angleTolerance = 0.2;
    double kd = -0.00001;
    double curTime = 0;
    double lastTme = 0;
    final double MAX_DRIVE_SPEED = 0.4;


    double forward, strafe, rotate;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        drive = new Drive(hardwareMap, 0, false);
        limelight.pipelineSwitch(2);
        limelight.start();
        telemetry.addLine("all good fo start");
    }

    @Override
    public void start() {
        resetRuntime();
        curTime = getRuntime();
    }

    @Override
    public void loop() {
        forward = -gamepad1.left_stick_x;
        strafe = gamepad1.left_stick_y;

        LLResult llResult = limelight.getLatestResult();
        llResult.getTx();


        if (gamepad1.right_bumper && llResult != null && llResult.isValid()) {
            error = llResult.getTx() - goalTx;
            if (Math.abs(error) < angleTolerance) {
                rotate = 0;
            } else {
                double pTerm = error * kp;

                curTime = getRuntime();

                double dt = curTime - lastTme;
                double dTerm = ((error - lastError) / dt) * kd;

                rotate = Range.clip(pTerm + dTerm, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);

                lastError = error;
                lastTme = curTime;
            }
        } else {
            lastError = 0;
            lastTme = getRuntime();
            rotate = gamepad1.right_stick_x;
        }

        drive.drive(forward, strafe, rotate);

        telemetry.addData("rotate", rotate);
        telemetry.addData("tx", llResult.getTx());
        telemetry.update();
    }
}