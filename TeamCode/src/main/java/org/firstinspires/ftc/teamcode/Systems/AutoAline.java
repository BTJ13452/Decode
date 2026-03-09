package org.firstinspires.ftc.teamcode.Systems;

import static java.lang.Runtime.getRuntime;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.OpModes.TeleOp.BTJTeleOp;

public class AutoAline {



    public enum AllianceColor {
        BLUE,
        RED;

        public int pipeLineNumber() {
            switch (this) {
                case BLUE:
                    return 1;
                case RED:
                    return 0;
                default:
                    return 0;
            }
        }
    }

    AllianceColor allianceColor;
    Limelight3A limelight;

    double kp = 0.02;
    double error = 0;
    double lastError = 0;
    double goalTx = 0;
    double angleTolerance = 0.2;
    double kd = -0.000001;
    double lastTime = 0;
    final double MAX_DRIVE_SPEED = 0.5;

    public AutoAline(HardwareMap hardwareMap, AllianceColor allianceColor) {
        this.allianceColor = allianceColor;

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(allianceColor.pipeLineNumber());
        limelight.start();
    }

    public double rotationForAlignment(double curTime, double defaultValue) {
        LLResult llResult = limelight.getLatestResult();
        llResult.getTx();
        error = llResult.getTx() - goalTx;


        if (llResult != null && llResult.isValid()) {

            if (Math.abs(error) < angleTolerance) {
                return 0;
            } else {
                double pTerm = error * kp;

                double dt = curTime - lastTime;
                double dTerm = ((error - lastError) / dt) * kd;


                return Range.clip(pTerm + dTerm, -MAX_DRIVE_SPEED, MAX_DRIVE_SPEED);

            }
        }

        return defaultValue;
    }

    public void switchAlliance() {
        switch (allianceColor) {
            case RED:
                allianceColor = AllianceColor.BLUE;
                break;
            case BLUE:
                allianceColor = AllianceColor.RED;
        }
        limelight.pipelineSwitch(allianceColor.pipeLineNumber());
    }

    public void stop() {
        limelight.stop();
    }


}







