package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Shooter;
@Autonomous
@Disabled

public class

AutonomousFarByDistance3 extends BTJAuto {
    public void runPath() {
        shooter.setVelocity(Shooter.SPEED_FROM_MID);
        sleep(5000);
        sleep(1000);
        intake.shootVolleyMid();
        sleep(2000);
        drive.drive(0, 0.4, 0);
        while (pinpoint.getPosX(DistanceUnit.CM) < 25) {
            pinpoint.update();
        }
       drive.drive(0,0,0);
    }
}



