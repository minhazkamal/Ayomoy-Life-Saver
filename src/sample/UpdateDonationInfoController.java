package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * This controller class is for updating the Donation information. The class
 * extends on the DonationInfoController Class.
 *
 * @author abdullah239
 */
public class UpdateDonationInfoController extends DonationInfoController{

    ObservableList<String> GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> LocList = FXCollections.observableArrayList();

    @FXML
    private TextField p_mobile;
    @FXML
    private TextField p_name;
    @FXML
    private TextField complications;
    @FXML
    private DatePicker d_date;
    @FXML
    private ChoiceBox p_Location;
    @FXML
    private TextArea p_locationDetails;
    @FXML
    private TextField txtUser;
    @FXML
    private ChoiceBox p_Gender_choice;

    /**
     * Method for getting the getting the location details for a donation details.
     * catch block for handling the exception due to potential null entries.
     */
    public void getLoc()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT NAME FROM LOCATIONS ORDER BY NAME";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            Statement pst = con.createStatement();

            ResultSet rs = pst.executeQuery(query);

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                LocList.add(rs.getString(1));
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
        p_Gender_choice.setItems(GenderList);
        p_Location.setItems(LocList);

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM DONATION_INFO WHERE USERNAME=? AND D_DATE=?";

//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, LogINpanelController.getUser());
            pst.setDate(2, Date.valueOf(DonationInfoController.getUpdate_date()));

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                p_Location.setValue(rs.getString(2));
                p_name.setText(rs.getString(3));
                p_Gender_choice.setValue(rs.getString(4));
                complications.setText(rs.getString(5));
                p_mobile.setText(rs.getString(6));
                d_date.setValue(rs.getDate(7).toLocalDate());
                p_locationDetails.setText(rs.getString(8));
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
    }

    /**
     * This is for the checking alert while updating the information.
     * @return boolean, returns true value if there is an alert and
     * returns false if otherwise.
     */
    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(p_mobile.getText()!=null && (p_mobile.getText().length()<11||p_mobile.getText().length()>11))
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Patient Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        if(p_locationDetails.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Location Details Should be added"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }

    /**
     * Method for the Updated donation button. Loads the sample.DonationInfo.fxml file.
     * Establishes the connection with the database and overwrites them Using DML into
     * the database.
     * @param even, object of ActionEvent class
     * @throws IOException, checked exception for i/p and o/p
     */
    public void pressUpdateDonation(ActionEvent even) throws IOException {

        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE DONATION_INFO SET LOCATION=?, P_NAME=?, P_GENDER=?, COMPLICATIONS=?, P_CONTACT=?, LOCATION_DETAILS=? WHERE USERNAME=? AND D_DATE=?";

            PreparedStatement pst = null;

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);

                pst = con.prepareStatement(query);

                pst.setString(7, txtUser.getText());
                pst.setString(1, p_Location.getValue().toString());
                pst.setString(2, p_name.getText());
                if(p_Gender_choice.getValue()==null) pst.setString(3, null);
                else pst.setString(3, p_Gender_choice.getValue().toString());
                pst.setString(4, complications.getText());
                pst.setString(5, p_mobile.getText());
                pst.setDate(8, Date.valueOf(d_date.getValue()));
                pst.setString(6, p_locationDetails.getText());

                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Information Successfully Updated");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
                primaryStage.setResizable(false);
                primaryStage.show();

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

    }

    /**
     * This method is here for the functionality of the "Back" button. This will redirect the
     * user to the parent node that is the "root" which is sample.UserPanel.fxml. This hierarchical
     * structure is for the simplicity of the system.
     *
     * @param even, object of ActionEvent generated from click or Enter
     * @throws IOException,  checked exception
     */
    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
