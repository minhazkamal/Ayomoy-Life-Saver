package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * This is the main class that extends on the Application Class.
 *
 * @author minhaz231
 */

public class Main extends Application {

    public static String text;


    @Override
    /**
     * This is the starting point of the Application.
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Ayomoy Life Saver");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.getScene().getStylesheets().add("sample/alsstyles.css");
        primaryStage.setResizable(false);
        primaryStage.show();

//        primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("Demo.fxml"));
//        primaryStage.setTitle("Ayomoy Life Saver");
//        primaryStage.setScene(new Scene(root, 1000, 600));
//        primaryStage.show();
    }


    /**
     * Main function of the program.
     * @param args, object of String Class
     */
    public static void main(String[] args) {

//        Tesseract tesseract = new Tesseract();
//        try {
//
//            tesseract.setDatapath("D:\\Ayomoy-Life-Saver\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
//
//            // the path of your tess data folder
//            // inside the extracted file
//            text = tesseract.doOCR(new File("C:\\Users\\minha\\Desktop\\Screenshot 2020-12-21 131553.png"));
//
//            // path of your image file
//            System.out.print(text);
//
//        }
//        catch (TesseractException e) {
//            e.printStackTrace();
//        }
        launch(args);

    }
}
