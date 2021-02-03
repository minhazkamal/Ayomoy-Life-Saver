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
import java.time.LocalDate;
import java.time.Period;

/**
 * This is a Class for handling the adding new donation. This is a Controller
 * Class that handles the sample.AddNewDonation.fxml. This Adding new donation
 * is particularly for a feature for the donor class of user. They can add new
 * donation by providing the necessary information. This fxml file has the
 * text-fields and the dropdown boxes. They will be able to queue a donation in
 * the mother list upon filling this form. This particular class extends the
 * LogINpanelController that was used to handle the sample.LogIN.fxml.
 *
 * @author minhaz231
 */

public class AddNewDonationController extends LogINpanelController {

    /**
     * This is for the selection of Gender from the Dropdown menu.
     * Observable List is from JavaFX. It is a list that enables
     * listeners to track changes when they occur.
     */
    ObservableList<String> GenderList;

    {
        GenderList = FXCollections.observableArrayList("Male", "Female", "Others");
    }

    /**
     * This is for the selection of location from the location list.
     * A list is prebuilt into the system already. User must is made
     * to select from one of this list.
     *
     * Here an Observable list is used. Observable List is from JavaFX.
     * It is a list that enables listeners to track changes when they occur.
     *
     */
    ObservableList<String> LocList;

    {
        LocList = FXCollections.observableArrayList();
    }

    @FXML
    /*
     * This is to store the mobile number of the Donor,
     * Made Private for the Privacy issues
     */
    private TextField p_mobile;
    @FXML
    /*
     * This is to store the name of the Donor,
     * Made Private for the Privacy issues
     */
    private TextField p_name;
    @FXML
    /*
     * This is to store the complications of the Donor,
     * Made Private for the Privacy issues because this may
     * contain some medical conditions related to the Donor
     */
    private TextField complications;
    @FXML
    /*
     * This is to store the date of the donation that may take place,
     * Made Private for the Privacy issues
     */
    private DatePicker d_date;
    @FXML
    /*
     * This is to store the mobile number of the Donor,
     * Made Private for the Privacy issues
     */
    private ChoiceBox p_Location;
    @FXML
    /*
     * This is to store the location details of the Donor,
     * Made Private for the Privacy issues. This will act as
     * the point of contact for the donation to occur between
     * the respective two parties. This details adds a level of
     * finer granularity to the information.
     */
    private TextArea p_locationDetails;
    @FXML
    /*
     * This is to store the name of textUser,
     * Made Private for the Privacy issues
     */
    private TextField txtUser;
    @FXML
    /*
     * This is to store the gender choice of the Donor,
     * Made Private for the Privacy issues
     */
    private ChoiceBox p_Gender_choice;

    /**
     * This is a method to get the Locations that are stored in the database.
     * The methods is interacting with the database with the
     * hardcoded credentials and using an SQL Query to
     * extract the relevant information that are needed
     *
     * Here a catch block is used to handle the exceptions
     * and prevent the program from crashing. These exceptions
     * are caught in the variable e. In case of any exception
     * there is a dialogue box that will appear and it will
     * Show the appropriate string value that is extracted from the
     * e variable and this will be set as the title of the dialogue box.
     *
     * Another good practice is maintained in this method that is the method
     * is closing the connection rather then waiting for
     * the garbage collector to close it. This is saving
     * the program from creating any additional inconsistency
     * in the stored data. The value in the resizability method is
     * set false so that distortion in the UI is not possible at all.
     * 
     */

    public void getLoc()
    {
        String username;
        username = "als";
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
            }
            rs.close();
            con.close();
        } catch (Exception e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    /**
     *
     * This is a method without any parameter. The purpose of this is to
     * initialize and make way for the Observalelists to begin working.
     * here the items are set accordingly to the Lists
     *
     */
    public void initialize()
    {
        txtUser.setText(LogINpanelController.getUser());
        getLoc();
        p_Gender_choice.setItems(GenderList);
        p_Location.setItems(LocList);
    }

    /**
     *
     * This methods is here to check the validity of the date of new donation
     *
     * Here the Date of Birth of the User  and donation date are fetched from
     * the database and the result set is compared. For an invalid date this
     * iterative variable will give 0 value and return false. Also It is checking
     * Whether the user is less than 18 years old and this also will return a false
     * result. The donation date keeps track of whether the donor is donating more than
     * once within the span of 3 months.
     *
     * Here a catch block is used to handle the exceptions
     * and prevent the program from crashing. These exceptions
     * are caught in the variable e. In case of any exception
     * there is a dialogue box that will appear and it will
     * Show the appropriate string value that is extracted from the
     * e variable and this will be set as the title of the dialogue box.
     *
     * @return boolean
     * which returns true for a valid date and
     * false for an invalid date and invalid age of the user.
     * True is return for the opposite case.
     *
     */

    public boolean checkDate()
    {
        int i=0, flag=0;
        LocalDate dob = null;
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT D_DATE FROM DONATION_INFO WHERE USERNAME=? AND D_DATE BETWEEN ADD_MONTHS(?, -3) AND ADD_MONTHS(?, 3)";
        String dob_query = "SELECT DOB FROM PERSONAL_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            PreparedStatement pst1 = con.prepareStatement(dob_query);

            pst1.setString(1, txtUser.getText());
            pst.setString(1, txtUser.getText());
            pst.setDate(2, Date.valueOf(d_date.getValue()));
            pst.setDate(3, Date.valueOf(d_date.getValue()));

            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next())
                dob = rs1.getDate(1).toLocalDate();

            rs1.close();
            pst1.close();

            LocalDate current;
            current = d_date.getValue();

            Period period = Period.between(dob, current);


            if(period.getYears()<18) flag=1;

            ResultSet rs = pst.executeQuery();


            while(rs.next())
            {
               i++;
            }
            rs.close();
            con.close();
        } catch (Exception e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        if(i==0 && flag==0) return true;
        else return false;
    }

    /**
     *
     * This functions is for checking all the errors in the add new donations porcess.
     *
     * The function is here to show alert on three scenarios.
     * First of all it checks the text field of the mobile number.
     * If the mobile number is not given or blank than it will show error.
     * But for a given mobile number it wil show alert because of the digits less
     * than the number of 11. ALso it will check for the valid characters inserted.
     *
     * Then, using the previous checkDate method it will check the validity
     * of the date and alert accordingly.
     *
     * The location details supports no granularity check rather it sees
     * whether any details is given or not and alerts the user Otherwise.
     *
     * @return boolean
     * which depends on the value of alert check
     *
     */

    public boolean checkAlert()
    {
        boolean alertCheck = false;

        if(p_mobile.getText().isBlank()==false && (p_mobile.getText().length()<11||p_mobile.getText().length()>11))
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Patient Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }


        if(checkDate()==false)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Donation date is invalid. Because you have donation in last/next 3 months."));
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
     *
     * This method is here for the functionality of the submit button, A successful
     * submission will record the data into the database.
     *
     * The method establishes connection the database and accordingly goes for the
     * the Data Manipulation to write into the database. Now the inserted data are
     * in the PreparedStatement object to write into the database. If the gender given
     * is null the system will choose the gender of the user automatically.
     *
     * After a pressing the button any sort of anomalies will raise an alert. But
     * for a proper scenario it will show a confirmation dialogue box to the user
     * and upon passing "okay" the data will be written. In the catch block it is
     * capturing any sort of exceptions that may occur and showing the string value
     * of that on the warning stage.
     *
     * @param even
     * which is ActionEvent object.
     *
     * @throws IOException
     * That is a checked exception. Shows any exceptions that occur
     * due to the i/p and o/p of the total flow.
     *
     * @throws NullPointerException
     * it is thrown when an application attempts to use an object reference that has the
     * null value. This is a RunTimeException and handling it is very toilsome.
     *
     */


    public void pressSubmitDonation(ActionEvent even) throws IOException, NullPointerException{
        if(checkAlert()==false)
        {
            String username;
            username = "als";
            String password;
            password = "iutcse18";
            String url;
            url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query;
            query = "INSERT INTO DONATION_INFO VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst;
            pst = null;

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url, username, password);

                pst = con.prepareStatement(query);
                pst.setString(1, txtUser.getText());
                pst.setString(2, p_Location.getValue().toString());
                pst.setString(3, p_name.getText());

                if(p_Gender_choice.getValue()==null) pst.setString(4, null);
                else pst.setString(4, p_Gender_choice.getValue().toString());

                pst.setString(5, complications.getText());
                pst.setString(6, p_mobile.getText());
                pst.setDate(7, Date.valueOf(d_date.getValue()));
                pst.setString(8, p_locationDetails.getText());

                pst.executeUpdate();

                pst.close();

                if(LocalDate.now().compareTo(d_date.getValue().plusMonths(3))<=0 && LogINpanelController.getType().equals("Person"))
                {
                    String query1 = "UPDATE DONOR_INFO SET ELIGIBILITY='Ineligible' WHERE USERNAME=?";


                    PreparedStatement pst1 = con.prepareStatement(query1);
                    pst1.setString(1, LogINpanelController.getUser());

                    pst1.executeUpdate();

                    pst1.close();
                }

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Request Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Information Successfully Received");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
                primaryStage.setResizable(false);
                primaryStage.show();

            } catch (Exception e)
            {

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
     * This method is here for the functionality of the "Back" button. This will redirect the user to the
     * Parent that is the "root" which is sample.fxml
     *
     * @throws IOException
     * which is a checked exception.
     * Used to identify errors in i/p and o/p of a particular workflow.
     *
     */

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DonationInfo.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
