package sample;

import javafx.scene.control.TextArea;

public class DemoController{
    public TextArea txt;

    public void initialize()
    {
        txt.setText(Main.text);
        System.out.print("Ayomoy Life Saver");
    }

}
