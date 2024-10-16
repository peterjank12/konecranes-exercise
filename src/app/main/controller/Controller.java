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
        view.dispose();
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
        if (view != null) {
            this.updateModel();
            this.updateView();
        }
    }

    private void updateModel() {
        vehicles.forEach(vehicle -> vehicle.Move(view.getGridXConstraint(), view.getGridYConstraint()));
    }
    private void updateView() {
        view.updateDisplay();
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

    public void addView(SwingView view){
        this.view = view;
        view.setVisible(true);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
