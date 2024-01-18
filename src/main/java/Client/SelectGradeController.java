package Client;

import GuiTools.User;

import NetworkTools.PacketType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for selecting grades in the client application.
 */
public class SelectGradeController implements Initializable {
    private static final Logger logger = LogManager.getLogger(SelectGradeController.class);

    private ConnectionHandler handler;
    private List<Object[]> subjects = null;
    private List<Object[]> grades = null;

    @FXML
    private Label roleField;

    @FXML
    private Label nameField;

    @FXML
    private ListView<String> subjectsList;

    @FXML
    private ListView<String> gradesList;

    /**
     * Handles the action when a subject is chosen.
     */
    public void chosenSubjectOnAction() {
        logger.info("Selected subject: {}", Arrays.toString(subjects.get(0)));
        gradesList.getItems().clear();
        String selectedSubject = subjectsList.getSelectionModel().getSelectedItem();

        for (Object[] o : subjects) {
            if (o[1].equals(selectedSubject)) {
                String subjectsQuery = "SELECT * FROM grades_tbl WHERE fk_id_user_grades_tbl = " +
                        User.selectedStudentID + " AND fk_id_subject_grades_tbl = " + o[0];
                subjects = (List<Object[]>) (handler.sendMessage(subjectsQuery, PacketType.QUERY).getPayload());
                for (Object[] s : subjects) {
                    if (o[1].equals(selectedSubject)) {
                        gradesList.getItems().add(new String(s[1] + " - " + s[3] + " Wystawiono: " + s[2]));
                        gradesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                                // Action on item selection change (commented out).
                            }
                        });
                    }
                    break;
                }
            }
        }
    }

    @FXML
    private Button backButton;

    /**
     * Handles the action when the back button is clicked.
     */
    public void backButtonOnAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("selectStudent.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        logger.info("Navigated back to selectStudent.fxml");
        System.gc();
    }

    @FXML
    private Button logoutButton;

    /**
     * Handles the action when the logout button is clicked.
     */
    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientLogin.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        logger.info("Logged out. Navigated to clientLogin.fxml");
        System.gc();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleField.setText(User.role);
        nameField.setText(User.name + " " + User.surname);

        handler = ConnectionHandler.getInstance();
        String classesQuery = "SELECT * FROM subjects_tbl";
        subjects = (List<Object[]>) (handler.sendMessage(classesQuery, PacketType.QUERY).getPayload());
        for (Object[] o : subjects) {
            subjectsList.getItems().add(o[1].toString());
            logger.debug("Added subject to the list: {}", Arrays.toString(o));
            subjectsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    chosenSubjectOnAction();
                }
            });
        }
    }
}
