package app.main.view;

import app.main.controller.Controller;



public class View {
    public View(){
    }

    public void update(Controller controller) {
        controller.getVehicles().forEach(vh -> System.out.println(vh.toString()));
    }



}
