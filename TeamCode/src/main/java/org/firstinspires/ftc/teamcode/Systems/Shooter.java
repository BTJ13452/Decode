package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class Shooter {

    public enum Distance {
        ONE,
        TWO,
        THREE,
        FOUR;

        public int getValue() {
            switch (this) {
                case ONE:
                    return 0;
                case TWO:
                    return 1;
                case THREE:
                    return 2;
                case FOUR:
                    return 3;
            }
            return 0;
        }
    }

    public  final double SHOOTER_SPEED_ONE = 0.7;
    public final double SHOOTER_SPEED_TWO = 0.8;
    public final double SHOOTER_SPEED_THREE = 0.9;
    public final double SHOOTER_SPEED_FOUR = 1;


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



    public int voltageFindRange(double voltage) {

        if (Double.isNaN(voltage)) return 0;

        if (voltage <= 9) return 0;
        if (voltage <= 10) return 1;
        if (voltage <= 11) return 2;
        if (voltage <= 12) return 3;
        if (voltage <= 13) return 4;

        return 5;
    }
}
