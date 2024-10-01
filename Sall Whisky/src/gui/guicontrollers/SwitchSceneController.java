package gui.guicontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class SwitchSceneController {

    public static void btnStartView(Stage stage, Scene scene, ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/MainView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void btnCrudCask(Stage stage, Scene scene, ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CRUDCaskView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void btnCrudStorage(Stage stage, Scene scene, ActionEvent event) throws IOException{
        URL url = new File("Sall Whisky/src/gui/views/CRUDWarehouseView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void btnRawMaterial(Stage stage, Scene scene, ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CRUDRawMaterialsView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void btnCRUDSupplier(Stage stage, Scene scene, ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateSupplierView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void btnDestillateAndFillOnCaskOnAction(Stage stage, Scene scene, ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateDistilleryFillOnCask.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
