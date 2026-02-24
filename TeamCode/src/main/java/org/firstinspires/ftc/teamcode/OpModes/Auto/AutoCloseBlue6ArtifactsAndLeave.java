package org.firstinspires.ftc.teamcode.OpModes.Auto;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

@Autonomous
@Disabled

public class AutoCloseBlue6ArtifactsAndLeave extends OpMode {
    Drive drive;
    Intake intake;
    Shooter shooter;
    @Override
    public void init() {
        drive = new Drive(hardwareMap, 0, false);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }
    @Override
    public void start() {
        shooter.setPower(0.65);
        sleep(6000);
        drive.drive(0, -0.3, 0);
        sleep(2000);
        drive.drive(0, 0, 0);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        sleep(1000);
        intake.transportArtifactToShooter(Intake.Cell.LEFT);
        sleep(1000);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        sleep(2000);
        drive.drive(0, -0.3, 0);
        sleep(500);
        drive.drive(0, 0, 0);
        sleep(500);
        drive.drive(0, 0, -1);
        sleep(170);
        drive.drive(0, 0, 0);
        sleep(500);
        intake.firstStageIntake(Intake.Direction.FORWARD);
        intake.secondStageTransport(Intake.Direction.REVERSE);
        intake.thirdStageTransport(Intake.Direction.REVERSE);
        drive.drive(0, 0.3, 0);
        sleep(2700);
        drive.drive(0, -0.3, 0);
        sleep(2000);
        drive.drive(0, 0, 0);
        sleep(500);
        drive.drive(0, 0, 1);
        sleep(190);
        drive.drive(0, 0, 0);
        sleep(500);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        sleep(1000);
        intake.transportArtifactToShooter(Intake.Cell.LEFT);
        sleep(1000);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        sleep(1000);
        drive.drive(-0.3, 0, 0);
        shooter.setPower(0);
        drive.drive(0, 0, 0);
    }
    @Override
    public void loop() {

    }
}
