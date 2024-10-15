package app.main.model.command;


import app.main.model.vehicle.Direction;

import java.util.Optional;

public class ChangeDirection implements Command {
    public static final String CHANGEDIR = "changedir";
    private int id;
    private Direction direction;

   public ChangeDirection(){}

    public ChangeDirection(int id, Direction direction) {
        this.id = id;
        this.direction = direction;
    }

    @Override
    public Optional<Command> commandFromString(String input) {

        if (input.chars().filter(ch -> ch ==' ').count() == 2 ) {
            String arr[] = input.split(" ", 2);
            String firstWord = arr[0];
            String theRest = arr[1];

            if (CHANGEDIR.equals(firstWord)) {
                String args[] = theRest.split(" ", 2);
                this.id = Integer.parseInt(args[0]);
                this.direction = Direction.fromValue(args[1]);
                return Optional.of(this);
            }
        }
        return Optional.empty();

    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
