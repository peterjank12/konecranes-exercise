package app.main.controller;

import app.main.model.command.*;

import java.util.List;
import java.util.Optional;

public class InputInterpreter {

    private final List<Command> commands;

    public InputInterpreter() {
        commands = List.of(new ChangeDirection(),new CreateVehicle(),new ExitApplication());
    }

    public Optional<Command> createCommand(String input) {

        Optional<Command> optionalCommand = commands.stream()
                .map(o -> o.commandFromString(input))
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());

        return optionalCommand;
    }

}
