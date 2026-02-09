package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Systems.Drive;

@TeleOp
public class AprilTagAutoAlinment extends OpMode {

    Limelight3A limelight;
    Drive drive;

    double kp = 0.002;
    double error = 0;
    double lastError = 0;
    double goalTx = 2;
    double angleTolerance = 0.1;
    double kd = 0.0001;
    double curTime = 0;
    double lastTme = 0;


    double forward, strafe, rotate;


    double[] stepSizes = {1, 0.1, 0.001, 0.0001};
    int stepIndex = (4);


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        drive = new Drive(hardwareMap, 0);
        limelight.pipelineSwitch(0);
        limelight.start();
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
        rotate = gamepad1.right_stick_x;

        LLResult llResult = limelight.getLatestResult();
        telemetry.addData("tx", llResult.getTx());
        telemetry.addData("rotate", rotate);
        telemetry.update();


        if (gamepad1.a) {
            if (llResult != null && llResult.isValid()) {
                error = goalTx - llResult.getTx();
                if (Math.abs(error) < angleTolerance) {
                    rotate = 0;

                } else {
                    double pTerm = error * kp;

                    curTime = getRuntime();

                    double dt = curTime - lastTme;
                    double dTerm = ((error - lastError) / dt) * kd;

                    rotate = Range.clip(pTerm + dTerm, -4, 4);
                    telemetry.addData("rotate", rotate);
                    telemetry.update();
                    lastError = error;
                    lastTme = curTime;
                    lastError = 0;

                }

            }
        } else {
            lastTme = getRuntime();


        }
        drive.drive(forward, strafe, rotate);

        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
            lastError = 0;
        } else {
            lastTme = getRuntime();
        }

    }


}