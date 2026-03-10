package org.firstinspires.ftc.teamcode.Systems;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.ArrayList;
import java.util.List;

public class Shooter {

    public final int CLOSE_CELL_IN_POWERS = 0;
    public final int MID_CELL_IN_POWERS = 1;
    public final int FAR_CELL_IN_POWERS = 2;
    public final double[][] SHOOTER_POWERS = {
            {0.8, 0.78, 0.72, 0.65, 0.63, 0.6, 0.68, 0.6},   // CLOSE
            {0.84, 0.82, 0.79, 0.7, 0.675, 0.68, 0.67, 0.665},   // MID
            {0.98, 0.98, 0.98, 0.940, 0.890, 0.87, 0.860, 0.7}    // FAR
//          10.5v 11v 11.5V, 12V, ,12.5V, 13V, 13+
    };


    DcMotor ShooterMotor;
    VoltageSensor voltageSensor;

    public double powerOffset = 0;


    public Shooter(HardwareMap hardwareMap) {

        ShooterMotor = hardwareMap.dcMotor.get("shooter Motor");
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");


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

