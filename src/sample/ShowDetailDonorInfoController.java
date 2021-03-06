package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * This controller class handles the Donor details. Extends the  SearchDonorOrgController class.
 *
 * @author minhaz231
 */
public class ShowDetailDonorInfoController extends SearchDonorOrgController{

    @FXML
    private Label showName;
    @FXML
    private Label showMobileNo;
    @FXML
    private Label showEmail;
    @FXML
    private Label showDOB;
    @FXML
    private Label showAge;
    @FXML
    private Label showLocation;
    @FXML
    private Label showBG;
    @FXML
    private Label showGender;
    @FXML
    private Label showLastDonationDate;
    @FXML
    private Label showLastDonationArea;
    @FXML
    private Label showLastDonationOrg;

    /**
     * Finds the age of the Donor.
     *
     * @param DOB, the date of birth of user.
     * @return String, the age of User.
     */
    public String FindAge(LocalDate DOB)
    {
        LocalDate current = LocalDate.now();

        Period period = Period.between(DOB, current);

        String d = Integer.toString(period.getYears())+" years "+ Integer.toString(period.getMonths())+" months and "+Integer.toString(period.getDays())+" days";

        return d;
    }

    /**
     * Method for showing personal information of the Donor. Fetches the information from
     * the database. Catches exception into the catch block and shows the error on the
     * title of the window.
     */
    public void showPersonalInfo()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM PERSONAL_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, SearchDonorOrgController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(SearchDonorOrgController.getUser())) {
                    //System.out.println(rs.getString(1));
                    showName.setText(rs.getString(2));
                    showMobileNo.setText(rs.getString(3));
                    showEmail.setText(rs.getString(4));
                    showDOB.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(rs.getDate(5).toLocalDate()));
                    //showDOB.setText(rs.getDate(5).toLocalDate().format("dd-MM-yyyy").);
                    showLocation.setText(rs.getString(6));
                    showGender.setText(rs.getString(7));
                    showBG.setText(rs.getString(8));
                    showAge.setText(FindAge(rs.getDate(5).toLocalDate()));
                    rs.close();
                    con.close();

                    return;
                }
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
            System.out.println(e);
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    /**
     * Method for showing the donation information fetched from the database.
     */
    public void showDonationInfo()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT D_DATE, LOCATION, LOCATION_DETAILS FROM DONATION_INFO WHERE USERNAME=? AND ROWNUM=1 ORDER BY D_DATE DESC";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, SearchDonorOrgController.getUser());
            //System.out.println(SearchDonorOrgController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                showLastDonationDate.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(rs.getDate(1).toLocalDate()));
                showLastDonationArea.setText(rs.getString(2));
                showLastDonationOrg.setText(rs.getString(3));
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
            System.out.println(e);
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
        showPersonalInfo();
        showDonationInfo();
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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("SearchDonor-Org.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
