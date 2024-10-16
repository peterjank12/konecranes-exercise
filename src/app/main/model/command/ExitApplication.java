package app.main.model.command;


import java.util.Optional;

// models application exit command
public class ExitApplication implements Command {
    private static final String EXIT = "exit";

    @Override
    public Optional<Command> commandFromString(String input) {

        if (EXIT.equals(input)) {
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
