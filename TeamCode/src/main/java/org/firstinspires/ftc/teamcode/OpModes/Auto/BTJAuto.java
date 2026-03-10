package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Systems.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake;
import org.firstinspires.ftc.teamcode.Systems.Shooter;

public abstract class BTJAuto extends OpMode {

    Drive drive;
    GoBildaPinpointDriver pinpoint;
    Intake intake;
    Shooter shooter;
    Thread path;
    VoltageSensor voltageSensor;


    @Override
    public void init() {
        drive = new Drive(hardwareMap, 0, false);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");
        voltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");
        pinpoint.resetPosAndIMU();
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        path = new Thread(new Runnable() {
            @Override
            public void run() {
                runPath();
            }
        });
    }

    @Override
    public void loop() {
        telemetry.addData("x =", pinpoint.getPosX(DistanceUnit.CM));
        telemetry.addData("y =", pinpoint.getPosY(DistanceUnit.CM));
        telemetry.update();
        pinpoint.update();
    }


    @Override
    public void start() {
        path.start();
    }

    @Override
    public void stop() {
        path.interrupt();
    }
    public abstract void runPath();
}
