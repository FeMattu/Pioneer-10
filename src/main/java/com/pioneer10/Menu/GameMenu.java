package com.pioneer10.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class GameMenu extends FXGLMenu {
    public GameMenu() {
        super(MenuType.GAME_MENU);

        var btnPause = new Button("Resume");
        btnPause.setOnAction(e -> fireResume());

        var btnExit = new Button("Main Menu");
        btnExit.setOnAction(e -> fireExitToMainMenu());

        var box = new VBox(btnPause, btnExit);
        box.setAlignment(Pos.TOP_CENTER);
        box.setTranslateX(getAppWidth() / 2.0 - 100);
        box.setTranslateY(getAppHeight() / 2.0 - 100);

        getContentRoot().getChildren().addAll(box);
    }
}
