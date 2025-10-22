package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    final double IN_POWER = 1;
   // DcMotor inMotor;

    CRServo rightServo;
    CRServo leftServo;




    public Intake(HardwareMap hardwareMap) {
       // inMotor = hardwareMap.dcMotor.get("Motor Intake");

        rightServo = hardwareMap.get(CRServo.class,"rightServo");
        leftServo = hardwareMap.get(CRServo.class,"leftServo");

       // inMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void activateIntake(){
       // inMotor.setPower(IN_POWER);
        rightServo.setDirection(CRServo.Direction.REVERSE);
        leftServo.setDirection(CRServo.Direction.FORWARD);

        rightServo.setPower(IN_POWER);
        leftServo.setPower(IN_POWER);
    }

    public void deactivateIntake(){
        //inMotor.setPower(0);
        rightServo.setPower(0);
        leftServo.setPower(0);

    }

    public void activateEjection(){
        //inMotor.setPower(-IN_POWER);
        rightServo.setPower(IN_POWER);
        leftServo.setPower(IN_POWER);

    }


    public boolean isActive(){
       // if(inMotor.getPower() == 0)
         if (rightServo.getPower() == 0)
            return false;
        return true;
    }

}
