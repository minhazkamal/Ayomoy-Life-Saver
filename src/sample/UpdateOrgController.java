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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class UpdateOrgController extends OrganizationPanelController{

    ObservableList<String> LocList = FXCollections.observableArrayList();

    @FXML
    private TextField OrgName;
    @FXML
    private TextField ContactNo;
    @FXML
    private ChoiceBox AddressLocation;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField LicNo;
    @FXML
    private TextField ContactPerson;
    @FXML
    private TextField CPMobile;
    @FXML
    private TextArea description;

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

    public void initialize()
    {
        getLoc();
        AddressLocation.setItems(LocList);

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM ORG_INFO WHERE USERNAME=?";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, OrganizationPanelController.getUser());

            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                if(rs.getString(1).equals(LogINpanelController.getUser())) {
                    //System.out.println(rs.getString(1));
                    txtUsername.setText(rs.getString(1));
                    OrgName.setText(rs.getString(2));
                    ContactNo.setText(rs.getString(3));
                    txtEmail.setText(rs.getString(4));
                    AddressLocation.setValue(rs.getString(5));
                    LicNo.setText(rs.getString(6));
                    ContactPerson.setText(rs.getString(7));
                    CPMobile.setText(rs.getString(8));
                    description.setText(rs.getString(9));
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

    public void pressBack(ActionEvent even) throws IOException {
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public boolean checkAlert()
    {
        boolean alertCheck = false;
        if(txtEmail.getText().isBlank()||txtEmail.getText().indexOf('@')==-1)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Email Address is not correct"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(OrgName.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Organization Name Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(ContactNo.getText().length()<11||ContactNo.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(LicNo.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("License Number Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(ContactPerson.getText().isBlank())
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Contact Person Field can not be blank"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(CPMobile.getText().length()<11||CPMobile.getText().length()>11)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Emergency Mobile Number should be 11 digits"));
            alert.setResizable(false);
            alert.showAndWait();
        }
        if(description.getText().length()>250)
        {
            alertCheck = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf("Description should be in 250 characters"));
            alert.setResizable(false);
            alert.showAndWait();
        }

        return alertCheck;
    }

    public void pressUpdate(ActionEvent even) throws IOException{
        if(checkAlert()==false)
        {
            String username = "als";
            String password = "iutcse18";
            String url = "jdbc:oracle:thin:@localhost:1521/XE";
            String query = "UPDATE ORG_INFO SET USERNAME=?, NAME=?, CONTACT=?, EMAIL=?, LOCATION=?, LIC_NO=?, CONTACT_PERSON=?, CP_MOBILE=?, DESCRIPTION=? WHERE USERNAME=?";

            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(url,username,password);
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(10, txtUsername.getText());

                pst.setString(1, txtUsername.getText());
                pst.setString(2, OrgName.getText());
                pst.setString(3, ContactNo.getText());
                pst.setString(4, txtEmail.getText());
                pst.setString(5, AddressLocation.getValue().toString());
                pst.setString(6, LicNo.getText());
                pst.setString(7, ContactPerson.getText());
                pst.setString(8, CPMobile.getText());
                pst.setString(9, description.getText());

                pst.executeUpdate();

                con.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Profile Updated Successfully");
                alert.setResizable(false);
                alert.showAndWait();

                ((Node) even.getSource()).getScene().getWindow().hide();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("OrganizationPanel.fxml"));
                primaryStage.setTitle("Ayomoy Life Saver");
                primaryStage.setScene(new Scene(root, 1000, 600));
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
        else
        {
            ((Node) even.getSource()).getScene().getWindow().hide();

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("UpdateOrg.fxml"));
            primaryStage.setTitle("Ayomoy Life Saver");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void pressChangePass(ActionEvent even) throws IOException{

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.showAndWait();
    }
}
