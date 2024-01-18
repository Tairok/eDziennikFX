package eDziennikFX;

import Gui.GradeView;
import Gui.GuiForm;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    public TableView<GradeView> gradeTableView;
    public TableColumn<GradeView, String> subjectColumn;
    public TableColumn<GradeView, String> gradeTitleColumn;
    public TableColumn<GradeView, String> gradeColumn;
    public TableColumn<GradeView, String> dateColumn;
    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private ComboBox<String> gradeComboBox;

    @FXML
    private DatePicker datePicker;

//    @FXML
//    private TableView<GradeView> gradeTableView;

    @FXML
    private void initialize() {
        System.out.println("initialize");

//        gradeTableView = new TableView<>();

        // Initialize TableView columns
        subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());

        gradeTitleColumn = new TableColumn<>("Grade Title");
        gradeTitleColumn.setCellValueFactory(cellData -> cellData.getValue().gradeTitleProperty());

        gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());

        dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Add columns to the TableView
        gradeTableView.getColumns().addAll(subjectColumn, gradeTitleColumn, gradeColumn, dateColumn);

        // TODO Add sample data (replace this with your actual data)
        GradeView grade1 = new GradeView("Math", "Exam", "A", "2024-01-01");
        GradeView grade2 = new GradeView("Science", "Homework", "B", "2024-01-02");

        ObservableList<GradeView> gradeList = FXCollections.observableArrayList(
                grade1, grade2
        );

        gradeTableView.setItems(gradeList);

        System.out.println("end initialize");
    }

    @FXML
    private void handleAddGrade() {
        // TODO Obsługa dodawania ocen
        // Pobierz wartości z ComboBox i DatePicker, a następnie dodaj wpis do TableView
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    // Inne metody obsługujące interakcje użytkownika

    @FXML
    private void handleAddUser() {
        //TODO dodaj usera do bazy danych
    }

    @FXML
    private void handleDeleteUser() {
        //TODO usuń usera z bazy danych
    }

}