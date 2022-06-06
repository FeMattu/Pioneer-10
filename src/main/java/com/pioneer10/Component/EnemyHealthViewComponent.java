package com.pioneer10.Component;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.paint.Color;

@Required(HealthIntComponent.class)
public class EnemyHealthViewComponent extends ChildViewComponent {

    private HealthIntComponent hp;
    private ProgressBar hpBar;

    public EnemyHealthViewComponent() {
        super(0, -20, false);

        hpBar = new ProgressBar(true);
        hpBar.setWidth(60);
        hpBar.setHeight(10);
        hpBar.setFill(Color.LIGHTGREEN);
        hpBar.setLabelVisible(false);

        getViewRoot().getChildren().add(hpBar);
    }

    @Override
    public void onAdded() {
        super.onAdded();

        hpBar.maxValueProperty().bind(hp.maxValueProperty());
        hpBar.currentValueProperty().bind(hp.valueProperty());
    }
}
