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

    CRServo wheel;

    CRServo midRightServo;
    CRServo midLeftServo;

    public Intake(HardwareMap hardwareMap) {
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");

        midRightServo = hardwareMap.get(CRServo.class, "midRightServo");
        midLeftServo = hardwareMap.get(CRServo.class, "midLeftServo");

        midRightServo.setDirection(CRServo.Direction.REVERSE);
        midLeftServo.setDirection(CRServo.Direction.FORWARD);

        rightServo.setDirection(CRServo.Direction.REVERSE);
        leftServo.setDirection(CRServo.Direction.FORWARD);
  
        wheel = hardwareMap.get(CRServo.class, "wheel");

    }

    public void activateIntake() {
        rightServo.setPower(IN_POWER);
        leftServo.setPower(IN_POWER);
    }
    public void rightActivateMidIntake(){
        midRightServo.setPower(IN_POWER);
    }
    public void leftActivateMidIntake() {
        midLeftServo.setPower(IN_POWER);
    }

    public void deactivateIntake() {
        rightServo.setPower(0);
        leftServo.setPower(0);

    }

    public void deactivateAllIntake() {
        rightServo.setPower(0);
        leftServo.setPower(0);
        midRightServo.setPower(0);
        midLeftServo.setPower(0);
        wheel.setPower(0);

    }

    public void activateEjection() {
        rightServo.setPower(-IN_POWER);
        leftServo.setPower(-IN_POWER);
        midRightServo.setPower(-IN_POWER);
        midLeftServo.setPower(-IN_POWER);
        wheel.setPower(IN_POWER);



    }
    public void ActivateWheel() {
        wheel.setPower(-IN_POWER);
    }

    public void deactivateWheel() {
        wheel.setPower(0);
    }


    public boolean isActive() {
        if (rightServo.getPower() == 0)
            return false;
        return true;
    }

    public boolean isMidRightActive() {
        if (midRightServo.getPower() == 0)
            return false;
        return true;
    }

    public boolean isMidLeftActive() {
        if (midLeftServo.getPower() == 0)
            return false;
        return true;
    }

    public boolean isMidRightNotActive() {
        if (midRightServo.getPower() != 0)
            return false;
        return true;
    }

    public boolean isMidLeftNotActive() {
        if (midLeftServo.getPower() != 0)
            return false;
        return true;
    }


    public void deMidRightActivateIntake() {
        midRightServo.setPower(0);

    }

    public void deMidLeftActivateIntake() {
        midLeftServo.setPower(0);

    }
}