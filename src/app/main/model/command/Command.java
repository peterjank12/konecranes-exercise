package app.main.model.command;

import java.util.Optional;

public interface Command {
    public Optional<Command> commandFromString(String input);

}
