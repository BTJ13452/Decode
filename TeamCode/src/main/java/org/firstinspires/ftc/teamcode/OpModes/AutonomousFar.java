package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

@Autonomous
public class AutonomousFar extends OpMode {
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
        shooter.setPower(0.8);
        sleep(10000);
        intake.shootVolley();
        sleep(5000);
        drive.drive(0, 0.2, 0);
        sleep(2000);
        shooter.setPower(0);
        drive.drive(0, 0, 0);



    }

    @Override
    public void loop() {

    }
}
