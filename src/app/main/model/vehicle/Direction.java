package app.main.model.vehicle;

import java.util.Locale;

// models Vehicle direction as EAST, NORTH, WEST, SOUTH, NONE
public enum Direction {

    EAST(1, 0),
    NORTH(0, 1),
    WEST(-1, 0),
    SOUTH(0, -1),

    NONE(0, 0);

    private final int xMovement;
    private final int yMovement;
    private double scale;

    Direction(int xMovement, int yMovement) {
        this.xMovement = xMovement;
        this.yMovement = yMovement;
        scale = 1.00;
    }

    // returns enum instance based on name
    public static Direction fromValue(String value) {
        for (Direction b : Direction.values()) {
            if (b.name().equals(value.toUpperCase(Locale.getDefault()))) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public int getXMovement() {
        return xMovement;
    }

    public int getYMovement() {
        return yMovement;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}

