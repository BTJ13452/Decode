package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

@TeleOp
@Disabled
public class Pinpoint extends OpMode {

    GoBildaPinpointDriver PinpointBTJ;

    @Override
    public void init() {
        PinpointBTJ = hardwareMap.get(GoBildaPinpointDriver.class,"Pinpoint");
        PinpointBTJ.setOffsets(166  ,37,DistanceUnit.CM);
        PinpointBTJ.setOffsets(98 ,37,DistanceUnit.CM);
        PinpointBTJ.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        PinpointBTJ.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,GoBildaPinpointDriver.EncoderDirection.FORWARD);
        PinpointBTJ.resetPosAndIMU();
    }

    @Override
    public void loop() {
        PinpointBTJ.update();
        telemetry.addData("PlaceX",PinpointBTJ.getPosX(DistanceUnit.INCH));

        telemetry.addData("PlaceY",PinpointBTJ.getPosY(DistanceUnit.INCH));
        telemetry.update();
    }
}