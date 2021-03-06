package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class WhichUserMainMenu implements DAOInterface {


    public WhichUserMainMenu(String uOa) throws IOException, SQLException {
        figureOutActiveUser(uOa);
    }

    private void figureOutActiveUser(String uOa) throws IOException, SQLException {
        if (uOa.equals("admin")) {
            System.out.println("User is an admin");
            //exit(event);
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminMain.fxml"));
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource("/view/UsersTableView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cryptocurrency Trader");
            primaryStage.show();
        } else {
            System.out.println("User is regular user");
            //exit(event);
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserMain.fxml"));
            Parent root = loader.load();

            String[] userRole = daoUsers.activeUserInfo();
            if (userRole[2].equals("admin")) {
                UserMenuController controller = loader.getController();
                controller.disableAdminBtn(true);
            } else {
                UserMenuController controller = loader.getController();
                controller.disableAdminBtn(false);
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cryptocurrency Trader");

            primaryStage.show();
        }
    }

}
