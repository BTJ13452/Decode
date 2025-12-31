package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    public enum Distance {
        CLOSE,
        MID,
        FAR;

        public static int getValue(Distance d) {
            switch (d) {
                case CLOSE:
                    return 0;
                case MID:
                    return 1;
                case FAR:
                    return 2;
            }
            return -1;
        }
    }
    double[][] SHOOTER_POWERS =
            { {0.7/*close*/,                                                                       0.655, 0.65, 0.65},
             {0.8/*mid*/,                                                                         0.68, 0.66, 0.64},
               {0.9 /*far*/,                                                                      0.855, 0.8, 0.78}};


    public double powerOffset;
    DcMotor ShooterMotorR;
    DcMotor ShooterMotorL;

    public Shooter(HardwareMap hardwareMap) {
        ShooterMotorR = hardwareMap.dcMotor.get("ShooterMotorR");
        ShooterMotorL = hardwareMap.dcMotor.get("ShooterMotorL");
        ShooterMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        powerOffset = 0;
    }

    public void setPower(double power) {
        ShooterMotorR.setPower(power);
        ShooterMotorL.setPower(power);
    }

    public double getPower() {
        return ShooterMotorL.getPower();
    }

    public void autoSpeed(Distance d, double voltage) {
        double power = SHOOTER_POWERS[Distance.getValue(d)][0];
        setPower(power + powerOffset);
    }
    public int voltageFindRange(double voltage) {
        if (voltage > 11 && voltage <= 12)
            return 0;

        if (voltage > 12 && voltage <= 13)
            return 1;

        if (voltage > 13 && voltage <= 14)
            return 2;

        if (voltage > 14)
            return 3;

        return -1;
    }

}



