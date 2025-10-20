package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    final double IN_POWER = 1;
    DcMotor inMotor;

    public Intake(HardwareMap hardwareMap) {
        inMotor = hardwareMap.dcMotor.get("Motor Intake");

        inMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void activateIntake(){
        inMotor.setPower(IN_POWER);
    }

    public void deactivateIntake(){
        inMotor.setPower(0);
    }

    public void activateEjection(){
        inMotor.setPower(-IN_POWER);
    }


    public boolean isActive(){
        if(inMotor.getPower() == 0)
            return false;
        return true;
    }

}
