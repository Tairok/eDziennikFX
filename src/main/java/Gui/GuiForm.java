package Gui;



import GuiTools.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Gui for login form
 */
public class GuiForm {

    public GuiForm(Stage primaryStage)
    {
        primaryStage.setTitle("eDziennik - logowanie");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    if(User.checkUser(usernameInput.getText(), passwordInput.getText()))
                    {
                        System.out.println("Login successful");

                        setStage(usernameInput.getText());

                        // Hide this current window (if this is what you want)
                        ((Node)(e.getSource())).getScene().getWindow().hide();
                    }
                    else System.out.println("Login failed");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    private void setStage(String username) throws SQLException, ClassNotFoundException, IOException {
        // GET roleName of USER from database

        //testing roleName
//        String roleName = "admin";
//        String roleName = "teacher";
//        String roleName = "student";
        String title = null, fxmlFilename = null;

        switch (username) {
            case "a" -> {
                title = "Panel Administratora";
                fxmlFilename = "/eDziennikFX/panelAdmin.fxml";
            }
            case "t" -> {
                title = "Panel Nauczyciela";
                fxmlFilename = "/eDziennikFX/panelTeacher.fxml";
            }
            case "s" -> {
                title = "Panel Ucznia";
                fxmlFilename = "/eDziennikFX/panelStudent.fxml";
            }

            default -> throw new IllegalStateException("Unexpected value: " + username);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilename));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Handle log in session, check if data exists in db
     * @param username
     * @param password
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void handleLogin(String username, String password) throws SQLException, ClassNotFoundException {

        if(User.checkUser(username, password))
        {
            System.out.println("Login successful");
            //add action

        }
        else System.out.println("Login failed");


    }
}

