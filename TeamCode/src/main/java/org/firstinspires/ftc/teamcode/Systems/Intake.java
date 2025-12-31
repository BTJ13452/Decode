package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    public enum Direction {
        FORWARD,
        REVERSE,
        STOP;

        public static int getValue(Direction d){
            switch (d){
                case FORWARD:
                    return 1;
                case REVERSE:
                    return -1;
                default:
                    return 0;
            }
        }
    }

    public enum Cell{
        RIGHT,
        LEFT;
    }

    final double IN_POWER = 1;

    CRServo rightFirstStageIntakeServo;
    CRServo leftFirstStageIntakeServo;
    CRServo rightSecondStageTransportServo;
    CRServo leftSecondStageTransportServo;
    CRServo rightThirdStageTransportServo;
    CRServo leftThirdStageTransportServo;

    public Intake(HardwareMap hardwareMap) {
        rightFirstStageIntakeServo = hardwareMap.get(CRServo.class, "rightServo");
        leftFirstStageIntakeServo = hardwareMap.get(CRServo.class, "leftServo");

        rightSecondStageTransportServo = hardwareMap.get(CRServo.class, "midRightServo");
        leftSecondStageTransportServo = hardwareMap.get(CRServo.class, "midLeftServo");

        leftThirdStageTransportServo = hardwareMap.get(CRServo.class, "wheel");
        rightThirdStageTransportServo = hardwareMap.get(CRServo.class, "wheel2");

        rightSecondStageTransportServo.setDirection(CRServo.Direction.REVERSE);
        leftSecondStageTransportServo.setDirection(CRServo.Direction.FORWARD);

        rightFirstStageIntakeServo.setDirection(CRServo.Direction.REVERSE);
        leftFirstStageIntakeServo.setDirection(CRServo.Direction.FORWARD);

        leftThirdStageTransportServo.setDirection(CRServo.Direction.FORWARD);
        rightThirdStageTransportServo.setDirection(CRServo.Direction.REVERSE);
    }


    public void firstStageIntake(Direction d) {
        rightFirstStageIntakeServo.setPower(IN_POWER * Direction.getValue(d));
        leftFirstStageIntakeServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void rightSecondStageTransport(Direction d) {
        rightSecondStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void leftSecondStageTransport(Direction d) {
        leftSecondStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void secondStageTransport(Direction d) {
        rightSecondStageTransport(d);
        leftSecondStageTransport(d);
    }

    public void thirdStageTransport(Direction d) {
        leftThirdStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
        rightThirdStageTransportServo.setPower(IN_POWER * Direction.getValue(d));
    }

    public void startAll(Direction d) {
        firstStageIntake(d);
        secondStageTransport(d);
        thirdStageTransport(d);
    }

    public boolean isIntakeActive() {
        return !(rightFirstStageIntakeServo.getPower() == 0);
    }

    public void transportArtifactToShooter(Cell cell){
        firstStageIntake(Direction.FORWARD);
        if(cell == Cell.RIGHT){
            rightSecondStageTransport(Direction.FORWARD);
            leftSecondStageTransport(Direction.REVERSE);
        }
        else {
            leftSecondStageTransport(Direction.FORWARD);
            rightSecondStageTransport(Direction.REVERSE);
        }
        thirdStageTransport(Direction.FORWARD);
    }

}