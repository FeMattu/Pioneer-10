package com.pioneer10.controller;

import com.pioneer10.view.GioveScene;
import com.pioneer10.view.EarthScene;
import com.pioneer10.view.MarteScene;
import com.pioneer10.view.MenuScene;
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

    public void changSceneOnMarteClick() {
        stage.setScene(new MarteScene().getScene());
    }

    public void returnToMenuOnDied() {stage.setScene(new MenuScene(stage, 1280, 720));
    }
}

