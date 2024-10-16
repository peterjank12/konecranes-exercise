package app.main.model.command;


import app.main.model.vehicle.Direction;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// models the direction change command
public class ChangeDirection implements Command {
    public static final String CHANGEDIR = "changedir";
    private int id;
    private Direction direction;
    private static Pattern pattern;
    private static final String ID = "id";
    private static final String DIR = "dir";


    public ChangeDirection(int id, Direction direction) {
        this.id = id;
        this.direction = direction;
        //pattern = Pattern.compile("changedir (?<id>[0-9]) (?<dir>NORTH|EAST|NONE|SOUTH|WEST)");
    }

    @Override
    public Optional<Command> commandFromString(String input) {

        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            int id = Integer.parseInt(matcher.group(ID));
            String dir = matcher.group(DIR);
            this.id = id;
            direction = Direction.fromValue(dir);
            return Optional.of(this);
        }

        return Optional.empty();

    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

}
