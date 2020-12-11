package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sampleController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView imView;

    @FXML
    private Button button;

    @FXML
    public void initialize(){
        imView.setImage(
                new Image("images/cover.jpg")
        );

//        imView.setImage(
//                new Image("images/cover.jpg")
//        );
//        root.heightProperty().addListener(
//                h -> imView.setFitHeight(
//                        root.getHeight()
//                )
//        );
//        root.widthProperty().addListener(
//                h -> imView.setFitWidth(
//                        root.getWidth()
//                )
//        );
    }
    @FXML
    public void pressToContinue(ActionEvent even) throws IOException
    {
        //System.out.println("Hello World!!!");
        ((Node) even.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogINpanel.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
