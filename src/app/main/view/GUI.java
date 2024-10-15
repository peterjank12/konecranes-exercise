package app.main.view;

import app.main.controller.Controller;
import app.main.model.command.ChangeDirection;
import app.main.model.command.CreateVehicle;
import app.main.model.command.ExitApplication;
import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;
import app.main.model.vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public class GUI extends JFrame {
    private final Controller controller;
    private final JTextArea vehicleDisplay;
    private final GridPanel gridPanel;

    public GUI(Controller controller) {
        this.controller = controller;

        // Set up the frame
        setTitle("Vehicle Controller");
        setSize(512, 512);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JButton createVehicleButton = new JButton("Create Vehicle");
        JButton changeDirectionButton = new JButton("Change Direction");
        JButton exitButton = new JButton("Exit Application");

        // Add action listeners
        createVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateVehicle();
            }
        });

        changeDirectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangeDirection();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(createVehicleButton);
        buttonPanel.add(changeDirectionButton);
        buttonPanel.add(exitButton);

        // Vehicle display area
        vehicleDisplay = new JTextArea();
        vehicleDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(vehicleDisplay);

        JPanel panel = new JPanel();
        panel.add(buttonPanel);
        panel.add(scrollPane);
        this.add(panel, BorderLayout.NORTH);

        //Vehicle display grid
        gridPanel = new GridPanel(controller.getVehicles());
        this.add(gridPanel,BorderLayout.CENTER);

        // Update the vehicle display initially
        updateDisplay();

        // Make the frame visible
        setVisible(true);
    }

    private void handleCreateVehicle() {
        // Get user input for vehicle position and direction
        String xStr = JOptionPane.showInputDialog(this, "Enter x position:");
        String yStr = JOptionPane.showInputDialog(this, "Enter y position:");
        String directionStr = JOptionPane.showInputDialog(this, "Enter direction (NORTH, EAST, SOUTH, WEST):");

        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);
            Direction direction = Direction.valueOf(directionStr.toUpperCase(Locale.ROOT));
            Position position = new Position(x, y);

            // Create a CreateVehicle command and process it
            CreateVehicle command = new CreateVehicle(position, direction);
            controller.processInput(command);

            // Update the vehicle display
            updateDisplay();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void handleChangeDirection() {
        // Get user input for vehicle ID and new direction
        String idStr = JOptionPane.showInputDialog(this, "Enter vehicle ID:");
        String directionStr = JOptionPane.showInputDialog(this, "Enter new direction (NORTH, EAST, SOUTH, WEST):");

        try {
            int id = Integer.parseInt(idStr);
            Direction direction = Direction.valueOf(directionStr.toUpperCase());

            // Create a ChangeDirection command and process it
            ChangeDirection command = new ChangeDirection(id, direction);
            controller.processInput(command);

            // Update the vehicle display
            updateDisplay();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void handleExit() {
        // Create an ExitApplication command and process it
        ExitApplication command = new ExitApplication();
        controller.processInput(command);
        this.dispose();
    }

    public void updateDisplay() {
        // Get the current list of vehicles from the controller
        List<Vehicle> vehicles = controller.getVehicles();

        // Clear the text area
        vehicleDisplay.setText("");

        // Display each vehicle's details
        for (Vehicle vehicle : vehicles) {
            vehicleDisplay.append("ID: " + vehicle.getId() +
                    ", Position: (" + vehicle.getPosition().getX() + ", " + vehicle.getPosition().getY() + ")" +
                    ", Direction: " + vehicle.getDirection() + "\n");
        }

        gridPanel.repaint();
    }

    public int getGridXConstraint() {
        return gridPanel.getWidth();
    }

    public int getGridYConstraint() {
        return gridPanel.getHeight();
    }

}
