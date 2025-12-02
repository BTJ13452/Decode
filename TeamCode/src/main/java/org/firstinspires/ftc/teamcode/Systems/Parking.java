package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Parking {
    final double ENCODER_RESOLUTION = 751.8; //PPM
    final double RADIOS = 0.0191; //CM
    final double TICK_PER_CM = ENCODER_RESOLUTION / (2 * Math.PI * RADIOS);
    final double HEIGHT = 45.72; //CM
    final double MOTOR_POWER = 0.4;

    final int ACCEPTABLE_HEIGHT_DISTANCE = 1; //CM

    
    Thread fixElevatorsDistance;
    DcMotor rightElevator;
    DcMotor leftElevator;
    TouchSensor rightMagneticSensor;
    TouchSensor leftMagneticSensor;

    public Parking(HardwareMap hardwareMap) {
        rightElevator = hardwareMap.dcMotor.get("Right Elevator");
        leftElevator = hardwareMap.dcMotor.get("Left Elevator");

        rightElevator.setDirection(DcMotorSimple.Direction.FORWARD);
          leftElevator.setDirection(DcMotorSimple.Direction.REVERSE);

        rightElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightMagneticSensor = hardwareMap.touchSensor.get("Right Magnetic Sensor");
        leftMagneticSensor = hardwareMap.touchSensor.get("Left Magnetic Sensor");

        fixElevatorsDistance = new Thread(new Runnable() {
            @Override
            public void run() {
                while ((rightElevator.getTargetPosition() - rightElevator.getCurrentPosition()) / TICK_PER_CM > ACCEPTABLE_HEIGHT_DISTANCE || (leftElevator.getTargetPosition() - rightElevator.getCurrentPosition()) / TICK_PER_CM > ACCEPTABLE_HEIGHT_DISTANCE) {
                    if ((rightElevator.getCurrentPosition() - leftElevator.getCurrentPosition()) / TICK_PER_CM > ACCEPTABLE_HEIGHT_DISTANCE) {
                        rightElevator.setPower(0);
                    } else if ((leftElevator.getCurrentPosition() - rightElevator.getCurrentPosition()) / TICK_PER_CM > ACCEPTABLE_HEIGHT_DISTANCE) {
                        leftElevator.setPower(0);
                    } else {
                        rightElevator.setPower(MOTOR_POWER);
                        leftElevator.setPower(MOTOR_POWER);
                    }
                }
            }
        });
    }

    public void raiseRobot() {
        rightElevator.setTargetPosition((int) Math.round(HEIGHT * TICK_PER_CM));
        leftElevator.setTargetPosition((int) Math.round(HEIGHT * TICK_PER_CM));

        rightElevator.setPower(MOTOR_POWER);
        leftElevator.setPower(MOTOR_POWER);

        rightElevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftElevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fixElevatorsDistance.start();
    }
}