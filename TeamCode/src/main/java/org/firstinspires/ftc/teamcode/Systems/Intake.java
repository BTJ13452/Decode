package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    final double IN_POWER = 1;

    CRServo rightServo;
    CRServo leftServo;




    public Intake(HardwareMap hardwareMap) {
        rightServo = hardwareMap.get(CRServo.class,"rightServo");
        leftServo = hardwareMap.get(CRServo.class,"leftServo");

        rightServo.setDirection(CRServo.Direction.REVERSE);
        leftServo.setDirection(CRServo.Direction.FORWARD);
    }

    public void activateIntake(){
        rightServo.setPower(IN_POWER);
        leftServo.setPower(IN_POWER);
    }

    public void deactivateIntake(){
        rightServo.setPower(0);
        leftServo.setPower(0);

    }

    public void activateEjection(){
        rightServo.setPower(IN_POWER);
        leftServo.setPower(IN_POWER);

    }


    public boolean isActive(){
         if (rightServo.getPower() == 0)
            return false;
        return true;
    }

}
