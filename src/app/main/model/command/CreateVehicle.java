package app.main.model.command;

import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;

import java.util.Optional;

public class CreateVehicle implements Command {
    private Position position;
    private Direction direction;
    private static final String CREATE = "create";

    public CreateVehicle(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public CreateVehicle() {}

    @Override
    public Optional<Command> commandFromString(String input) {
        if (input.chars().filter(ch -> ch ==' ').count() == 3 ) {
            String[] arr = input.split(" ", 2);
            String firstWord = arr[0];
            String theRest = arr[1];

            if (CREATE.equals(firstWord)) {
                String[] args = theRest.split(" ", 2);
                int x = Integer.parseInt(args[0]);
                args = args[1].split(" ", 2);
                int y = Integer.parseInt(args[0]);
                this.position = new Position(x, y);
                this.direction = Direction.fromValue(args[1]);
                return Optional.of(this);
            }
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

//TODO impl, check if groups !null,
/*
    Pattern pattern = Pattern.compile("create (?<posx>[0-9]) (?<posy>[0-9]) (?<dir>NORTH|EAST|NONE|SOUTH|WEST)");
    Matcher matcher = pattern.matcher("create 0 9 NORTH");
    int posx = Integer.parseInt(matcher.group(POSX));
    int posy = Integer.parseInt(matcher.group(POSY));
    String dir = matcher.group(DIR);
    private static final String POSX = "posx";
    private static final String POSY = "posy";
    private static final String DIR = "dir";

*/
