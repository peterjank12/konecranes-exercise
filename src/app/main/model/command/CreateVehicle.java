package app.main.model.command;

import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateVehicle implements Command {
    private Position position;
    private Direction direction;
    private static final String CREATE = "create";
    private static final String POSX = "posx";
    private static final String POSY = "posy";
    private static final String DIR = "dir";
    private static Pattern pattern;

    public CreateVehicle(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        pattern = Pattern.compile("changedir (?<id>[0-9]) (?<dir>NORTH|EAST|NONE|SOUTH|WEST)");
    }

    @Override
    public Optional<Command> commandFromString(String input) {
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) {
            int posx = Integer.parseInt(matcher.group(POSX));
            int posy = Integer.parseInt(matcher.group(POSY));
            String dir = matcher.group(DIR);
            position = new Position(posx, posy);
            direction = Direction.fromValue(dir);
            return Optional.of(this);
        }
        return Optional.empty();
    }

    public  Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}


