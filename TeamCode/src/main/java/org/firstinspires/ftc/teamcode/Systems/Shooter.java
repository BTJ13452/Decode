package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    DcMotor ShooterMotorR;
    DcMotor ShooterMotorL;


    public Shooter(HardwareMap hardwareMap){
        ShooterMotorR = hardwareMap.dcMotor.get ("ShooterMotorR");
        ShooterMotorL = hardwareMap.dcMotor.get ("ShooterMotorL");
        ShooterMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotorL.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void setMotorSpeed(double speed){
        speed = Math.max(0, Math.min(speed, 1));
        ShooterMotorR.setPower(speed);
        ShooterMotorL.setPower(speed);
    }
    public double getMotorSpeed(){
        return ShooterMotorL.getPower();
    }


    }



