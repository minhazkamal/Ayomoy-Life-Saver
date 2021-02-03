package sample;

import javafx.scene.control.TextArea;
/**
 * This is a demo Comtroll Class, made for the purpose of testing and debuging purposes
 *
 * @author abdullah239
 */


public class DemoController{
    public TextArea txt;

    /**
     * Demo Initializaer for the fxml
     */
    public void initialize()
    {
        txt.setText(Main.text);
        System.out.print("Ayomoy Life Saver");
    }

}
