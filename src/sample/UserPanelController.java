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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UserPanelController extends LogINpanelController{

    @FXML
    private Label showUsername;
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

    public String FindAge(LocalDate DOB)
    {
        LocalDate current = LocalDate.now();

        Period period = Period.between(DOB, current);

        String d = Integer.toString(period.getYears())+" years "+ Integer.toString(period.getMonths())+" months and "+Integer.toString(period.getDays())+" days";

        return d;
    }

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
            pst.setString(1, LogINpanelController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(LogINpanelController.getUser())) {
                    //System.out.println(rs.getString(1));
                    showUsername.setText(rs.getString(1));
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

            alert.showAndWait();
        }
    }

    public void initialize()
    {
        showPersonalInfo();
    }

    public void PressUpdateProfile(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UserProfileUpdate.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public void PressAsDonor(ActionEvent even) throws IOException {
    }

    public void PressAsDonee(ActionEvent even) throws IOException {
    }

    public void pressBack(ActionEvent even) throws IOException {

        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}
