package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    public final int CLOSE_CELL_IN_POWERS = 0;
    public final int MID_CELL_IN_POWERS = 1;
    public final int FAR_CELL_IN_POWERS = 2;
    public final double[][] SHOOTER_POWERS = {
            {0.65, 0.65, 0.65, 0.65},   // CLOSE
            {0.83, 0.78, 0.73, 0.7},   // MID
            {0.940, 0.890, 0.860, 0.830}    // FAR
    };


    DcMotor ShooterMotor;

    public double powerOffset = 0;


    public Shooter(HardwareMap hardwareMap) {

        ShooterMotor = hardwareMap.dcMotor.get("shooter Motor");

        ShooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }



    public void setPower(double power) {
        ShooterMotor.setPower(power);
    }

    public double getPower() {
        return ShooterMotor.getPower();
    }

    public static int voltageFindRange(double voltage) {

        if (Double.isNaN(voltage)) return 0;

        if (voltage <= 11) return 0;
        if (voltage <= 12) return 1;
        if (voltage <= 13) return 2;

        return 3;
    }
}
