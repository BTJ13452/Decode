package org.firstinspires.ftc.teamcode.OpModes;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Shooter;


@TeleOp
@Disabled
public class TestShooter extends OpMode{

    final double SHOOTER_DIFFERENTIAL_SPEED = 0.05;

    Shooter shooter;

    @Override
    public void init() {
        shooter = new Shooter(hardwareMap);
    }

    @Override
    public void loop() {

        if(gamepad1.dpadDownWasPressed()){
            shooter.setMotorSpeed(0.75);
        } else if (gamepad1.dpadUpWasPressed()) {
            shooter.setMotorSpeed(0.85);
        }

        if(gamepad1.dpadRightWasPressed()){
            shooter.setMotorSpeed(shooter.getMotorSpeed()+SHOOTER_DIFFERENTIAL_SPEED);
        } else if (gamepad1.dpadLeftWasPressed()) {
            shooter.setMotorSpeed(shooter.getMotorSpeed()-SHOOTER_DIFFERENTIAL_SPEED
            );
        }
        if (gamepad1.rightStickButtonWasPressed()){
            shooter.setMotorSpeed(0);
        }

        telemetry.addData("Shooter speed", shooter.getMotorSpeed());
        telemetry.update();

    }
}
