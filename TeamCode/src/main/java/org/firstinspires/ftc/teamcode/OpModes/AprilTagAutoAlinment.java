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
    double goalTx = 8;
    double angleTolerance = 0.2;
    double kd = 0.0001;
    double curTime = 0;
    double lastTme = 0;


    double forward, strafe, rotate;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        drive = new Drive(hardwareMap, 0);
        limelight.pipelineSwitch(0);
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
        rotate = gamepad1.right_stick_x;

        LLResult llResult = limelight.getLatestResult();
        llResult.getTx();


        if (gamepad1.b) {
            if (llResult != null && llResult.isValid()) {
                error = goalTx - llResult.getTx();
                if (Math.abs(error) < angleTolerance) {
                    rotate = 0;

                } else {
                    double pTerm = error * kp;

                    curTime = getRuntime();

                    double dt = curTime - lastTme;
                    double dTerm = ((error - lastError) / dt) * kd;

                    rotate = Range.clip(pTerm + dTerm, -0.4, 0.4);

                    lastError = error;
                    lastTme = curTime;

                }

            } else {
                lastTme = getRuntime();
                lastError = 0;

            }


        } else {
            lastError = 0;
            lastTme = getRuntime();
        }
        drive.drive(forward, strafe, rotate);

        llResult.getTx();
        telemetry.addData("rotate", rotate);
        telemetry.addData("tx", llResult.getTx());
        telemetry.update();

    }
}