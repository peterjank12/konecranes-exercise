package app.main.view;

import app.main.model.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Panel that shows vehicles graphically
public class GridPanel extends JPanel {
    private final List<Vehicle> vehicles;

    public GridPanel(List<Vehicle> vehicles){
        this.vehicles = vehicles;
        setBackground(Color.DARK_GRAY);
        //setBorder(new LineBorder(Color.lightGray,5));
    }

    // shows the panel graphically
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        vehicles.stream().filter(Vehicle::isInMovement).forEach(v -> paintVehicle(g, v, Color.black));
        vehicles.stream().filter(Vehicle -> !Vehicle.isInMovement()).forEach(v -> paintVehicle(g, v, Color.red));
    }

    // paints each vehicle on the panel
    private void paintVehicle(Graphics g, Vehicle v, Color color) {
        g.setColor(color);
        g.fillOval(v.getPosition().getX() + getWidth()/2, v.getPosition().getY()*-1 + getHeight()/2,15,15);
    }

}
