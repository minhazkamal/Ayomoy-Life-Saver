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
import java.time.LocalDate;

/** This Controller class is here to change Control the sample.Donation.fxml
 *  This class extends the LogINpanelController Class.
 *
 * @author minhaz231
 */
public class DonationInfoController extends LogINpanelController{

    /**
     * Observable list are such kinds of list where the changes can be
     * seen and data can be fetched whenever necessary
     */

    ObservableList<Donation> oblist = FXCollections.observableArrayList();
    ObservableList<String> LocList = FXCollections.observableArrayList();

    public Button []u_button;
    public Button []d_button;

    int count_req;


    /**
     * These are fxml Related Files that need to be fetching the data from the
     * user input into the fields and dropdowns
     */

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

    /**
     * Getter function of the adaptation of the date in a donation
     *
     * @return update_date
     */
    public static String getUpdate_date() {
        return update_date;
    }

    /**
     * setter function of the update date
     * not used in the system but kept for future references.
     *
     * @param update_date shows the updated date.
     */

    public static void setUpdate_date(String update_date) {
        DonationInfoController.update_date = update_date;
    }

    /**
     * This is for the functionality of the Update Button. Pressing this button
     * updates the information and overwrites int the database using DML.
     *
     * @param even which is an object of ActionEvent class
     *
     * @throws IOException
     * related to i/p and o/p error
     */
    private void pressUpdateButton(ActionEvent even) throws IOException
    {
        for(int i=0; i<count_req; i++)
        {
            if(even.getSource() == u_button[i])
            {
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

    /**
     * This function is for the functionality of the Details Button.
     *
     * This presents the user with all the values of the properties of
     * the Donation class on a separate window.
     *
     * @param even which is an object of ActionEvent class
     *
     * @throws IOException
     * related to i/p and o/p error
     */
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

    /**
     * This function is the getter function of the Locations.
     *
     * Since location is the private attribute so the getter function
     * is necessary here.
     *
     * The catch block is here to handle the exceptions and show then
     * accordingly on the title of the window.
     */
    public void getLoc()
    {
        String username = "als";
        String password;
        password = "iutcse18";
        String url;
        url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query;
        query = "SELECT NAME FROM LOCATIONS ORDER BY NAME";


        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            Statement pst = con.createStatement();

            ResultSet rs = pst.executeQuery(query);

            while(rs.next())
            {

                LocList.add(rs.getString(1));
                //System.out.println(rs.getString(1));
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
    }

    /**
     * This is the initializing function of the fxml components.
     *
     * When a the window is launched this method initializes these
     * components. Establishes the connection with the database and
     * upon fetching the data columns works with the property values.
     *
     */
    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());
        getLoc();
        location_info.setItems(LocList);
        String username;
        username = "als";
        String password;
        password = "iutcse18";
        String url;
        url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query;
        query = "SELECT ROWNUM, P_NAME, LOCATION, D_DATE FROM DONATION_INFO WHERE USERNAME=? ORDER BY D_DATE ASC";
        String count_query;
        count_query = "SELECT COUNT(*) FROM DONATION_INFO WHERE USERNAME=?";

        try{

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            pst.setString(1, txtUser.getText());
            pst1.setString(1, txtUser.getText());

            ResultSet rs1;
            rs1 = pst1.executeQuery();

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

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the user to the
     * Parent that is the "root" which is sample.fxml
     *
     * @param even
     * refers to the event of action for the method
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     */

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

    /**
     * This functionality facilitates the searching of the donation records.
     *
     * There are risk of getting null values for some most of the columns in the
     * database which are handled using the if-else blocks.
     *
     * The catch block captures the exceptions in the variable e and
     * displays the string value as the title of the window.
     *
     * @param event which is an Object of the ActionEvent
     */
    public void pressSearch(ActionEvent event) {
        oblist.clear();
        DonationInfoTable.setItems(oblist);

        LocalDate dob = null;
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT ROWNUM, P_NAME, LOCATION, D_DATE FROM DONATION_INFO WHERE USERNAME=? AND D_DATE BETWEEN ? AND ? AND LOCATION LIKE ? ORDER BY D_DATE ASC";
        String count_query = "SELECT COUNT(*) FROM DONATION_INFO WHERE USERNAME=? AND D_DATE BETWEEN ? AND ? AND LOCATION LIKE ?";
        String dob_query = "SELECT DOB FROM PERSONAL_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            PreparedStatement pst1 = con.prepareStatement(count_query);
            PreparedStatement pst2 = con.prepareStatement(dob_query);
            pst.setString(1, txtUser.getText());
            pst1.setString(1, txtUser.getText());
            pst2.setString(1, txtUser.getText());

            ResultSet rs2 = pst2.executeQuery();
            while(rs2 .next())
            {
                dob = rs2.getDate(1).toLocalDate();
            }
            rs2.close();
            pst2.close();

            if(startingDate.getValue()==null)
            {
                pst.setDate(2, Date.valueOf(dob));
                pst1.setDate(2, Date.valueOf(dob));
            }
            else
            {
                pst.setDate(2, Date.valueOf(startingDate.getValue()));
                pst1.setDate(2, Date.valueOf(startingDate.getValue()));
            }

            if(endingDate.getValue()==null)
            {
                pst.setDate(3, Date.valueOf(LocalDate.now()));
                pst1.setDate(3, Date.valueOf(LocalDate.now()));
            }
            else
            {
                pst.setDate(3, Date.valueOf(endingDate.getValue()));
                pst1.setDate(3, Date.valueOf(endingDate.getValue()));
            }

            if(location_info.getValue()==null)
            {
                pst.setString(4,"%");
                pst1.setString(4,"%");
            }
            else
            {
                pst.setString(4,location_info.getValue().toString());
                pst1.setString(4,location_info.getValue().toString());
            }

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
                int j = rs.getInt(1);
                //System.out.println(j);
                oblist.add(new Donation(/*Integer.toString(count_req-j+1)*/rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toString(),  d_button[j-1], u_button[j-1]));
            }
            rs.close();
            con.close();

        } catch (Exception e)
        {
           System.out.println(e);
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

    /**
     * Rests the Information of the donations.
     *
     * @param even which is an object of the ActionEvent Class
     *
     * @throws IOException
     * checks the i/p and o/p retalted error.
     */

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
