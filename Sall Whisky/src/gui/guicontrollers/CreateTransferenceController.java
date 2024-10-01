package gui.guicontrollers;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cask;
import model.TapFromDistillate;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateTransferenceController implements Initializable {

    @FXML
    private Button btnShowAvailableCasksForTransference;
    @FXML
    private Button btnTransference;
    @FXML
    private TableColumn<Cask, TapFromDistillate> tbcAge;
    @FXML
    private TableColumn<Cask, TapFromDistillate> tbcAge1;
    @FXML
    private TableColumn<Cask, Double> tbcAlcoholPercentage;
    @FXML
    private TableColumn<Cask, Double> tbcAlcoholPercentage1;
    @FXML
    private TableColumn<Cask, Integer> tbcCaskID;
    @FXML
    private TableColumn<Cask, Integer> tbcCaskID1;
    @FXML
    private TableColumn<Cask, Double> tbcTotalLitersOfFills;
    @FXML
    private TableColumn<Cask, Double> tbcTotalLitersOfFills1;
    @FXML
    private TableView<Cask> tvwAvailableCasksForTransference;
    @FXML
    private TableView<Cask> tvwCasksWithDestillate;

    // ---------------------------------------------------------------------
    /** Initialize */
    // ---------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbcCaskID.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        tbcCaskID1.setCellValueFactory(new PropertyValueFactory<Cask, Integer>("caskId"));
        tbcAlcoholPercentage.setCellValueFactory(new PropertyValueFactory<Cask, Double>("TotalAlcoholPercentage"));
        tbcAlcoholPercentage1.setCellValueFactory(new PropertyValueFactory<Cask, Double>("TotalAlcoholPercentage"));
        tbcAge.setCellValueFactory(new PropertyValueFactory<Cask, TapFromDistillate>("YoungestTapFromDistillate"));
        tbcAge1.setCellValueFactory(new PropertyValueFactory<Cask, TapFromDistillate>("YoungestTapFromDistillate"));
        tbcTotalLitersOfFills.setCellValueFactory(new PropertyValueFactory<Cask, Double>("CurrentContentInLiters"));
        tbcTotalLitersOfFills1.setCellValueFactory(new PropertyValueFactory<Cask, Double>("LitersAvailable"));
        tvwCasksWithDestillate.getItems().setAll(Controller.getCasksWithDistillateOn());
    }

    // ---------------------------------------------------------------------
    /** ButtonOnAction */
    // ---------------------------------------------------------------------

    /** Shows all casks with room for transference */
    @FXML
    void btnShowAvailableCasksForTransferenceOnAction(ActionEvent event) {
        Cask selectedCask = tvwCasksWithDestillate.getSelectionModel().getSelectedItem();
        if (selectedCask == null) {
            tvwCasksWithDestillate.setStyle("-fx-border-color: red;");
            return;
        }
        double currentContentInLiters = selectedCask.getCurrentContentInLiters();
        ArrayList<Cask> availableCasksForTransference = new ArrayList<>(Controller.getCasksWithXLitersAvailable(currentContentInLiters));
        availableCasksForTransference.remove(selectedCask);
        tvwAvailableCasksForTransference.getItems().setAll(availableCasksForTransference);
    }

    /** Creates a new PutOnCask object and updates the controls */
    @FXML
    void btnTransferenceOnAction() {
        Cask oldCask = tvwCasksWithDestillate.getSelectionModel().getSelectedItem();
        Cask newCask = tvwAvailableCasksForTransference.getSelectionModel().getSelectedItem();
        if (oldCask == null) {
            tvwCasksWithDestillate.setStyle("-fx-border-color: red;");
            return;
        }
        if (newCask == null) {
            tvwAvailableCasksForTransference.setStyle("-fx-border-color: red;");
            return;
        }

        Controller.createTransference(oldCask, newCask);
        updateControls();
    }

    // ---------------------------------------------------------------------
    /** Helper methods */
    // ---------------------------------------------------------------------

    /** Updates the controls */
    public void updateControls() {
        tvwCasksWithDestillate.getItems().setAll(Controller.getCasksWithDistillateOn());
        tvwAvailableCasksForTransference.getItems().clear();
    }
}

