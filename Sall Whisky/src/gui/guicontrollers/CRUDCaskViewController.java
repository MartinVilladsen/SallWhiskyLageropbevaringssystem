package gui.guicontrollers;

import controller.Controller;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CRUDCaskViewController implements Initializable {

    @FXML
    private Button btnCRUDCask;
    @FXML
    private Button btnCRUDRawMaterial;
    @FXML
    private Button btnCRUDStorage;
    @FXML
    private Button btnCRUDSupplier;
    @FXML
    private Button btnCreateCask;
    @FXML
    private Button btnDeleteCask;
    @FXML
    private Button btnTransference;
    @FXML
    private Button btnStartside;
    @FXML
    private TableColumn<Cask, String> columnCountryOfOrigin;
    @FXML
    private TableColumn<Cask, Integer> columnID;
    @FXML
    private TableColumn<Cask, Integer> columnPosition;
    @FXML
    private TableColumn<Cask, String> columnPreviousContent;
    @FXML
    private TableColumn<Cask, Integer> columnRack;
    @FXML
    private TableColumn<Cask, Integer> columnShelf;
    @FXML
    private TableColumn<Cask, Double> columnSizeInLiters;
    @FXML
    private TableColumn<Cask, Double> columnSpaceAvailableInLiters;
    @FXML
    private TableColumn<Cask, Integer> columnWarehouse;
    @FXML
    private TableView<Cask> tvwCasks;
    private Stage stage;
    private Scene scene;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------

    /** Updates and initializes the tableview with all the casks */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnID.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        columnCountryOfOrigin.setCellValueFactory(new PropertyValueFactory<Cask, String>("countryOfOrigin"));
        columnSizeInLiters.setCellValueFactory(new PropertyValueFactory<Cask, Double>("sizeInLiters"));
        columnSpaceAvailableInLiters.setCellValueFactory(new PropertyValueFactory<Cask, Double>("LitersAvailable"));
        columnPreviousContent.setCellValueFactory(new PropertyValueFactory<Cask, String>("previousContent"));
        columnWarehouse.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("warehouseId"));
        columnRack.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("rackId"));
        columnShelf.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("shelfId"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("positionId"));

        updateTvwCasks();

    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Initializes the stage and scene for the createCaskView */
    @FXML
    void btnCreateCaskOnAction() throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateCaskView.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Opret fad");
        stage.setScene(new Scene(root1));
        stage.showAndWait();
        updateTvwCasks();
    }

    /**
     * Deletes the selected Cask
     * If the cask has any fillOnCasks or previousFillOnCasks connected
     * prompt the user with that info and abort deletion
     */
    @FXML
    void btnDeleteCaskOnAction() {
        Cask cask = tvwCasks.getSelectionModel().getSelectedItem();
        if (tvwCasks.getSelectionModel().isEmpty()) {
            tvwCasks.setStyle("-fx-border-color: red;");
        }
        else if (!cask.getCurrentFillOnCasks().isEmpty() || !cask.getPreviousPutOnCasks().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setHeaderText("Fad kan ikke slettes");
            alert.setContentText("Dette fad kan ikke slettes, da der er eller har været opfyldninger " +
                    "på faddet.");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræft sletning");
            alert.setHeaderText("Er du sikker på, at du vil slette dette fad");
            alert.getButtonTypes().setAll(new ButtonType("Ja"), new ButtonType("Fortryd"));
            ButtonType choice = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (choice.getText() == "Ja") {
                Controller.removeCask(cask);
            }
            updateTvwCasks();

        }
    }

    /** Initializes the stage and scene for the createTransferenceView */
    @FXML
    void btnTransferenceOnAction() throws IOException {
        URL url = new File("Sall Whisky/src/gui/views/CreateTransferenceView.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Omhæld fad");
        stage.setScene(new Scene(root1));
        stage.showAndWait();
        updateTvwCasks();
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Updates the tableview with all the casks */
    private void updateTvwCasks() {
        tvwCasks.getItems().setAll(Controller.getCasks());
    }

    // ---------------------------------------------------------------------
    /** Scene switch buttons */
    // ---------------------------------------------------------------------

    @FXML
    void btnCrudStorageOnAction(ActionEvent event) throws IOException {
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
    void btnStartSideOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnStartView(stage, scene, event);
    }
    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCRUDSupplier(stage, scene, event);
    }
}
