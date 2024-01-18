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
import java.util.concurrent.atomic.AtomicReferenceArray;

public class SelectStudentController implements Initializable {
    private ConnectionHandler handler;
    List<Object[]> classes = null;
    List<Object[]> students = null;

    @FXML
    private Label roleField;

    @FXML
    private Label nameField;

    @FXML
    private Button logoutButton;

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        classes = null;
        students = null;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientLogin.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        System.gc();
    }

    @FXML
    private ListView<String> classesList;

    @FXML
    private ListView<String> studentsList;

    public void chosenClassOnAction() {
        studentsList.getItems().clear();
        String selectedClass = classesList.getSelectionModel().getSelectedItem();
        for(Object[] o: classes){
            if(o[1].equals(selectedClass)){
                String studentsQuery = "SELECT * FROM users_tbl WHERE role = 'uczen' AND fk_id_class_user_tbl = " + o[0];
                students = (List<Object[]>)(handler.sendMessage(studentsQuery, PacketType.QUERY).getPayload());
                for(Object[] s: students){
                    studentsList.getItems().add(new String(s[1].toString() + " " + s[2].toString()));
                    studentsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                            try {
                                chosenStudentOnAction();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
                break;
            }
        }

    }

    public void chosenStudentOnAction() throws IOException{
        String selectedStudent = studentsList.getSelectionModel().getSelectedItem(); //złapać ID wybranego studenta i ustawić..
        System.out.println(selectedStudent);
        //selectedStudent = null;

        for(Object[] o : students){
            if(selectedStudent.equals(o[1] + " " + o[2]))
                User.selectedStudentID = Integer.parseInt(o[0].toString());
        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("SelectGrade.fxml"));
        Stage stage = (Stage) studentsList.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        roleField.setText(User.role);
        nameField.setText(User.name + " " + User.surname);


        handler = ConnectionHandler.getInstance();
        String classesQuery = "SELECT * FROM classes_tbl";
        classes = (List<Object[]>)(handler.sendMessage(classesQuery, PacketType.QUERY).getPayload());
        for(Object[] o: classes){
            classesList.getItems().add(o[1].toString());
            classesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    chosenClassOnAction();
                }
            });
        }
    }
}
