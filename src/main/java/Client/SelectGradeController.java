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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SelectGradeController implements Initializable {
    private ConnectionHandler handler;
    List<Object[]> subjects = null;
    List<Object[]> grades = null;

    @FXML
    private Label roleField;

    @FXML
    private Label nameField;

    @FXML
    private ListView<String> subjectsList;

    @FXML
    private ListView<String> gradesList;

    public void chosenSubjectOnAction() {
        System.out.println(Arrays.toString(subjects.get(0)));
        gradesList.getItems().clear();
        String selectedSubject = subjectsList.getSelectionModel().getSelectedItem();

        for (Object[] o : subjects) {
            if (o[1].equals(selectedSubject)) {
                String subjectsQuery = "SELECT * FROM grades_tbl WHERE fk_id_user_grades_tbl = " + User.selectedStudentID + " AND fk_id_subject_grades_tbl = " + o[0];
                subjects = (List<Object[]>) (handler.sendMessage(subjectsQuery, PacketType.QUERY).getPayload());
                for (Object[] s : subjects) {
                    if (o[1].equals(selectedSubject)) {
                        gradesList.getItems().add(new String(s[1] + " - " + s[3] + " Wystawiono: " + s[2]));
                        gradesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                            /*try {
                                chosenStudentOnAction();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }*/
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

    public void backButtonOnAction(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("selectStudent.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        //subjects = null;
        //grades = null;
        System.gc();
    }

    @FXML
    private Button logoutButton;

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientLogin.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        //subjects = null;
        //grades = null;
        System.gc();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        roleField.setText(User.role);
        nameField.setText(User.name + " " + User.surname);


        handler = ConnectionHandler.getInstance();
        String classesQuery = "SELECT * FROM subjects_tbl";
        subjects = (List<Object[]>)(handler.sendMessage(classesQuery, PacketType.QUERY).getPayload());
        for(Object[] o: subjects){
            subjectsList.getItems().add(o[1].toString());
            System.out.println(Arrays.toString(o));
            subjectsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    chosenSubjectOnAction();
                }
            });
        }

    }
}
