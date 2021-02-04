package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 *  This is the controller class for managing the Filter requests.
 *
 *  Lot of filters are predefined here in the class in lists to avoid invalid queries
 *  into the database.
 *
 * @author minhaz231
 * @param <bg>
 */
public class FilterRequestController<bg> extends PendingRequestController{

    ObservableList<String> BGList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    ObservableList<String> LocList = FXCollections.observableArrayList();

    /**
     * FXML related Variables, fecthes the data from the UI
     */
    @FXML
    private ChoiceBox filterBG;
    @FXML
    private ChoiceBox filterLocation;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker endingDate;

    private LocalDate dob;

    public static String bg;
    public static String loct;
    public static boolean flag = false;

    /**
     * Getter function of Location, static
     * @return String, which is the location
     */
    public static String getLoct() {
        return loct;
    }

    public static LocalDate std;
    /**
     * Getter function of Blood Group, static
     * @return String, Which is the Blood Group
     */
    public static String getBg() {
        return bg;
    }
    /**
     * Getter function of Local Date, static
     * @return LocalDate, which is the starting date of the Filter imposed
     */
    public static LocalDate getStd() {
        return std;
    }
    /**
     * Getter function of Local Date, static
     * @return LocalDate,  which is the ending of the date of the Filter Imposed
     */
    public static LocalDate getEnd_d() {
        return end;
    }
    /**
     * Getter function of count, static
     * @return int, which is the frequency of the count
     */
    public static int getCountf() {
        return countf;
    }

    public static LocalDate end;
    public static int countf;
    /**
     * This method fecthes the Locaiton details for a request from the
     * database directly through queries.
     *
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
        getLoc();
        filterBG.setItems(BGList);
        filterLocation.setItems(LocList);
        //filterBG.setValue(PendingRequestController.getBg());
        startingDate.setValue(LocalDate.now());
    }
    /**
     * This is for Imposing the search filters.
     *
     * The method formulates a proper query with the constraints and searches the
     * database for the results. If else blocks are used for handling the null fields.
     *
     * @param even, Object of ActionEvent Class
     * @throws IOException, checked exception for i/p and o/p
     */
    public void pressSearchFilter(ActionEvent even) throws IOException {

        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT BLOOD_GROUP, DOB FROM PERSONAL_INFO WHERE USERNAME=?";
        String count_query = "SELECT COUNT(*) FROM REQUEST WHERE APPROX_DATE>=? AND APPROX_DATE<=? AND BLOOD_GROUP LIKE ? AND LOCATION LIKE ?";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst2 = con.prepareStatement(query);
            pst2.setString(1, DonorPanelController.getUser());
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()) {
                bg = rs2.getString(1);
                dob = rs2.getDate(2).toLocalDate();
                //System.out.println(count_req);
            }
            rs2.close();

            PreparedStatement pst1 = con.prepareStatement(count_query);

            pst1.setDate(1, Date.valueOf(LocalDate.now()));
            std = LocalDate.now();

            if(endingDate.getValue()==null)
            {
                pst1.setDate(2, Date.valueOf(LocalDate.now().plusYears(1)));
                end = LocalDate.now().plusYears(1);
            }
            else
            {
                pst1.setDate(2, Date.valueOf(endingDate.getValue()));
                end = endingDate.getValue();
            }

            if(filterBG.getValue()==null)
            {
                pst1.setString(3, "%");
                //bg = PendingRequestController.getBg();
                bg = "%";
            }
            else
            {
                pst1.setString(3, filterBG.getValue().toString());
                bg = filterBG.getValue().toString();
            }


            if(filterLocation.getValue()==null)
            {
                pst1.setString(4,"%");
                loct = "%";
            }
            else
            {
                pst1.setString(4,filterLocation.getValue().toString());
                loct = filterLocation.getValue().toString();
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

    /**
     * Method fo clearing all the imposed filters on the list.
     *
     * @param even, Object of ActionEvent
     */
    public void pressCancelFilter(ActionEvent even) {
        ((Node) even.getSource()).getScene().getWindow().hide();
    }
}

