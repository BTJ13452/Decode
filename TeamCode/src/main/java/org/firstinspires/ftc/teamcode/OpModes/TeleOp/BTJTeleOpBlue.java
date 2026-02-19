package org.firstinspires.ftc.teamcode.OpModes.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.AutoAline;

@TeleOp
public class BTJTeleOpBlue extends BTJTeleOp {

    @Override
    public void init() {
        super.init();
        autoAline = new AutoAline(hardwareMap, AutoAline.AllianceColor.BLUE);
    }
}
