package app.main;

import app.main.controller.Controller;
import app.main.view.SwingView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            controller.addView(new SwingView(controller));
        });
    }
}

