package gui.javaFXViews;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Whisky;

public class CreateWhiskyStoryWindow extends Stage {
    private Whisky whisky;
    private TextArea txaWhiskyStory = new TextArea();

    public CreateWhiskyStoryWindow(Whisky whisky) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.whisky = whisky;
        this.setTitle("Hent whiskyhistorie");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public void initContent (GridPane pane) {
        txaWhiskyStory.setText(Controller.generateStoryForWhisky(whisky));
        txaWhiskyStory.setPrefHeight(700);
        pane.setPadding(new Insets(20));
        pane.setHgap(30);
        pane.setVgap(10);
        Label lblWhiskyStory = new Label("Whisky historie");
        pane.add(lblWhiskyStory, 0,0);
        pane.add(txaWhiskyStory, 0,1);
    }
}
