package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.NoSuchElementException;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Gui launched");

//        URL fxmlFileName = new File("Sall Whisky/src/gui/views/MainView.fxml").toURI().toURL();
        URL fxmlFileName = new File("Sall Whisky/src/gui/views/MainView.fxml").toURI().toURL();
        if (fxmlFileName == null) throw new NoSuchElementException("FXML file not found");

        Parent root = FXMLLoader.load(fxmlFileName);
        stage.setMinWidth(root.minWidth(-1));
        stage.setMinHeight(root.minHeight(-1));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
