package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BtcHistory;
import model.DaoUpdateCurrencyHist;
import model.EthHistory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class CurrencyHistoryController implements DAOInterface {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableView<BtcHistory> btcTableView;

    @FXML
    private TableColumn<BtcHistory, Date> btcTimeCol;

    @FXML
    private TableColumn<BtcHistory, Double> btcUsdCol;

    @FXML
    private TableColumn<BtcHistory, Double> btcEthCol;

    @FXML
    private TableView<EthHistory> ethTableView;

    @FXML
    private TableColumn<EthHistory, Date> ethTimeCol;

    @FXML
    private TableColumn<EthHistory, Double> ethUsdCol;

    @FXML
    private TableColumn<EthHistory, Double> ethBtcCol;

    @FXML
    private Button refreshBtn;

    private ObservableList<BtcHistory> btcHist = FXCollections.observableArrayList();

    private ObservableList<EthHistory> ethHist = FXCollections.observableArrayList();

    DaoUpdateCurrencyHist daoUpdateCurrencyHist = new DaoUpdateCurrencyHist();

    @FXML
    void exit(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void mainMenu(ActionEvent event) throws IOException, SQLException {
        exit(event);
        new WhichUserMainMenu("user");
    }

    @FXML
    void initialize() throws SQLException {
        btcEthCol.setCellValueFactory(new PropertyValueFactory<>("ethPrice"));
        btcTimeCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        btcUsdCol.setCellValueFactory(new PropertyValueFactory<>("usdPrice"));
        ethBtcCol.setCellValueFactory(new PropertyValueFactory<>("btcPrice"));
        ethTimeCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        ethUsdCol.setCellValueFactory(new PropertyValueFactory<>("usdPrice"));
        fillBothTables();
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        new CurrencyHistoryController();
        fillBothTables();
    }

    public CurrencyHistoryController() {

    }

    public void fillBothTables() throws SQLException {
        btcTableView.getItems().clear();
        btcTableView.getItems().removeAll();
        ethTableView.getItems().clear();
        ethTableView.getItems().removeAll();
        btcHist.removeAll();
        btcHist.clear();
        ethHist.clear();
        ethHist.removeAll();
        //personData.clear();
        //personData.removeAll();

        setBtcData();
        setEthData();
        btcTableView.setItems(btcHist);
        ethTableView.setItems(ethHist);
    }

    public void setBtcData() throws SQLException {
        ResultSet rs = daoUpdateCurrencyHist.getBtcHist();
        // loop through the result set
        while (rs.next()) {
            Long DateTimeStamp = rs.getLong("TimeStamp");
            double USDHistory = rs.getDouble("USDAmt");
            double EthHistory = rs.getDouble("CryptoAmt");
            Date dt = new Date(DateTimeStamp * 1000);
            //DateFormat df = new DateFormat.format(dt);
            btcHist.add(new BtcHistory(dt, EthHistory, USDHistory));
        }
        rs.close();
    }

    public void setEthData() throws SQLException {
        ResultSet rs = daoUpdateCurrencyHist.getEthHist();
        // loop through the result set
        while (rs.next()) {
            Long DateTimeStamp = rs.getLong("TimeStamp");
            double USDHistory = rs.getDouble("USDAmt");
            double BtcHistory = rs.getDouble("CryptoAmt");
            Date dt = new Date(DateTimeStamp * 1000);
            ethHist.add(new EthHistory(dt, BtcHistory, USDHistory));
        }
        rs.close();
    }

}
