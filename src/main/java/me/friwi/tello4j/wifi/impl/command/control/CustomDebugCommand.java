package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class CustomDebugCommand extends ControlCommand {

    public CustomDebugCommand() {
        super("takeofffffff 30 10"); //for drone debugging
    }
}