package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
public class TestShooter extends OpMode {


    Shooter shooter;
    Intake intake;
    Limelight3A limelight;
    VoltageSensor voltageSensor;


    double power;

    @Override
    public void init() {
        shooter = new Shooter(hardwareMap);
        power = 0;
        intake = new Intake(hardwareMap);
        intake.startAll(Intake.Direction.FORWARD);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();


        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);


        limelight.start();

    }

    @Override
    public void loop() {
        shooter.setPower(power);

        if (gamepad1.dpadUpWasPressed()) {
            power += 0.1;
        }

        if (gamepad1.dpadRightWasPressed()) {
            power += 0.01;
        }

        if (gamepad1.dpadDownWasPressed()) {
            power -= 0.1;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            power -= 0.01;
        }


        LLResult llResult = limelight.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            telemetry.addData("TA ", llResult.getTa());
            telemetry.addData("Shooter speed", shooter.getPower());
            telemetry.addData("voltage", voltageSensor.getVoltage());
            telemetry.update();
        }



    }

    public double powerByDistance(double ta) {
        return shooter.calculatePowerWithDistance(ta, voltageSensor.getVoltage());
    }


    @Override
    public void stop() {
        limelight.stop();
    }


}
