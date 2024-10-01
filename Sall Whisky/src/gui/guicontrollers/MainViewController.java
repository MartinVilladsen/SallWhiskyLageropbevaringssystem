package gui.guicontrollers;

import controller.Controller;
import gui.javaFXViews.CreateWhiskyStoryWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Cask;
import model.TapFromDistillate;
import model.Whisky;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnCRUDCask;
    @FXML
    private Button btnCRUDRawMaterial;
    @FXML
    private Button btnCRUDStorage;
    @FXML
    private Button btnCRUDSupplier;
    @FXML
    private Button btnDestillateAndFillOnCask;
    @FXML
    private Button btnStartside;
    @FXML
    private Button btnCreateWhisky;
    @FXML
    private TableColumn<Cask, TapFromDistillate> tbcAge;
    @FXML
    private TableColumn<Cask, Double> tbcAlcoholPercentage;
    @FXML
    private TableColumn<Cask, Integer> tbcCaskID;
    @FXML
    private TableColumn<Cask, Double> tbcTotalLitersOfFills;
    @FXML
    private TableView<Cask> tvwRipeCasks;
    @FXML
    private Button btnGetWhiskyStory;
    @FXML
    private ListView<Whisky> lvwWhisky;
    private Whisky whisky;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcCaskID.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        tbcAlcoholPercentage.setCellValueFactory(new PropertyValueFactory<Cask, Double>("TotalAlcoholPercentage"));
        tbcAge.setCellValueFactory(new PropertyValueFactory<Cask, TapFromDistillate>("YoungestTapFromDistillate"));
        tbcTotalLitersOfFills.setCellValueFactory(new PropertyValueFactory<Cask, Double>("CurrentContentInLiters"));
        tvwRipeCasks.getItems().setAll(Controller.getRipeCasks());
        lvwWhisky.getItems().setAll(Controller.getWhiskies());
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Initializes the stage and scene for the createCaskView */
    @FXML
    void btnCreateWhiskyOnAction(ActionEvent event) throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateWhiskyView.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Opret Whisky");
        stage.setScene(new Scene(root1));
        stage.showAndWait();
        updateViews();
    }

    /** Updates and initializes the tableview with all the casks */
    @FXML
    void btnGetWhiskyStoryOnAction(ActionEvent event) {
        whisky = lvwWhisky.getSelectionModel().getSelectedItem();
        if (whisky != null) {
            CreateWhiskyStoryWindow dialog = new CreateWhiskyStoryWindow(whisky);
            dialog.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Whisky");
            alert.setHeaderText("Du skal vælge en whisky");
            alert.setContentText("Du skal vælge en whisky for at kunne hente en historie");
            alert.show();
        }
    }

    // ---------------------------------------------------------------------
    /** Scene switch buttons */
    // ---------------------------------------------------------------------

    @FXML
    void btnCrudCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudCask(stage, scene, event);
    }
    @FXML
    void btnCrudStorageOnAction(ActionEvent event) throws IOException{
        SwitchSceneController.btnCrudStorage(stage, scene, event);
    }
    @FXML
    void btnDestillateAndFillOnCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnDestillateAndFillOnCaskOnAction(stage, scene, event);
    }
    @FXML
    void btnRawMaterialOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnRawMaterial(stage, scene, event);
    }
    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCRUDSupplier(stage, scene, event);
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /**
     * Update the Whisky ListView and TableView RipeCasks
     */
    private void updateViews() {
        tvwRipeCasks.getItems().setAll(Controller.getRipeCasks());
        lvwWhisky.getItems().setAll(Controller.getWhiskies());
    }
    public Whisky getWhisky() {
        return whisky;
    }
}
