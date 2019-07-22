package gui;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("Seller");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        System.out.println("Department");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Open New Screen
    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            // Salvamos a atual tela
            Scene mainScene = Main.getMainScene();

            // Primeiro acessamos o Primeiro Filho no Caso VBox
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            // Segundo Pegamos uma referencia do filho menu e limpamos os demais
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();

            // Terceiro devolvemos o menu porque queremos ele + o novo dado
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().add(newVBox);

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}