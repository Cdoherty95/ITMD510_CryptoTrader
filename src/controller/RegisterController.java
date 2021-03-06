package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController implements DAOInterface {

    /*
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    */

    @FXML
    private TextField usernameIn;

    @FXML
    private PasswordField pass1In;

    @FXML
    private PasswordField pass2In;

    @FXML
    private TextField fNameIn;

    @FXML
    private TextField lNameIn;

    @FXML
    private TextField emIn;

    @FXML
    private TextField payNameIn;

    @FXML
    private TextField accountIn;

    @FXML
    private TextField routingIn;

    @FXML
    private Button registerBtn;

    @FXML
    private ToggleButton regAsAdmin;

    @FXML
    private Label errorLabel;

    @FXML
    private Button cancelBtn;

    private boolean checkBankInfo() {
        try {
            Integer.parseInt(accountIn.getText());
        } catch (NumberFormatException e) {
            System.out.println("acc is not int");
            return false;
        }
        try {
            Integer.parseInt(routingIn.getText());
        } catch (NumberFormatException e) {
            System.out.println("routing is not int");
            return false;
        }
        return true;
    }

    private boolean seeIfUsernameExists(String userName) throws SQLException, InterruptedException {
        return daoUsers.getUserID(userName) == 0;
    }

    @FXML
    void Register(ActionEvent event) throws SQLException, InterruptedException, IOException {


        //check to make sure user entered the same password
        if (pass1In.getText().equals(pass2In.getText())) {
            if (checkBankInfo()) {//ints are ints
                if (seeIfUsernameExists(usernameIn.getText())) { //see if username exists
                    if (regAsAdmin.isSelected()) { //Check if admin is selected
                        daoUsers.createUser(usernameIn.getText(), pass1In.getText(), "admin");
                        int uid = daoUsers.getUserID(usernameIn.getText());
                        daoUsers.registerUser(uid, fNameIn.getText(), lNameIn.getText(), payNameIn.getText(), Integer.parseInt(accountIn.getText()),
                                Integer.parseInt(routingIn.getText()), emIn.getText());
                        cancel(event);
                    } else {//user is regular user
                        daoUsers.createUser(usernameIn.getText(), pass1In.getText(), "user");
                        int uid = daoUsers.getUserID(usernameIn.getText());
                        daoUsers.registerUser(uid, fNameIn.getText(), lNameIn.getText(), payNameIn.getText(), Integer.parseInt(accountIn.getText()),
                                Integer.parseInt(routingIn.getText()), emIn.getText());
                        cancel(event);
                    }
                } else {
                    usernameIn.clear();
                    errorLabel.setText("Username Exists");
                }
            } else {//account or routing numbers were incorrect
                accountIn.clear();
                routingIn.clear();
                errorLabel.setText("Bank Information Must be numbers only");
            }
        } else { //passwords didnt match
            pass1In.clear();
            pass2In.clear();
            errorLabel.setText("Passwords Did Not Match");
        }
    }

    @FXML
    void verifyPasswordMatches(InputMethodEvent event) {
        System.out.println("entered verify");

    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        exit(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cryptocurrency Trader");
        primaryStage.show();
    }

    public void exit(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void initialize() {
        assert usernameIn != null : "fx:id=\"usernameIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert pass1In != null : "fx:id=\"pass1In\" was not injected: check your FXML file 'Register.fxml'.";
        assert pass2In != null : "fx:id=\"pass2In\" was not injected: check your FXML file 'Register.fxml'.";
        assert fNameIn != null : "fx:id=\"fNameIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert lNameIn != null : "fx:id=\"lNameIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert emIn != null : "fx:id=\"emIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert payNameIn != null : "fx:id=\"payNameIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert accountIn != null : "fx:id=\"accountIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert routingIn != null : "fx:id=\"routingIn\" was not injected: check your FXML file 'Register.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'Register.fxml'.";
        assert regAsAdmin != null : "fx:id=\"regAsAdmin\" was not injected: check your FXML file 'Register.fxml'.";

    }
}
