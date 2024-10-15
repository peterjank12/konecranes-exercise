package app.main.controller;

import app.main.model.command.*;
import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;
import app.main.model.vehicle.Vehicle;
import app.main.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<Vehicle> vehicles;
    private final Thread inputThread;
    private final Thread simulationThread;
    private final InputInterpreter inputInterpreter;
    private final View consoleView;

    public Controller(){
        vehicles = initializeVehicles();
        consoleView = new View();
        inputInterpreter = new InputInterpreter();
        inputThread = new Thread(new InputHandler(this));
        simulationThread = new Thread(new SimulationHandler(this));
        inputThread.start();
        simulationThread.start();

    }

    public void processInput(String input) {

        Optional<Command> optionalCommand = inputInterpreter.createCommand(input);

        if (optionalCommand.isEmpty()) {
            System.out.println("Wrong command!");
            return;
        }
        Command command = optionalCommand.get();

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
        inputThread.interrupt();
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
       // this.updateView();
    }

    private void updateModel() {
        vehicles.forEach(Vehicle::Move);
    }
    private void updateView() {
        consoleView.update(this);
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
        vehicles.add(new Vehicle(new Position(3,-2), Direction.NORTH));
        vehicles.add(new Vehicle(new Position(-1,0), Direction.EAST));
        vehicles.add(new Vehicle(new Position(-4,6), Direction.SOUTH));
        return vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
