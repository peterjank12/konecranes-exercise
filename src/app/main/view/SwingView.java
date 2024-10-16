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


// Graphic user interface. Shows vehicles graphically and descriptively.
public class SwingView extends JFrame {
    private final Controller controller;
    private final JTextArea displayArea;
    private final GridPanel gridPanel;

    public SwingView(Controller controller) {
        this.controller = controller;

        // frame setup
        setTitle("Vehicle Controller");
        setSize(512, 512);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // create command buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JButton createVehicleButton = new JButton("Create Vehicle");
        JButton changeDirectionButton = new JButton("Change Direction");
        JButton exitButton = new JButton("Exit Application");

        // button action listeners
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

        // add buttons to the panel
        buttonPanel.add(createVehicleButton);
        buttonPanel.add(changeDirectionButton);
        buttonPanel.add(exitButton);

        // vehicle text display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // create encompassing panel with GridBagLayout
        JPanel encompassingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // configure constraints for the button panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        encompassingPanel.add(buttonPanel, gbc);

        // Configure constraints for the scroll pane
        gbc.gridx = 1;
        gbc.weightx = 3;
        encompassingPanel.add(scrollPane, gbc);

        this.add(encompassingPanel, BorderLayout.NORTH);

        // graphic display canvas
        gridPanel = new GridPanel(controller.getVehicles());
        this.add(gridPanel,BorderLayout.CENTER);

        updateDisplay();
        //setVisible(true);
    }

    private void handleCreateVehicle() {
        // get user input for vehicle position and direction
        String xStr = JOptionPane.showInputDialog(this, "Enter x position (integer):");
        String yStr = JOptionPane.showInputDialog(this, "Enter y position (integer):");
        String directionStr = JOptionPane.showInputDialog(this, "Enter direction (NORTH, EAST, SOUTH, WEST, NONE):");

        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);

            // move vehicle position back in grid
            if (Math.abs(x) > (gridPanel.getWidth()/2 - 5)) {
                x = 0;
            }
            if (Math.abs(y) > (gridPanel.getHeight()/2 - 5)) {
                y = 0;
            }

            Position position = new Position(x, y);
            Direction direction = Direction.valueOf(directionStr.toUpperCase(Locale.ROOT));

            // create a createVehicle command and send to Controller for porcessing
            CreateVehicle command = new CreateVehicle(position, direction);
            controller.processInput(command);

            updateDisplay();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid position. Enter valid integers for x and y. ");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid direction. Please enter one of: NORTH, EAST, SOUTH, WEST, NONE.");
        }
    }

    private void handleChangeDirection() {
        // get user input for vehicle ID and new direction
        String idStr = JOptionPane.showInputDialog(this, "Enter vehicle ID (integer):");
        String directionStr = JOptionPane.showInputDialog(this, "Enter new direction (NORTH, EAST, SOUTH, WEST, NONE):");

        try {
            int id = Integer.parseInt(idStr);
            Direction direction = Direction.valueOf(directionStr.toUpperCase());

            // create a ChangeDirection command and send to controller for processing
            ChangeDirection command = new ChangeDirection(id, direction);
            controller.processInput(command);

            updateDisplay();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter valid integer for ID.");
        }catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid direction. Please enter one of: NORTH, EAST, SOUTH, WEST, NONE.");
        }
    }

    // registers exit command
    private void handleExit() {
        // create an ExitApplication command and process it
        ExitApplication command = new ExitApplication();
        controller.processInput(command);
    }

    // updates the view
    public void updateDisplay() {
        List<Vehicle> vehicles = controller.getVehicles();

        // display each vehicle's details on the vehicle display area
        StringBuilder sb = new StringBuilder();
        vehicles.forEach(vehicle -> sb.append(vehicle.toString()).append("\n"));
        SwingUtilities.invokeLater(() -> displayArea.setText(sb.toString()));

        // repaint canvas showing vehicles
        gridPanel.repaint();
    }

    // gets the canvas areas horizontal dimension in pixels
    public int getGridXConstraint() {
        return gridPanel.getWidth();
    }

    // gets the canvas areas vertical dimension
    public int getGridYConstraint() {
        return gridPanel.getHeight();
    }

}
