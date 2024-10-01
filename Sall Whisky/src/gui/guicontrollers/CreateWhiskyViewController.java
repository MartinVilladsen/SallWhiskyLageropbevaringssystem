package gui.guicontrollers;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateWhiskyViewController implements Initializable {
    @FXML
    private TextField amountOfFillCltxf;
    @FXML
    private Button btnCalcNumberOfBottles;
    @FXML
    private Button btnCreateWhisky;
    @FXML
    private Button btnRegisterAlcoholpercentage;
    @FXML
    private ComboBox<Integer> cbbBottleSizeInCl;
    @FXML
    private Label lblAmountOfBottles;
    @FXML
    private Label lblTypeOfWhisky;
    @FXML
    private ListView<WhiskyFill> lvwWhiskybatch;
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
    private TextArea txaContentOfWhisky;
    @FXML
    private TextArea txaDescriptionOfWhisky;
    @FXML
    private TextField txfAlcoholPercentage;
    @FXML
    private TextField txfWaterForDilution;
    @FXML
    private TextField txfWhiskyName;
    private WhiskyFill whiskyFill;
    private Whisky whisky;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer bottleSize[] = {50, 75, 100, 120};
        cbbBottleSizeInCl.setItems(FXCollections.observableArrayList(bottleSize));

        tbcCaskID.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        tbcAlcoholPercentage.setCellValueFactory(new PropertyValueFactory<Cask, Double>("TotalAlcoholPercentage"));
        tbcAge.setCellValueFactory(new PropertyValueFactory<Cask, TapFromDistillate>("YoungestTapFromDistillate"));
        tbcTotalLitersOfFills.setCellValueFactory(new PropertyValueFactory<Cask, Double>("CurrentContentInLiters"));
        lvwWhiskybatch.getItems().setAll(Controller.getWhiskyFills());
        lblTypeOfWhisky.setText(Controller.getWhiskyType(lvwWhiskybatch.getItems()));
        updateRipeCasks();
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Calculates the amount of bottles that can be filled with the selected whisky */
    @FXML
    void btnCalcNumberOfBottlesOnAction(ActionEvent event) {
        if (cbbBottleSizeInCl.getSelectionModel().getSelectedItem() == null) {
            cbbBottleSizeInCl.setStyle("-fx-border-color: red;");
            return;
        }
        int sizeOfBottle = cbbBottleSizeInCl.getSelectionModel().getSelectedItem();
        double waterInLiters = txfParseDouble(txfWaterForDilution);

        if (waterInLiters < 0) {
            return;
        }

        if (lvwWhiskybatch.getItems().size() == 0) {
            showErrorWindow("Ingen batch", "Du har ingen whiskybatches");
        }

        String name = txfWhiskyName.getText().trim();
        String description = txaDescriptionOfWhisky.getText().trim();
        whisky = new Whisky(name, waterInLiters, lvwWhiskybatch.getItems(), description);
        int amountOfBottles = Controller.amountOfBottles(whisky, sizeOfBottle);
        lblAmountOfBottles.setText("" + amountOfBottles);
        btnCreateWhisky.setDisable(false);
        lvwWhiskybatch.getItems().setAll(Controller.getWhiskyFills());
        clearErrorMarkings();
    }

    /** Creates a new whisky object and whisky bottles for the whisky */
    @FXML
    void btnCreateWhiskyOnAction(ActionEvent event) {
        int sizeOfBottle = cbbBottleSizeInCl.getSelectionModel().getSelectedItem();
        double waterInLiters = txfParseDouble(txfWaterForDilution);
        int amountOfBottles = Integer.parseInt(lblAmountOfBottles.getText());

        if (amountOfBottles < 0) {
            return;
        }
        String name = txfWhiskyName.getText().trim();

        if (name.isEmpty()) {
            txfWhiskyName.setStyle("-fx-border-color: red;");
            return;
        }

        String description = txaDescriptionOfWhisky.getText().trim();
        whisky = Controller.createWhisky(name, waterInLiters, new ArrayList<>(lvwWhiskybatch.getItems()), description);

        Controller.createWhiskyBottlesForWhisky(amountOfBottles, sizeOfBottle, whisky);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Whisky");
        alert.setHeaderText("Whisky oprettet");
        alert.setContentText("En ny whisky er oprettet med navn: " + whisky.getName() + "\n" + "Der er oprettet " + amountOfBottles + " flasker med den valgte whisky");
        alert.show();
        clearAllEditableFields();
        clearErrorMarkings();
    }

    /** Registers the alcohol percentage of the whisky fill
     * If the cask is null, return error window */
    @FXML
    void btnRegisterAlcoholpercentageOnAction(ActionEvent event) {
        Cask cask = tvwRipeCasks.getSelectionModel().getSelectedItem();
        if (cask == null) {
            showErrorWindow("Intet fad", "Du har ikke valgt et fad");
            return;
        }
        double value = 0.0;
        double amountToFill = 0.0;

        value = txfParseDouble(txfAlcoholPercentage);
        amountToFill = txfParseDouble(amountOfFillCltxf);

        if (value < 1 || amountToFill < 1 || value > 99) {
            if (value > 99 || value < 1) {
                txfAlcoholPercentage.setStyle("-fx-border-color: red;");
            }
            if (amountToFill < 1) {
                amountOfFillCltxf.setStyle("-fx-border-color: red;");
            }
            return;
        }
        ArrayList<TapFromDistillate> tempFillsOnCask = new ArrayList<>();
        for (FillOnCask fillOnCask : cask.getCurrentFillOnCasks()) {
            tempFillsOnCask.add(fillOnCask.getTapFromDistillate());
        }

        try {
            whiskyFill = Controller.createWhiskyFill(amountToFill, tempFillsOnCask, value, cask);
        } catch (InterruptedException e) {
            showErrorWindow("Påfyldningsfejl", e.getMessage());
            return;
        }

        tvwRipeCasks.getItems().removeAll();
        updateRipeCasks();
        updatelvwWhiskyFillReadyForFill();
        updateContentOfWhisky(whiskyFill.toString());
        btnCreateWhisky.setDisable(true);
        lblTypeOfWhisky.setText(Controller.getWhiskyType(lvwWhiskybatch.getItems()));
        clearErrorMarkings();
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Shows an errorWindow */
    private void showErrorWindow(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Fejl");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    /** Clears the error markings on the textfields */
    private void clearErrorMarkings() {
        txfWaterForDilution.setStyle("-fx-border-color: transparent;");
        amountOfFillCltxf.setStyle("-fx-border-color: transparent;");
        txfAlcoholPercentage.setStyle("-fx-border-color: transparent;");
        txfWhiskyName.setStyle("-fx-border-color: transparent;");
    }

    /** Parses a string to a double */
    private double txfParseDouble(TextField txf) {
        double returnValue = -1.0;
        try {
            returnValue = Double.parseDouble(txf.getText().trim());
        } catch (NumberFormatException e) {
            txf.setStyle("-fx-border-color: red;");
            return returnValue;
        }
        txf.setOnMouseClicked(e -> {txf.setStyle("-fx-border-color: transparent;");});
        return returnValue;
    }

    /** Clears all editable fields */
    private void clearAllEditableFields() {
        lvwWhiskybatch.getItems().clear();
        txfAlcoholPercentage.clear();
        amountOfFillCltxf.clear();
        txfWhiskyName.clear();
        txfWaterForDilution.clear();
        txaContentOfWhisky.clear();
        txaDescriptionOfWhisky.clear();
        lblAmountOfBottles.setText("" + 0);
        lblTypeOfWhisky.setText("");
        cbbBottleSizeInCl.valueProperty().set(null);
    }

    /** Updates the listview with the whisky fills ready for fill */
    private void updatelvwWhiskyFillReadyForFill() {
        if (lvwWhiskybatch.getItems().isEmpty()) {
            return;
        }
        lvwWhiskybatch.getItems().setAll(Controller.getWhiskyFills());
    }

    /** Updates the content of the whisky */
    private void updateContentOfWhisky(String content) {
        txaContentOfWhisky.appendText(content + " \n");
    }


    /** Updates the tableview with the ripe casks */
    private void updateRipeCasks() {
        tvwRipeCasks.getItems().setAll(Controller.getRipeCasks());
        lvwWhiskybatch.getItems().setAll(Controller.getWhiskyFills());
    }
}
