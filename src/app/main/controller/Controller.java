package app.main.controller;

import app.main.model.command.*;
import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;
import app.main.model.vehicle.Vehicle;
import app.main.view.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<Vehicle> vehicles;
    private final Thread simulationThread;
    private final GUI gui;

    public Controller(){
        vehicles = initializeVehicles();
        gui = new GUI(this);
        simulationThread = new Thread(new SimulationHandler(this));
        simulationThread.start();
    }

    public void processInput(Command command) {

        if(command instanceof CreateVehicle) {
            createVehicle((CreateVehicle) command);
            this.updateView();
            return;
        }
        if(command instanceof ChangeDirection) {
            changeDirection((ChangeDirection) command);
            this.updateView();
            return;
        }
        if(command instanceof ExitApplication) {
            exitApplication();
        }
    }

    private void exitApplication() {
        System.out.println("Exited app!");
        simulationThread.interrupt();
    }

    private void changeDirection(ChangeDirection command) {
        int id =  command.getId();
        Direction direction = command.getDirection();
        if(this.setVehicleDirection(id, direction))
            System.out.println("Direction changed!");
        else
            System.out.println("Couldn't change vehicle direction!");
    }

    private void createVehicle(CreateVehicle command) {
        Position position = command.getPosition();
        Direction direction = command.getDirection();
        this.addVehicle(position,direction);
        System.out.println("Created Vehicle!");
    }


    public void tick() {
        this.updateModel();
        this.updateView();
    }

    private void updateModel() {
        vehicles.forEach(vehicle -> vehicle.Move(gui.getGridXConstraint(), gui.getGridYConstraint()));
    }
    private void updateView() {
        gui.updateDisplay();
    }

    private void addVehicle(Position position, Direction direction) {
        Vehicle vehicle = new Vehicle( position, direction);
        vehicles.add(vehicle);
    }

    private boolean setVehicleDirection(int id, Direction direction) {
        Optional<Vehicle> optional = vehicles.stream().filter(vehicle -> vehicle.getId() == id).findAny();
        if(optional.isPresent()) {
            Vehicle vehicle = optional.get();
            vehicle.setDirection(direction);
            return true;
        }
        return false;
    }
    private List<Vehicle> initializeVehicles() {
        vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Vehicle(new Position(0,0), Direction.NORTH));
        vehicles.add(new Vehicle(new Position(0,0), Direction.EAST));
        vehicles.add(new Vehicle(new Position(0,0), Direction.SOUTH));
        vehicles.add(new Vehicle(new Position(0,0), Direction.WEST));

        return vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
