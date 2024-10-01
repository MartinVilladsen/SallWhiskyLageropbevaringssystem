package gui.guicontrollers;

        import controller.Controller;
        import javafx.beans.value.ChangeListener;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.ListView;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;
        import model.*;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

public class CRUDWarehouseViewController implements Initializable {
    @FXML
    private AnchorPane ap_Pane1;

    @FXML
    private Button btnCRUDCask;

    @FXML
    private Button btnCRUDRawMaterial;

    @FXML
    private Button btnCRUDSupplier;

    @FXML
    private Button btnCRUDWarehouse;

    @FXML
    private Button btnCreateWarehouse;

    @FXML
    private Button btnDestillateAndFillOnCask;

    @FXML
    private Button btnStartside;

    @FXML
    private ListView<Position> lvwPosition;

    @FXML
    private ListView<Rack> lvwRack;

    @FXML
    private ListView<Shelf> lvwShelf;

    @FXML
    private ListView<Warehouse> lvwWarehouse;
    @FXML
    private TextField txfRackAmount;
    @FXML
    private TextField txfPositionAmount;
    @FXML
    private TextField txfShelfAmount;
    @FXML
    private TextField txfWarehouseAddress;
    private Stage stage;
    private Scene scene;
    private Rack rack;
    private Shelf shelf;
    private Position position;
    private Warehouse currentlySelectedWarehouse;
    private Rack currentlySelectedRack;
    private Shelf currentlySelectedShelf;
    private Warehouse warehouse;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChangeListener<Warehouse> warehouseChangeListener = (ov, o, n) -> this.selectedWarehouseChanged();
        lvwWarehouse.getSelectionModel().selectedItemProperty().addListener(warehouseChangeListener);

        ChangeListener<Rack> rackChangeListener = (ov, o, n) -> this.selectedRackChanged();
        lvwRack.getSelectionModel().selectedItemProperty().addListener(rackChangeListener);

        ChangeListener<Shelf> shelfChangeListener = (ov, o, n) -> this.selectedShelfChanged();
        lvwShelf.getSelectionModel().selectedItemProperty().addListener(shelfChangeListener);

        lvwWarehouse.getItems().setAll(Controller.getStorage().getWarehouses());
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Pre: Racks, Shelves and Positions amount < 10
     * Creates a warehouse with the given address and the given amount of racks, shelves and positions
     * Updates the listview with all the warehouses
     * */
    @FXML
    void btnCreateWarehouseOnAction(ActionEvent event) {
        boolean missingInfo = canParseToInteger(txfRackAmount);
        missingInfo = canParseToInteger(txfShelfAmount);
        missingInfo = canParseToInteger(txfPositionAmount);

        if (txfWarehouseAddress.getText().isEmpty()) {
            missingInfo = true;
            txfWarehouseAddress.setStyle("-fx-border-color: red;");
            txfWarehouseAddress.setOnMouseClicked(e -> {
                txfWarehouseAddress.setStyle("-fx-border-color: transparent;");});
        }
        if (Integer.parseInt(txfRackAmount.getText()) > 10) {
            txfRackAmount.setStyle("-fx-border-color: red;");
            txfRackAmount.setOnMouseClicked(e -> {
                txfRackAmount.setStyle("-fx-border-color: transparent;");});
        }
            if (Integer.parseInt(txfShelfAmount.getText()) > 10) {
                txfShelfAmount.setStyle("-fx-border-color: red;");
                txfShelfAmount.setOnMouseClicked(e -> {
                    txfShelfAmount.setStyle("-fx-border-color: transparent;");});
            }
                if (Integer.parseInt(txfPositionAmount.getText()) > 10) {
                    txfPositionAmount.setStyle("-fx-border-color: red;");
                    txfPositionAmount.setOnMouseClicked(e -> {
                        txfPositionAmount.setStyle("-fx-border-color: transparent;");});
                }

                if (txfWarehouseAddress.getText().isEmpty() || Integer.parseInt(txfRackAmount.getText()) > 10 ||
                        Integer.parseInt(txfShelfAmount.getText()) > 10 || Integer.parseInt(txfPositionAmount.getText()) > 10) {
                    return;
                }

        if (!missingInfo) {
            warehouse = Controller.createWarehouse(txfWarehouseAddress.getText());
            for (int i = 0; i < Integer.parseInt(txfRackAmount.getText()); i++) {
                rack = Controller.createRack(warehouse);
                for (int j = 0; j < Integer.parseInt(txfShelfAmount.getText()); j++) {
                    shelf = Controller.createShelf(rack);
                    for (int k = 0; k < Integer.parseInt(txfPositionAmount.getText()); k++) {
                        position = Controller.createPosition(shelf, 500);
                    }
                }
            }
        }
        updateWarehouses();
        clearInput();
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Handles change in the selected warehouse in the UI
     * Updates listviews based on selection */
    public void selectedWarehouseChanged() {
        Warehouse selectedWarehouse = lvwWarehouse.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            lvwRack.getItems().setAll(selectedWarehouse.getRacks());
            lvwShelf.getItems().clear();
            lvwPosition.getItems().clear();
        }
    }

    /** Handles change in the selected rack in the UI
     * Updates listviews based on selection */
    public void selectedRackChanged() {
        Rack selectedRack = lvwRack.getSelectionModel().getSelectedItem();
        if (selectedRack != null) {
            lvwShelf.getItems().setAll(selectedRack.getShelves());
            lvwPosition.getItems().clear();
        }
    }

    /** Handles change in the selected Shelf in the UI
     * Updates listviews based on selection */
    public void selectedShelfChanged() {
        Shelf selectedShelf = lvwShelf.getSelectionModel().getSelectedItem();
        if (selectedShelf != null) {
            lvwPosition.getItems().setAll(selectedShelf.getPositions());
        }
    }

    /** Checks if the input can be parsed to an integer */
    private boolean canParseToInteger(TextField txf) {
        boolean cannotParse = false;
        try {
            double returnValue = Integer.parseInt(txf.getText().trim());
        } catch (NumberFormatException exception) {
            cannotParse = true;
            txf.setStyle("-fx-border-color: red;");
            txf.setOnMouseClicked(e -> {txf.setStyle("-fx-border-color: transparent");});
        }
        return cannotParse;
    }

    private void updateWarehouses() {
        lvwWarehouse.getItems().setAll(Controller.getStorage().getWarehouses());
    }

    private void clearInput() {
        txfRackAmount.clear();
        txfShelfAmount.clear();
        txfPositionAmount.clear();
        txfWarehouseAddress.clear();
    }

    // ---------------------------------------------------------------------
    /** Scene switch buttons */
    // ---------------------------------------------------------------------

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
    @FXML
    void btnCrudCaskOnAction(ActionEvent event) throws IOException {
        SwitchSceneController.btnCrudCask(stage, scene, event);
    }
}

