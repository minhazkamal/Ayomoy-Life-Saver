package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.sql.*;
import java.time.LocalDate;

public class FilterDonationController {

    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    ObservableList<String> LocList = FXCollections.observableArrayList();
    ObservableList<String> Pay_Cond = FXCollections.observableArrayList("Any", "Paid", "NON-Paid");

    public static String bg;
    public static String loct;
    public static String pay;
    public static int countf;

    public static int getCountf() {
        return countf;
    }

    public static String getBg() {
        return bg;
    }

    public static String getLoct() {
        return loct;
    }

    public static String getPay() {
        return pay;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static boolean flag = false;

    @FXML
    private ChoiceBox filterBG;
    @FXML
    private ChoiceBox filterLocation;
    @FXML
    private ChoiceBox filterPay;

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
        filterBG.setItems(BGList);
        filterLocation.setItems(LocList);
        filterPay.setItems(Pay_Cond);
    }

    public void pressSearchFilter(ActionEvent even) {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        //String query = "SELECT BLOOD_GROUP, DOB FROM PERSONAL_INFO WHERE USERNAME=?";
        String count_query = "SELECT COUNT(*) FROM DONOR_INFO, PERSONAL_INFO WHERE DONOR_INFO.USERNAME=PERSONAL_INFO.USERNAME AND BLOOD_GROUP LIKE ? AND LOCATION LIKE ? AND PAYABLE LIKE ?";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement pst1 = con.prepareStatement(count_query);

            if(filterBG.getValue()==null)
            {
                pst1.setString(1,"%");
                bg = "%";
            }
            else
            {
                pst1.setString(1, filterBG.getValue().toString());
                bg = filterBG.getValue().toString();
            }

            if(filterLocation.getValue()==null)
            {
                pst1.setString(2,"%");
                loct = "%";
            }
            else
            {
                pst1.setString(2,filterLocation.getValue().toString());
                loct = filterLocation.getValue().toString();
            }

            if(filterPay.getValue()==null || filterPay.getValue().toString().compareTo("Any")==0)
            {
                pst1.setString(3, "%");
                pay = "%";
            }
            else
            {
                pst1.setString(3, filterPay.getValue().toString());
                pay = filterPay.getValue().toString();
            }

            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                countf = rs1.getInt(1);
                //System.out.println(count_req);
            }
            rs1.close();
            con.close();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }

        flag=true;
        ((Node) even.getSource()).getScene().getWindow().hide();

    }

    public void pressCancelFilter(ActionEvent even) {
        ((Node) even.getSource()).getScene().getWindow().hide();
    }
}
