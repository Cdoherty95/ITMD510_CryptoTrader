package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController implements DAOInterface {


    @FXML
    private TextField Username;

    @FXML
    private Button loginBTN;

    @FXML
    private PasswordField Password;

    @FXML
    private Button exitBTN;

    @FXML
    private Label Welcome;

    @FXML
    private Label errorLable;

    @FXML
    private Button registerbtn;


    public void loginbtnClicked(ActionEvent event) throws SQLException, IOException, InterruptedException {

        if (!Username.getText().isEmpty() && !Password.getText().isEmpty()) {

            //this makes all the Active Users Inactive
            daoUsers.makeAllInactive();
            //this checks for the passwords matching
            if (daoUsers.loginPasswordMatching(Username.getText(), Password.getText())) {
                exit(event);

                new WhichUserMainMenu("user");
                //Move on to get user information
            } else { // if the Username or password is incorrect
                System.out.println("Bad Username or password");
                errorLable.setText("Username Or Password is incorrect");
            }
        } else {
            errorLable.setText("Please input Username and Password");
        }
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        System.out.println("User is regular user");
        exit(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cryptocurrency Trader");
        primaryStage.show();
    }

    public void exit(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) exitBTN.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
