package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Shooter {

    public final int CLOSE_CELL_IN_POWERS = 0;
    public final int MID_CELL_IN_POWERS = 1;
    public final int FAR_CELL_IN_POWERS = 2;
    public static double error = 1;
    public static double kfError = 20;
    public static double kp = 0.00000001;
    public static double kd = 0.0000001;
    public static double kf = 400;
    public static long timeBetweenUpdates = 0;


    public final static double SPEED_FROM_MID = 1500;
    public final static double SPEED_FROM_CLOSE = 1200;

    public final double[][] SHOOTER_POWERS = {
            {0.8, 0.77, 0.71, 0.65, 0.63, 0.62, 0.61, 0.6},   // קרוב

            {0.85, 0.82, 0.76, 0.7, 0.69, 0.69, 0.67, 0.66},   // רחוק

            {0.98, 0.98, 0.98, 0.940, 0.890, 0.87, 0.860, 0.7}    // צמוד לקיר
//
//           10.5v 11v 11.5V, 12V, ,12.5V, 13V, 13+
    };


    public DcMotorEx shooterMotor;
    VoltageSensor voltageSensor;

    public double powerOffset = 0;


    public Shooter(HardwareMap hardwareMap) {

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter Motor");
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterMotor.setDirection(DcMotorEx.Direction.REVERSE);
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kp, 0.0, kd, kf);
        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

    }


    public void setPower(double power) {
        shooterMotor.setPower(power);
    }

    public void setVelocity(double targetV) {
        shooterMotor.setVelocity(targetV);
    }

    public double getVelocity() {
        return shooterMotor.getVelocity();
    }

    public void RunByPidf() {
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kp, 0.0, kd, kf);
        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

    }


    public static int voltageFindRange(double voltage) {

        if (Double.isNaN(voltage)) return 0;
        if (voltage <= 10) return 0;
        if (voltage <= 10.5) return 1;
        if (voltage <= 11) return 2;
        if (voltage <= 11.5) return 3;
        if (voltage <= 12) return 4;
        if (voltage <= 12.5) return 5;
        if (voltage <= 13) return 6;

        return 7;
    }


}



