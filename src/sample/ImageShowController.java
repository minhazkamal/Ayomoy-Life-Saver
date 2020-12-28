package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageShowController {
    @FXML
    private ImageView imageView;


    public void initialize()
    {
        String username = "als";
        String password = "iutcse18";
        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String query = "SELECT * FROM TEST_REPORTS WHERE USERNAME=?";
//        Statement pst = null;
//        ResultSet rs = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, DonorApprovalListController.getUser_img());
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
//                System.out.println(1);
//                String a = rs.getString("NAME");
//                System.out.println(a);
                InputStream is = rs.getBinaryStream(2);
                Image image = new Image(is);
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
//                File file = new File("photo.jpg");
//                System.out.println(file.createNewFile());
//                OutputStream os = new FileOutputStream(file);
//                byte[] content = new byte[1024];
//                int size = 0;
//                while((size = is.read(content)) != -1){
//
//                    os.write(content, 0, size);
//
//                }
//
//                String path = file.getAbsolutePath();
//
//                os.close();
//
//                is.close();
//
//                Image image = new Image("file:photo.jpg");
//
//                imageView.setImage(image);
//
////                imageView.setFitWidth(100);
////
////                imageView.setFitHeight(150);
//
//                imageView.setPreserveRatio(true);

            }
            rs.close();
            con.close();



        } catch (Exception e)
        {
//            System.out.println(1);
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(String.valueOf(e));
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
}
