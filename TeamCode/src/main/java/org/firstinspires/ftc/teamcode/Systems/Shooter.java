package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class Shooter {


    public final double SHOOTER_SPEED_ONE = 0.7;
    public final double SHOOTER_SPEED_TWO = 0.75;
    public final double SHOOTER_SPEED_THREE = 0.8;
    public final double SHOOTER_SPEED_FOUR = 0.85;


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

}
