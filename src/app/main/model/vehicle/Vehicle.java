package app.main.model.vehicle;


import java.util.concurrent.atomic.AtomicInteger;

// Models a Vehicle
public class Vehicle {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private final int id;
    private boolean inMovement = true;
    private Direction direction;
    private Position position;

    public Vehicle( Position position, Direction direction){
        this.id = NEXT_ID.getAndIncrement();
        this.position = position;
        this.direction = direction;
    }

    // Moves the vehicle in the set direction, signals border contact
    public void Move(int xConstraint, int yConstraint) {
        int newXPos = position.getX() + direction.getXMovement();
        int newYPos = position.getY() + direction.getYMovement();
        inMovement = true;
        int xCons = xConstraint/2;
        int yCons = yConstraint/2;

        if (newXPos < (xCons - 17.5) && newXPos > (-1 * xCons + 17.5)) {
            position.setX(newXPos);
        }
        else {
            inMovement = false;
        }
        if (newYPos < (yCons - 17.5) && newYPos > (-1 * yCons + 17.5)) {
            position.setY(newYPos);
        }
        else {
            inMovement = false;
        }
    }

    @Override
    public String toString() {
        return  "ID: " + id +
                " | Position: "  + position.toString() +
                ", Direction: " + direction.toString();
    }


    public Position getPosition() {
        return position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public boolean isInMovement() {
        return inMovement;
    }
}
