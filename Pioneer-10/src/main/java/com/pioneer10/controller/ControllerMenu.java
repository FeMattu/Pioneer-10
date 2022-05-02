package com.pioneer10.controller;

import com.pioneer10.view.GioveScene;
import com.pioneer10.view.EarthScene;
import javafx.stage.Stage;

public class ControllerMenu {
    private Stage stage;

    public ControllerMenu(Stage stage){
        this.stage = stage;
    }

    public void changeSceneOnEarthClick(){
        stage.setScene(new EarthScene().getScene());
    }

    public void changSceneOnGioveClick() {
        stage.setScene(new GioveScene().getScene());
    }
}

