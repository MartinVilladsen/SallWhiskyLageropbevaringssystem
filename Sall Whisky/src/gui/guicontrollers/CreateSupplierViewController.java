package gui.guicontrollers;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CaskSupplier;
import model.GrainSupplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateSupplierViewController implements Initializable {
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
    private Button btnCreateSupplier;
    @FXML
    private ComboBox<String> cbbSupplier;
    @FXML
    private ListView<CaskSupplier> lvwCaskSuppliers;
    @FXML
    private ListView<GrainSupplier> lvwGrainSuppliers;
    @FXML
    private TextField txfAddress;
    @FXML
    private TextField txfCountry;
    @FXML
    private TextField txfName;
    @FXML
    private TextField txfVatId;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------

    /** Uses eventListeners to update the supplier view */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lvwCaskSuppliers.getItems().setAll(Controller.getCaskSuppliers());
        lvwGrainSuppliers.getItems().setAll(Controller.getGrainSuppliers());

        ChangeListener<GrainSupplier> grainSupplierChangeListener = (ov, o, n) -> this.selectedStorageItemChanged();
        lvwGrainSuppliers.getSelectionModel().selectedItemProperty().addListener(grainSupplierChangeListener);

        ChangeListener<CaskSupplier> caskSupplierChangeListener = (ov, o, n) -> this.selectedStorageItemChanged();
        lvwCaskSuppliers.getSelectionModel().selectedItemProperty().addListener(caskSupplierChangeListener);

        String suppliers[] = {"Kornleverandør", "Fadleverandør"};
        cbbSupplier.setItems(FXCollections.observableArrayList(suppliers));
        cbbSupplier.getSelectionModel().select(cbbSupplier.getItems().get(0));
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /**
     * Creates a new supplier on input
     * Checks if the input is valid
     * Updates the listviews
     */
    @FXML
    void btnCreateSupplierOnAction(ActionEvent event) {
        String name = txfName.getText().trim();
        String address = txfAddress.getText().trim();
        String country = txfCountry.getText().trim();
        String vatId = txfVatId.getText().trim();

        if (name.isEmpty()) {
            txfName.setStyle("-fx-border-color: red;");
            txfName.setOnMouseClicked(e -> {
                txfName.setStyle("-fx-border-color: transparent;");});
        }
        if (address.isEmpty()) {
            txfAddress.setStyle("-fx-border-color: red;");
            txfAddress.setOnMouseClicked(e -> {
            txfAddress.setStyle("-fx-border-color: transparent;");});
        }
        if (country.isEmpty()) {
            txfCountry.setStyle("-fx-border-color: red;");
            txfCountry.setOnMouseClicked(e -> {
                txfCountry.setStyle("-fx-border-color: transparent;");});
        }
        if (vatId.isEmpty()) {
            canParseToInteger(txfVatId);
        }
        if (country.isEmpty() || name.isEmpty()  || address.isEmpty() || vatId.isEmpty()) {
            return;
        }
        if (cbbSupplier.getSelectionModel().getSelectedItem().equals("Kornleverandør")) {
            Controller.createGrainSupplier(name, address, country, vatId);
            clearInput();
        } else if (cbbSupplier.getSelectionModel().getSelectedItem().equals("Fadleverandør")) {
            Controller.createCaskSupplier(name, address, country, vatId);
            clearInput();
        }
        updateLvwCaskSupplier();
        updateLvwGrainSupplier();
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Clears the input fields */
    private void clearInput() {
        txfName.clear();
        txfAddress.clear();
        txfCountry.clear();
        txfVatId.clear();
        txfName.setStyle("-fx-border-color: transparent");
        txfAddress.setStyle("-fx-border-color: transparent");
        txfCountry.setStyle("-fx-border-color: transparent");
        txfVatId.setStyle("-fx-border-color: transparent");
    }

    /** Checks if the input can be parsed to an integer */
    private boolean canParseToInteger(TextField txf) {
        boolean cannotParse = false;
        try {
            Integer.parseInt(txf.getText().trim());
        } catch (NumberFormatException exception) {
            cannotParse = true;
            txf.setStyle("-fx-border-color: red;");
            txf.setOnMouseClicked(e -> {txf.setStyle("-fx-border-color: transparent");});
        }
        return cannotParse;
    }

    /** Updates the grain supplier listview */
    private void updateLvwGrainSupplier() {
        lvwGrainSuppliers.getItems().setAll(Controller.getGrainSuppliers());
    }

    /** Updates the cask supplier listview */
    private void updateLvwCaskSupplier() {
        lvwCaskSuppliers.getItems().setAll(Controller.getCaskSuppliers());
    }

    /** Updates the listviews */
    public void selectedStorageItemChanged() {
        GrainSupplier selectedGrainSupplier = lvwGrainSuppliers.getSelectionModel().getSelectedItem();
        CaskSupplier selectedCaskSupplier = lvwCaskSuppliers.getSelectionModel().getSelectedItem();
    }

    // ---------------------------------------------------------------------
    /** Scene switch buttons */
    // ---------------------------------------------------------------------

    /** Switches to the CRUDCask view */
    @FXML
    void btnCrudCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudCask(stage, scene, event);
    }
    /** Switches to the warehouse view */
    @FXML
    void btnCrudStorageOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudStorage(stage, scene, event);
    }
    /** Switches to the RawMaterial view */
    @FXML
    void btnRawMaterialOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnRawMaterial(stage, scene, event);
    }
    /** Switches to the start view */
    @FXML
    void btnStartSideOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnStartView(stage, scene, event);
    }
    /** Switches to the DestillateAndFillOnCask view */
    @FXML
    void btnDestillateAndFillOnCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnDestillateAndFillOnCaskOnAction(stage, scene, event);
    }
}
