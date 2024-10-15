package app.main.model.command;

import java.util.Optional;

public interface Command {
    Optional<Command> commandFromString(String input);

}
