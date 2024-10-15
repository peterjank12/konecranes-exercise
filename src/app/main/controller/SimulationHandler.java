package app.main.controller;

import java.util.concurrent.Callable;

public class SimulationHandler implements Runnable {

    private final Controller controller;
    private boolean stopped;

    public SimulationHandler(Controller controller) {
        this.controller = controller;
        stopped = false;
    }

    @Override
    public void run() {
        while(!stopped) {
            controller.tick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                stopped = true;
            }
        }
    }

}