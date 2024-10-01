package gui.guicontrollers;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CRUDDistilleryAndFillController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private TextField txfAlcoholpercentage;
    @FXML
    private TextField txfAmountOfDistillateInLiters;
    @FXML
    private ListView<Cask> lvwAvailableCasks;
    @FXML
    private Button btnCRUDCask;
    @FXML
    private Button btnCRUDRawMaterial;
    @FXML
    private Button btnCRUDStorage;
    @FXML
    private Button btnCRUDSupplier;
    @FXML
    private Button btnCreateDistillate;
    @FXML
    private Button btnDestillateAndFillOnCask;
    @FXML
    private Button btnFillOnCask;
    @FXML
    private Button btnStartside;
    @FXML
    private ListView<Distillate> lvwDistillates;
    @FXML
    private TextField txfDistillationTime;
    @FXML
    private TextField txfNewMakeNo;
    @FXML
    private TextField txfEmployee;
    @FXML
    private ListView<Maltbatch> lvwMaltbatches;
    @FXML
    private TextField txfSmokingMaterial;
    @FXML
    private TextField txfAmountToPutOnCaskInLiters;
    @FXML
    private TextArea txaDescription;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvwAvailableCasks.getItems().setAll(Controller.getAvailableCasks());
        lvwMaltbatches.getItems().setAll(Controller.getMaltbatches());
        lvwDistillates.getItems().setAll(Controller.getAvailableDistillates());

        txfNewMakeNo.setOnMouseClicked(e -> { clearErrorMarkings();});
        txfEmployee.setOnMouseClicked(e -> { clearErrorMarkings();});
        txfAlcoholpercentage.setOnMouseClicked(e -> { clearErrorMarkings();});
        txfAmountOfDistillateInLiters.setOnMouseClicked(e -> { clearErrorMarkings();});
        txfSmokingMaterial.setOnMouseClicked(e -> { clearErrorMarkings();});
        txfDistillationTime.setOnMouseClicked(e -> { clearErrorMarkings();});
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Creates a distillate with the given information
     * If the information is invalid, the textfields will be marked red
     * If the information is valid, the distillate will be created and the textfields will be cleared
     */
    @FXML
    void btnCreateDistillateOnAction() {
        String newMakenr = txfNewMakeNo.getText().trim();
        Maltbatch maltbatch = lvwMaltbatches.getSelectionModel().getSelectedItem();
        double distillationTime;
        double alcoholPercentage;
        double amountInLiters;
        String employee = txfEmployee.getText().trim();
        if (maltbatch == null) {
            lvwMaltbatches.setStyle("-fx-border-color: red;");
            return;
        }
        List<Maltbatch> maltBatches = new ArrayList<>();
        maltBatches.add(lvwMaltbatches.getSelectionModel().getSelectedItem());

        distillationTime = txfParseDouble(txfDistillationTime);
        alcoholPercentage = txfParseDouble(txfAlcoholpercentage);
        amountInLiters = txfParseDouble(txfAmountOfDistillateInLiters);

        if (distillationTime < 0) {
            txfDistillationTime.setStyle("-fx-border-color: red;");
            return;
        }
        if (amountInLiters < 0) {
            txfAmountOfDistillateInLiters.setStyle("-fx-border-color: red;");
            return;
        }
        if (alcoholPercentage > 99 || alcoholPercentage < 0) {
            txfAlcoholpercentage.setStyle("-fx-border-color: red;");
            return;
        }

        if (employee.isEmpty()) {
            txfEmployee.setStyle("-fx-border-color: red;");
            return;
        }
        String description = txaDescription.getText().trim();

        Controller.createDistillate(newMakenr, distillationTime, alcoholPercentage, amountInLiters, employee, maltBatches, description);
        clearErrorMarkings();
        clearCreateDistillateFields();
        updateControls();
    }

    /** Fills the selected cask with the selected distillate
     * If the user has not selected a distillate prompt the user to select one
     * If the user has not selected a cask prompt the user to select one
     * Alert the user that the cask has been filled
     */
    @FXML
    void btnFillOnCaskOnAction() {
        Distillate distillate = lvwDistillates.getSelectionModel().getSelectedItem();
        Cask cask = lvwAvailableCasks.getSelectionModel().getSelectedItem();
        double amountInLiters = txfParseDouble(txfAmountToPutOnCaskInLiters);

        if (cask == null) {
            lvwAvailableCasks.setStyle("-fx-border-color: red;");
            return;
        }
        if (amountInLiters < 0) {
            return;
        }
        if (distillate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setHeaderText("Du skal vælge et distillat");
            alert.setContentText("Du skal vælge et distillat for at påfylde");
            alert.show();
            return;
        }
        ArrayList<DistillateFill> distillateFills = new ArrayList<>();
        distillateFills.add(new DistillateFill(amountInLiters, distillate));
        try {
            Controller.createTapFromDistillate(LocalDate.now(), cask, distillateFills);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setHeaderText("Fejl");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Påfyldning");
        alert.setHeaderText("Påfyldning oprettet");
        alert.setContentText("En påfyldning af " + amountInLiters + " liter er blevet påfyldt på fadID: " + cask.getCaskId());
        alert.show();
        clearErrorMarkings();
        clearCreateDistillateFillFields();
        updateControls();
    }

    // ---------------------------------------------------------------------
    /** Scene switch buttons */
    // ---------------------------------------------------------------------

    @FXML
    void btnCrudCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudCask(stage, scene, event);
    }
    @FXML
    void btnCrudStorageOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudStorage(stage, scene, event);
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

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Parses the textfield to a double */
    private double txfParseDouble(TextField txf) {
        double returnValue = -1.0;
        try {
            returnValue = Double.parseDouble(txf.getText().trim());
        } catch (NumberFormatException e) {
            txf.setStyle("-fx-border-color: red;");
        }
        return returnValue;
    }

    /** Clears the error markings on the textfields */
    private void clearErrorMarkings() {
        txfNewMakeNo.setStyle("-fx-border-color: transparent;");
        txfEmployee.setStyle("-fx-border-color: transparent;");
        txfAmountOfDistillateInLiters.setStyle("-fx-border-color: transparent;");
        txfAlcoholpercentage.setStyle("-fx-border-color: transparent;");
        txfAmountToPutOnCaskInLiters.setStyle("-fx-border-color: transparent;");
        txfDistillationTime.setStyle("-fx-border-color: transparent;");
        lvwMaltbatches.setStyle("-fx-border-color: transparent;");
    }

    /** Updates the listviews */
    private void updateControls() {
        lvwDistillates.getItems().setAll(Controller.getAvailableDistillates());
        lvwAvailableCasks.getItems().setAll(Controller.getAvailableCasks());
    }

    /** Clears the textfields */
    private void clearCreateDistillateFields() {
        txfDistillationTime.clear();
        txfAlcoholpercentage.clear();
        txfEmployee.clear();
        txfSmokingMaterial.clear();
        txfNewMakeNo.clear();
        txfAmountOfDistillateInLiters.clear();
        txaDescription.clear();
    }

    private void clearCreateDistillateFillFields() {
        txfAmountToPutOnCaskInLiters.clear();
    }
}
