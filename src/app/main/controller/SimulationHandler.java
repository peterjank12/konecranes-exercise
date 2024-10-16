package app.main.controller;

// handles the simulation of time for the movement of vehicles and view updates
public class SimulationHandler implements Runnable {

    private final Controller controller;
    private boolean stopped;

    public SimulationHandler(Controller controller) {
        this.controller = controller;
        stopped = false;
    }

    // signals the controller every x milliseconds
    @Override
    public void run() {
        while(!stopped) {
            controller.tick();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                stopped = true;
            }
        }
    }

}