package com.pioneer10.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.ui.FXGLScrollPane;
import com.pioneer10.PioneerApp;
import com.pioneer10.data.LevelData;
import com.pioneer10.model.Planet;
import com.pioneer10.model.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;
import static javax.swing.text.StyleConstants.setBackground;

public class MainMenu extends FXGLMenu {

    private ObjectProperty<MenuButton> selectedButton;
    private Pane contentMenu = new Pane();
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        //background
        getContentRoot().getChildren().add(new ImageView(new Image(
                Utils.getPathFileFromResources("assets/space2.jpg")
        )));

        //title
        var title = getUIFactoryService().newText("Pioneer-10", Color.WHITE, 60);
        title.setStrokeWidth(2);
        title.setStroke(Color.GRAY);
        title.setTranslateX(100);
        title.setTranslateY(100);

        //bottoni
        MenuButton playGame = new MenuButton("Levels", "Select level", () -> LevelScene());
        MenuButton options = new MenuButton("Options", "Adjust in-game option", () -> OptionsScene());
        MenuButton credits = new MenuButton("Credits", "How create game", () -> CreditScene());
        MenuButton exits = new MenuButton("Exit", "Close game", () -> fireExit());

        selectedButton = new SimpleObjectProperty<>(playGame);

        var textDescription = getUIFactoryService().newText("", Color.GRAY, 20);
        textDescription.textProperty().bind(
                Bindings.createStringBinding(() -> selectedButton.get().getDescription(), selectedButton)
        );

        //menu button
        var menuBox = new VBox(12,
                playGame,
                options,
                credits,
                exits,
                new Text(""),
                new LineSeparator(),
                textDescription
        );
        menuBox.setAlignment(Pos.CENTER_LEFT);

        menuBox.setTranslateY(430);
        menuBox.setTranslateX(100);

        //tasto di back
        var view = new KeyView(KeyCode.ESCAPE, Color.GREEN, 14.0);
        view.setOnMouseClicked(e -> {
            contentMenu.getChildren().setAll(menuBox);
        });
        var hBox = new HBox(25,
                getUIFactoryService().newText("Back", 14),
                view
        );
        hBox.setTranslateX(getAppWidth() - 200);
        hBox.setTranslateY(430 + 7 * 28);
        hBox.setAlignment(Pos.BOTTOM_CENTER);

        getRoot().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                contentMenu.getChildren().setAll(menuBox);
            }
        });

        contentMenu.getChildren().setAll(menuBox);

        getContentRoot().getChildren().addAll(
                title,
                contentMenu,
                hBox
        );
    }

    private Pane worldPreview = new Pane();//TODO: create world preview
    public void LevelScene(){

        //Tutti i livelli, l'ordine in cui sono messi, sarà l'ordine di visualizzazione
        List<String> levelsData = List.of(
                "Terra.json",
                "Giove.json",
                "Nettuno.json",
                "Nettuno.json",
                "Nettuno.json",
                "Nettuno.json"
        );
        var box = new VBox(12);
        box.getChildren().add(getUIFactoryService().newText("Livelli", Color.GREEN,40));

        var levelBox = LevelSelectionBox(
                levelsData
                        .stream()
                        .map(name -> FXGL.getAssetLoader().loadJSON("levels/levelsData/" + name, LevelData.class).get())
                        .collect(Collectors.toList())
        );

        box.getChildren().add(levelBox);

        box.setTranslateX(100);
        box.setTranslateY(430);

        contentMenu.getChildren().setAll(box, worldPreview);
    }

    public VBox LevelSelectionBox(List<LevelData> levels) {
        var box = new VBox(12);

        levels.forEach(data -> {
            MenuButton item = new MenuButton(data.name(), "", ()->{
                FXGL.<PioneerApp>getAppCast().setCurrentLeve(data);
                fireNewGame();
            });

            box.getChildren().add(item);
        });

        return box;
    }

    private void CreditScene(){
        var credits = new VBox(
                getUIFactoryService().newText("Credits", Color.GREEN, 40),
                new Text(""),
                getUIFactoryService().newText("Mattucci Federico", Color.WHITE, 20),
                getUIFactoryService().newText("Arcuti Manuel", Color.WHITE, 20),
                getUIFactoryService().newText("Mario Popa", Color.WHITE, 20),
                getUIFactoryService().newText("Tarlarini Gabriele", Color.WHITE, 20),
                getUIFactoryService().newText("Augusto Alessandro", Color.WHITE, 20)
        );
        credits.setTranslateX(100);
        credits.setTranslateY(430);

        contentMenu.getChildren().setAll(credits);
    }

    private void OptionsScene(){
        var options = new VBox(
                getUIFactoryService().newText("Options", Color.GREEN, 40),
                new Text(""),
                getUIFactoryService().newText("Mattucci Federico", Color.WHITE, 20),
                getUIFactoryService().newText("Arcuti Manuel", Color.WHITE, 20),
                getUIFactoryService().newText("Mario Popa", Color.WHITE, 20),
                getUIFactoryService().newText("Tarlarini Gabriele", Color.WHITE, 20),
                getUIFactoryService().newText("Augusto Alessandro", Color.WHITE, 20)
        );
        options.setTranslateX(100);
        options.setTranslateY(430);

        contentMenu.getChildren().setAll(options);
    }

    private class MenuButton extends StackPane {

        public static final Color SELECTED_COLOR = Color.WHITE;
        public static final Color NOT_SELECTED_COLOR = Color.GRAY;

        private Rectangle selector;
        private String name, description;
        public MenuButton(String name, String description, Runnable action) {
            this.name = name;
            this.description = description;

            //selector
            selector = new Rectangle(7, 21, Color.WHITE);
            selector.setTranslateX(-23);
            selector.setTranslateY(-2);
            selector.visibleProperty().bind(focusedProperty());


            //text
            var text = getUIFactoryService().newText(name, Color.WHITE,20.0);

            text.fillProperty().bind(
                    Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR)
            );
            text.strokeProperty().bind(
                    Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR)
            );

            text.setStrokeWidth(1.0);

            focusedProperty().addListener((observable, oldValue, isSelected) ->{
                if(isSelected){
                    selectedButton.setValue(this);
                }
            });


            setAlignment(Pos.CENTER_LEFT);
            setFocusTraversable(true);

            setOnKeyPressed( e -> {
                if(e.getCode() == KeyCode.ENTER){
                    action.run();
                }
            });

            getChildren().addAll(selector,text);
        }

        public String getDescription() {
            return description;
        }
    }

    private static class LineSeparator extends Parent{
        private Rectangle line = new Rectangle(500, 3);
        public LineSeparator(){
            var gradient = new LinearGradient(
                    0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.WHITE),
                    new Stop(0.5, Color.GRAY),
                    new Stop(1, Color.TRANSPARENT)
            );

            line.setFill(gradient);
            getChildren().add(line);
        }
    }

}
