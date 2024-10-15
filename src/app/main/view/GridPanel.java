package app.main.view;

import app.main.model.vehicle.Vehicle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class GridPanel extends JPanel {
    private List<Vehicle> vehicles;

    public GridPanel(List<Vehicle> vehicles){
        this.vehicles = vehicles;
        setBackground(Color.DARK_GRAY);
        setBorder(new LineBorder(Color.lightGray,5));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        vehicles.stream().filter(Vehicle::isInMovement).forEach(v -> paintVehicle(g, v, Color.black));
        vehicles.stream().filter(Vehicle -> !Vehicle.isInMovement()).forEach(v -> paintVehicle(g, v, Color.red));
    }

    private void paintVehicle(Graphics g, Vehicle v, Color color) {
        g.setColor(color);
        g.fillOval(v.getPosition().getX() + getWidth()/2, v.getPosition().getY()*-1 + getHeight()/2,15,15);
    }

}
