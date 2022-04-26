package com.pioneer10.controller;

import com.pioneer10.view.EarthScene;
import javafx.stage.Stage;

public class ControllerMondo {
    private Stage stage;

    public ControllerMondo(Stage stage){
        this.stage = stage;
    }

    public void changeSceneOnEarthClick(){
        stage.setScene(new EarthScene().getScene());
    }


}
