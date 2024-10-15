package app.main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputHandler implements Runnable{

    private final Controller controller;
    private boolean stopped;


    public InputHandler(Controller controller) {
        stopped = false;
        this.controller = controller;
    }


    private String readInput() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    @Override
    public void run() {
        while(!stopped && !Thread.currentThread().isInterrupted()) {
            String inputLine = readInput();
            controller.processInput(inputLine);
        }
    }
}