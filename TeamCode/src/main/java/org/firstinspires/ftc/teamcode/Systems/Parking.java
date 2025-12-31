package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Parking {
    final double ENCODER_RESOLUTION = 751.8; //PPM
    final double RADIOS = 0.0191; //CM
    final double TICK_PER_CM = ENCODER_RESOLUTION / (2 * Math.PI * RADIOS);
    final double HEIGHT = 45.72; //CM
    final int HEIGHT_IN_TICKS = (int) Math.round(HEIGHT*TICK_PER_CM);

    final double MOTOR_POWER_FOR_RAISING = 0.2;
    final double MOTOR_POWER_FOR_LOWERING = -0.2;
    final int ACCEPTABLE_HEIGHT_DISTANCE = 1; //CM

    boolean isRobotUp;

    DcMotor rightElevator;
    DcMotor leftElevator;
    TouchSensor rightMagneticSensor;
    TouchSensor leftMagneticSensor;

    Thread fixElevatorsDistance = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!isElevatorInPosition(rightElevator) || !isElevatorInPosition(leftElevator)) {
                if (distanceBetweenElevators(rightElevator, leftElevator) > ACCEPTABLE_HEIGHT_DISTANCE) {
                    rightElevator.setPower(0);
                } else if (distanceBetweenElevators(leftElevator, rightElevator) > ACCEPTABLE_HEIGHT_DISTANCE) {
                    leftElevator.setPower(0);
                } else {
                    rightElevator.setPower(MOTOR_POWER_FOR_RAISING);
                    leftElevator.setPower(MOTOR_POWER_FOR_RAISING);
                }
            }
        }
    });


    public Parking(HardwareMap hardwareMap) {
        isRobotUp = false;

        rightElevator = hardwareMap.dcMotor.get("Right Elevator");
        leftElevator = hardwareMap.dcMotor.get("Left Elevator");

        rightElevator.setDirection(DcMotorSimple.Direction.FORWARD);
        leftElevator.setDirection(DcMotorSimple.Direction.REVERSE);

        rightMagneticSensor = hardwareMap.touchSensor.get("Right Magnetic Sensor");
        leftMagneticSensor = hardwareMap.touchSensor.get("Left Magnetic Sensor");

        resetElevator();
    }

    public void raiseRobot() {
        isRobotUp = true;

        rightElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightElevator.setTargetPosition(HEIGHT_IN_TICKS);
        leftElevator.setTargetPosition(HEIGHT_IN_TICKS);

        rightElevator.setPower(MOTOR_POWER_FOR_RAISING);
        leftElevator.setPower(MOTOR_POWER_FOR_RAISING);

        rightElevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftElevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fixElevatorsDistance.start();
    }

    public boolean isElevatorInPosition(DcMotor elevator) {
        return (elevator.getTargetPosition() - elevator.getCurrentPosition() / TICK_PER_CM < ACCEPTABLE_HEIGHT_DISTANCE);
    }

    public double distanceBetweenElevators(DcMotor first, DcMotor second) {
        return (first.getCurrentPosition() - second.getCurrentPosition()) / TICK_PER_CM;
    }


    public void lowerRobot() {
        if (fixElevatorsDistance.isAlive()) {
            fixElevatorsDistance.interrupt();
        }

        leftElevator.setTargetPosition(0);
        rightElevator.setTargetPosition(0);

        leftElevator.setPower(MOTOR_POWER_FOR_LOWERING);
        rightElevator.setPower(MOTOR_POWER_FOR_LOWERING);

        //isRobotUp = false;
    }

    public void resetElevator() {
        while (!leftMagneticSensor.isPressed() || !rightMagneticSensor.isPressed()) {
            if (!leftMagneticSensor.isPressed())
                leftElevator.setPower(MOTOR_POWER_FOR_LOWERING);
            if (!rightMagneticSensor.isPressed())
                rightElevator.setPower(MOTOR_POWER_FOR_LOWERING);
        }

        rightElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightElevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftElevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void stayClosed(){
        stayClosed(rightElevator,rightMagneticSensor);
        stayClosed(leftElevator,leftMagneticSensor);
    }

    private void stayClosed(DcMotor elevator, TouchSensor magnet){
        if(!magnet.isPressed()){
            elevator.setPower(MOTOR_POWER_FOR_LOWERING);
        }else {
            elevator.setPower(0);
        }
    }

    public boolean isRobotRaised() {
        return isRobotUp;
    }

    public double getPower(){
        return rightElevator.getPower();
    }

    public void stop(){
        if(fixElevatorsDistance.isAlive()){
            fixElevatorsDistance.interrupt();
        }
    }
}



