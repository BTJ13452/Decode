package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    DcMotor ShoterMotor;
    DcMotor ShoterMotor2;


    public Shooter(HardwareMap hardwareMap){
        ShoterMotor = hardwareMap.dcMotor.get ("ShoterMotor");
        ShoterMotor = hardwareMap.dcMotor.get ("ShoterMotor2");
        ShoterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        ShoterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setMotorSpeed(double speed){
        speed = Math.max(0, Math.min(speed, 1));
        ShoterMotor.setPower(speed);
        ShoterMotor2.setPower(speed);
    }
    public double getMotorSpeed(){
        return ShoterMotor2.getPower();
    }

}

