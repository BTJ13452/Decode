package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
@Disabled
public class TestShooter extends OpMode {


    Shooter shooter;
    Intake intake;

    double power;

    @Override
    public void init() {
        shooter = new Shooter(hardwareMap);
        power = 0;
        intake = new Intake(hardwareMap);
        intake.startAll(Intake.Direction.FORWARD);

    }

    @Override
    public void loop() {

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


        shooter.setPower(power);
        telemetry.addData("Shooter speed", shooter.getPower());
        telemetry.update();
    }
}
