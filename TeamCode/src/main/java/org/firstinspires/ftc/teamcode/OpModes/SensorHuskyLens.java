
package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.dfrobot.HuskyLens;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.Systems.Intake;

import java.util.concurrent.TimeUnit;


@TeleOp
@Disabled
public class SensorHuskyLens extends LinearOpMode {

    DcMotor ShooterMotorR;
    DcMotor ShooterMotorL;
    Intake intake;
    private final int READ_PERIOD = 1;

    private HuskyLens huskyLens;

    @Override
    public void runOpMode() {
        ShooterMotorR = hardwareMap.dcMotor.get("ShooterMotorR");
        ShooterMotorL = hardwareMap.dcMotor.get("ShooterMotorL");
        ShooterMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
        ShooterMotorL.setDirection(DcMotorSimple.Direction.FORWARD);

        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");


        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);


        rateLimit.expire();


        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }


        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        intake = new Intake(hardwareMap);

        telemetry.update();
        waitForStart();


        while (opModeIsActive()) {
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();


            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                telemetry.addData("y", blocks[i].id);

//                telemetry.addData("y ", blocks[i].y);
//                   telemetry.addData("x ", blocks[i].x);
                telemetry.addData("height", blocks[i].height);
                telemetry.addData("width", blocks[i].width);
//                telemetry.addData("left", blocks[i].left);
//                telemetry.addData("top", blocks[i].top);

            }
            for (int i = 0; i < blocks.length; i++) {
                if (blocks[i].height >= 24 && blocks[i].width >= 24) {
                    intake.leftSecondStageTransport(Intake.Direction.STOP);
                    intake.thirdStageTransport(Intake.Direction.STOP);
                    ShooterMotorR.setPower(0.7);
                    ShooterMotorL.setPower(0.7);


                }else{
                    intake.leftSecondStageTransport(Intake.Direction.STOP);
                    intake.thirdStageTransport(Intake.Direction.STOP);
                    ShooterMotorR.setPower(0);
                    ShooterMotorL.setPower(0);
                }
            }


            telemetry.update();
        }
    }
}