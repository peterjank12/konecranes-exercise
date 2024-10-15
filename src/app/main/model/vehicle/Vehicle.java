package app.main.model.vehicle;

import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Vehicle {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    private final int id;
    private boolean inMovement = true;
    private Direction direction;
    private Position position;

    public Vehicle( Position position, Direction direction){
        this.id = NEXT_ID.getAndIncrement();
        this.position = position;
        this.direction = direction;
    }

    public void Move(int xConstraint, int yConstraint) {
        int newXPos = position.getX() + direction.getXMovement();
        int newYPos = position.getY() + direction.getYMovement();
        inMovement = true;
        int xCons = xConstraint/2;
        int yCons = yConstraint/2;

        if (newXPos < (xCons - 20) && newXPos > (-1 * xCons + 5)) {
            position.setX(newXPos);
        }
        else {
            inMovement = false;
        }
        if (newYPos < (yCons - 5) && newYPos > (-1 * yCons + 20)) {
            position.setY(newYPos);
        }
        else {
            inMovement = false;
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id = " + Integer.toString(id) +
                ", direction = " + direction.toString() +
                ", position = "  + position.toString() +
                '}';
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition() {
        return position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public boolean isInMovement() {
        return inMovement;
    }
}
