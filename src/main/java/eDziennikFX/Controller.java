package eDziennikFX;

import Gui.GradeView;
import Gui.GuiForm;
import Gui.UserView;
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

    public TableView<UserView> userTableView;
    public TableColumn<UserView, String> idColumnAdmin;
    public TableColumn<UserView, String> firstNameColumnAdmin;
    public TableColumn<UserView, String> lastNameColumnAdmin;
    public TableColumn<UserView, String> loginColumnAdmin;
    public TableColumn<UserView, String> roleColumnAdmin;
    @FXML
    public TextField firstNameFieldAdmin;
    public TextField lastNameFieldAdmin;
    public TextField loginFieldAdmin;
    public PasswordField passwordFieldAdmin;
    public ComboBox<String> roleComboBoxAdmin;
    public ComboBox<String> loginComboBoxAdmin;


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
//        initializeStudent();
        initializeTeacher();
//        initializeAdmin();
    }

    private void initializeAdmin() {
        System.out.println("initialize admin");
//TODO opcjonalnie pobieraj role z bazy danych
        roleComboBoxAdmin.getItems().addAll("admin", "teacher", "student");
        //TODO pobieraj loginy userów z bazy danych
        loginComboBoxAdmin.getItems().addAll("admin", "T-szer", "invincible");

        idColumnAdmin = new TableColumn<>("ID");
        idColumnAdmin.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        firstNameColumnAdmin = new TableColumn<>("First Name");
        firstNameColumnAdmin.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());

        lastNameColumnAdmin = new TableColumn<>("Last Name");
        lastNameColumnAdmin.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        loginColumnAdmin = new TableColumn<>("Login");
        loginColumnAdmin.setCellValueFactory(cellData -> cellData.getValue().loginProperty());

        roleColumnAdmin = new TableColumn<>("Role");
        roleColumnAdmin.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        userTableView.getColumns().addAll(idColumnAdmin, firstNameColumnAdmin, lastNameColumnAdmin, loginColumnAdmin, roleColumnAdmin);

        // TODO Pobierz userów z bazy danych
        UserView user1 = new UserView("1", "Admin", "Adminson", "admin", "admin");
        UserView user2 = new UserView("2", "Teacher", "Teacherson", "T-czer", "teacher");
        UserView user3 = new UserView("3", "Mark", "Greyson", "invincible", "student");

        ObservableList<UserView> userList = FXCollections.observableArrayList(
                user1, user2, user3
        );

        userTableView.setItems(userList);

        System.out.println("end initialize admin");
    }


    private void initializeStudent() {
        System.out.println("initialize student");

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

        // TODO Pobierz oceny z bazy danych
        GradeView grade1 = new GradeView("Math", "Exam", "A", "2024-01-01");
        GradeView grade2 = new GradeView("Science", "Homework", "B", "2024-01-02");

        ObservableList<GradeView> gradeList = FXCollections.observableArrayList(
                grade1, grade2
        );

        gradeTableView.setItems(gradeList);

        System.out.println("end initialize student");
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
        String firstName = firstNameFieldAdmin.getText();
        String lastName  = lastNameFieldAdmin.getText();
        String username  = loginFieldAdmin.getText();
        String password  = passwordFieldAdmin.getText();
        String role      = roleComboBoxAdmin.getValue();

        System.out.println("Dodaję użyszkodnika: " + firstName + ", " + lastName + ", " + username + ", " + role);
    }

    @FXML
    private void handleDeleteUser() {
        String username = loginComboBoxAdmin.getValue();
        System.out.println("Usuwam użyszkodnika: " + username);
        //TODO usuń usera z bazy danych
    }

}