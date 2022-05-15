package com.pioneer10.view;

import com.pioneer10.model.Utils;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class brightness extends Scene{

    public brightness(Stage stage, double width, double height) {
        super(new Group(), width, height, true, SceneAntialiasing.BALANCED);
        Group root = new Group();
        this.setRoot(root);


        //background
        Image background = new Image(Utils.getPathFileFromResources("assets/space2.jpg"));
        ImagePattern patternBackground = new ImagePattern(background);
        this.setFill(patternBackground);

        Text BRIGHTNESS = new Text("BRIGHTNESS:");
        BRIGHTNESS.setEffect(Utils.neonEffect());
        BRIGHTNESS.setFill(Color.WHITE);
        BRIGHTNESS.setStroke(Color.BLACK);

        BRIGHTNESS.setFont(Font.font("Consolas", 100));
        root.getChildren().add(BRIGHTNESS);

        BRIGHTNESS.setTranslateX(this.getWidth()/4-BRIGHTNESS.getLayoutBounds().getWidth()/2);
        BRIGHTNESS.setTranslateY(this.getHeight()/5-BRIGHTNESS.getLayoutBounds().getHeight()/2);

        Button button5 = new Button("BRIGHTNESS: 50%");
        root.getChildren().add(button5);

        Button button6 = new Button("BRIGHTNESS: 30%");
        root.getChildren().add(button6);

        Button button7 = new Button("BRIGHTNESS: 100%");
        root.getChildren().add(button7);

        button5.setTranslateX(this.getWidth()/9.8 - button5.getWidth()/2);
        button5.setTranslateY(this.getHeight()/3 - button5.getHeight()/2 + 150);
        button5.setScaleX(3);
        button5.setScaleY(2);
        button5.setEffect(Utils.neonEffect());
        button5.setTextFill(Color.WHITE);

        button6.setTranslateX(this.getWidth()/9.8 - button6.getWidth()/2);
        button6.setTranslateY(this.getHeight()/3 - button6.getHeight()/2 + 230);
        button6.setScaleX(3);
        button6.setScaleY(2);
        button6.setEffect(Utils.neonEffect());
        button6.setTextFill(Color.WHITE);

        button7.setTranslateX(this.getWidth()/9.8 - button7.getWidth()/2);
        button7.setTranslateY(this.getHeight()/3 - button7.getHeight()/2 + 70);
        button7.setScaleX(2.9);
        button7.setScaleY(2);
        button7.setEffect(Utils.neonEffect());
        button7.setTextFill(Color.WHITE);

        class BrightnessManager {
            public static void setBrightness(Object[] brightness)
                    throws IOException {
                //Creates a powerShell command that will set the brightness to the requested value (0-100), after the requested delay (in milliseconds) has passed.
                String s = String.format("$brightness = %d;", brightness)
                        + "$delay = 0;"
                        + "$myMonitor = Get-WmiObject -Namespace root\\wmi -Class WmiMonitorBrightnessMethods;"
                        + "$myMonitor.wmisetbrightness($delay, $brightness)";
                String command = "powershell.exe  " + s;
                // Executing the command
                Process powerShellProcess = Runtime.getRuntime().exec(command);

                powerShellProcess.getOutputStream().close();

                //Report any error messages
                String line;

                BufferedReader stderr = new BufferedReader(new InputStreamReader(
                        powerShellProcess.getErrorStream()));
                line = stderr.readLine();
                if (line != null)
                {
                    System.err.println("Standard Error:");
                    do
                    {
                        System.err.println(line);
                    } while ((line = stderr.readLine()) != null);

                }
                stderr.close();

            }
        }

        //Adding event Filter
        Object brightness = 50;
        Object finalBrightness = brightness;
        button5.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                BrightnessManager.setBrightness(new Object[]{finalBrightness});
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        //Adding event Filter
        brightness = 30;
        Object finalBrightness1 = brightness;
        button6.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                BrightnessManager.setBrightness(new Object[]{finalBrightness1});
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        //Adding event Filter
        brightness = 100;
        Object finalBrightness2 = brightness;
        button7.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                BrightnessManager.setBrightness(new Object[]{finalBrightness2});
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });


        Button button11 = new Button("BACK");
        root.getChildren().add(button11);

        button11.setTranslateX(this.getWidth()/11.2 - button11.getWidth()/2);
        button11.setTranslateY(this.getHeight()/3 - button11.getHeight()/2 + 310);
        button11.setScaleX(5.2);
        button11.setScaleY(2);
        button11.setEffect(Utils.neonEffect());
        button11.setTextFill(Color.WHITE);

        button11.setOnMouseClicked(event -> {
            stage.setScene(new OptionScene(stage,width,height));
        });

    }
}