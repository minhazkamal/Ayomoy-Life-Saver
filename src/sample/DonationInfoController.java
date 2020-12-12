package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DonationInfoController extends LogINpanelController{

    ObservableList<Donation> oblist = FXCollections.observableArrayList();
    public Button []u_button;
    public Button []d_button;
    int count_req;
    @FXML
    private TextField txtUser;
    @FXML
    private TableView<Donation> DonationInfoTable;
    @FXML
    private TableColumn<Donation, String> col_serial;
    @FXML
    private TableColumn<Donation, String> col_name;
    @FXML
    private TableColumn<Donation, String> col_location;
    @FXML
    private TableColumn<Donation, String> col_date;
    @FXML
    private TableColumn<Donation, Button> col_update;
    @FXML
    private TableColumn<Donation, Button> col_details;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker endingDate;
    @FXML
    private ChoiceBox location_info;

    private static String update_date;

    public static String getUpdate_date() {
        return update_date;
    }

    public static void setUpdate_date(String update_date) {
        DonationInfoController.update_date = update_date;
    }

    private void pressUpdateButton(ActionEvent even) throws IOException
    {
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == u_button[i])
            {
//                System.out.print(i+1);
//                System.out.println(" update button is pressed");
                update_date = oblist.get(i).getDate();
                break;
            }
        }

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UpdateDonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void pressDetailsButton(ActionEvent even) throws IOException
    {
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == d_button[i])
            {
                update_date = oblist.get(i).getDate();
                break;
            }
        }
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ShowDetailDonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, P_NAME, LOCATION, D_DATE FROM DONATION_INFO WHERE USERNAME=? ORDER BY D_DATE ASC";
        String count_query = "SELECT COUNT(*) FROM DONATION_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            pst.setString(1, txtUser.getText());
            pst1.setString(1, txtUser.getText());

            ResultSet rs1 = pst1.executeQuery();
            while(rs1.next())
            {
                count_req = rs1.getInt(1);
            }
            rs1.close();
            pst1.close();

            u_button = new Button[count_req];
            d_button = new Button[count_req];

            for(int i=0; i<count_req; i++)
            {
                u_button[i] = new Button();
                u_button[i].setOnAction(even -> {
                    try {
                        pressUpdateButton(even);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                d_button[i] = new Button();
                d_button[i].setOnAction(even -> {
                    try {
                        pressDetailsButton(even);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                int j = rs.getInt(1);
                //System.out.println(j);
                oblist.add(new Donation(/*Integer.toString(count_req-j+1)*/rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toString(),  d_button[j-1], u_button[j-1]));
            }
            rs.close();
            con.close();

        } catch (Exception e)
        {
//           System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        col_serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("loc"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_details.setCellValueFactory(new PropertyValueFactory<>("details_button"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update_button"));


        DonationInfoTable.setItems(oblist);
    }

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonorPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressNewDonation(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddNewDonation.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void pressSearch(ActionEvent actionEvent) {

    }

    public void pressReset(ActionEvent even) throws IOException{
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
