package gui.Department;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

import java.net.URL;
import java.util.*;

public class DepartmentFormController implements Initializable {
    private Department entity;
    private DepartmentService service;
    private List<DataChangeListener> dataChangeListenerListeners = new ArrayList<DataChangeListener>();

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
        if (entity == null) {
            throw new IllegalStateException("Entity nao injetada");
        }
        if (service == null) {
            throw new IllegalStateException("Service nao injetada");
        }
        try {
            getFormData(entity);
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close(); // Depois da nossa programacao defensiva, precisamos fechar o formulario
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }


    }

    // Vai evocar uma funcao x em todos inscritos no vetor
    private void notifyDataChangeListeners() {
        for (DataChangeListener s : dataChangeListenerListeners) {
            s.onDataChanged();
        }
    }

    @FXML
    private void onCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
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

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    private void getFormData(Department entity) { // Passa o valor do form para o obj instanciado
        ValidationException exception = new ValidationException("Validation error"); // instanciar Excessao

        entity.setId(Utils.tryParseToInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.setErrors("name", "Nome do setor é obrigatório");
        }
        entity.setName(txtName.getText());
        if (exception.getErrors().size() > 0) { // Map de Validation
            throw exception;
        }
    }

    public void subscribleDataChangeListener(DataChangeListener listener) { // Qualquer implemente essa interface
        dataChangeListenerListeners.add(listener);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        // Se tiver um error n faz isso
        if (fields.contains("name")) {
            errorName.setText(errors.get("name"));  // Pega a mensagem na chave name
        }
    }
}
