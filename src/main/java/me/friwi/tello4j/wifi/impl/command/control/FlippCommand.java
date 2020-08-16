package me.friwi.tello4j.wifi.impl.command.control;

import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.wifi.model.command.ControlCommand;

public class FlippCommand extends ControlCommand {
    private FlipDirection direction;

    public FlippCommand(FlipDirection direction) {
        super("flipp " + direction.getCommand());
        this.direction = direction;
    }
}