package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

@Autonomous
public class BTJAuto extends LinearOpMode {

    Drive drive;
    Shooter shooter;
    Intake intake;
    VoltageSensor voltageSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap, 0);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");


        waitForStart();

        shooter.autoSpeed(Shooter.Distance.FAR, voltageSensor.getVoltage());
        sleep(1000);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);
        sleep(7000);
        intake.transportArtifactToShooter(Intake.Cell.LEFT);
        sleep(7000);
        intake.transportArtifactToShooter(Intake.Cell.RIGHT);

        drive.drive(0, 0.5, 0);
        sleep(500);
        drive.drive(0, 0, 0);
    }
}
