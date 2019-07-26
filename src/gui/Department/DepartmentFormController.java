package gui.Department;

import gui.util.Constraints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {
    private Department entity;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private Label errorName;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void onSaveAction(ActionEvent event) {
        System.out.println("Salvo");
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        System.out.println("Cancelado");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    // Nossas validacoes básicas
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Voce precisa instanciar um Departamento");
        }
        txtId.setText(String.valueOf(entity.getId())); // Trabalha só com strings
        txtName.setText(entity.getName());
    }
}
