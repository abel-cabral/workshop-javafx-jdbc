package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.text.ParseException;

public class Utils {
    // Devolve o cenário atual
    public static Stage currentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static Integer tryParseToInt(String any) {
        try {
            return Integer.parseInt(any);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
