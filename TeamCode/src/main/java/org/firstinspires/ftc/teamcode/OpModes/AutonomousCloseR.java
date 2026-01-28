package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

@Autonomous
public class AutonomousCloseR extends OpMode {
    Drive drive;
    Intake intake;
    Shooter shooter;
    @Override
    public void init() {
        drive = new Drive(hardwareMap, 0);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }
    @Override
    public void start() {
        shooter.setPower(0.7);
        sleep(7000);
        drive.drive(0, -0.3, 0);
        sleep(2500);
        drive.drive(0, 0, 0);
        intake.shootVolley();
        sleep(5000);
        drive.drive(0.7, 0, 0);
        sleep(1000);
        shooter.setPower(0);
    }
    @Override
    public void loop() {

    }
}
