package app.main.controller;

import app.main.model.command.*;
import app.main.model.vehicle.Direction;
import app.main.model.vehicle.Position;
import app.main.model.vehicle.Vehicle;
import app.main.view.SwingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Controls the flow of the application. Handles user inputs and updates the model accordingly. Keeps the view up to date with the model.
public class Controller {

    private List<Vehicle> vehicles;
    private final Thread simulationThread;
    private SwingView view = null;

    public Controller(){
        vehicles = initializeVehicles();
        simulationThread = new Thread(new SimulationHandler(this));
        simulationThread.start();
    }

    // processes the user input in form of commands
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

    // shuts down running thread and exits the application
    private void exitApplication() {
        System.out.println("Exiting app!");
        simulationThread.interrupt();
        view.dispose();
    }


    // changes direction of vehicle based on id
    private void changeDirection(ChangeDirection command) {
        int id =  command.getId();
        Direction direction = command.getDirection();
        if(this.setVehicleDirection(id, direction))
            System.out.println("Direction changed!");
        else
            System.out.println("Couldn't change vehicle direction!");
    }

    // creates new vehicle on given position with given direction
    private void createVehicle(CreateVehicle command) {
        Position position = command.getPosition();
        Direction direction = command.getDirection();
        this.addVehicle(position,direction);
        System.out.println("Created Vehicle!");
    }

    // simulates time, calls update on model and view
    public void tick() {
        if (view != null) {
            this.updateModel();
            this.updateView();
        }
    }

    // prompts model to update, passes canvas constraints
    private void updateModel() {
        vehicles.forEach(vehicle -> vehicle.Move(view.getGridXConstraint(), view.getGridYConstraint()));
    }

    // updates view
    private void updateView() {
        view.updateDisplay();
    }

    // creates and adds new vehicle to vehicles
    private void addVehicle(Position position, Direction direction) {
        Vehicle vehicle = new Vehicle( position, direction);
        vehicles.add(vehicle);
    }

    // sets the vehicles direction
    private boolean setVehicleDirection(int id, Direction direction) {
        Optional<Vehicle> optional = vehicles.stream().filter(vehicle -> vehicle.getId() == id).findAny();
        if(optional.isPresent()) {
            Vehicle vehicle = optional.get();
            vehicle.setDirection(direction);
            return true;
        }
        return false;
    }

    // initializes the vehicles list
    private List<Vehicle> initializeVehicles() {
        vehicles = new ArrayList<Vehicle>();
        vehicles.add(new Vehicle(new Position(0,0), Direction.NORTH));
        vehicles.add(new Vehicle(new Position(0,0), Direction.EAST));
        vehicles.add(new Vehicle(new Position(0,0), Direction.SOUTH));
        vehicles.add(new Vehicle(new Position(0,0), Direction.WEST));

        return vehicles;
    }

    // adds a view to the controller
    public void addView(SwingView view){
        this.view = view;
        view.setVisible(true);
    }

    // gets the vehicles
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
