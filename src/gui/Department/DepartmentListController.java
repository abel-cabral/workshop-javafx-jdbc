package gui.Department;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {
    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartment;

    // Declarar 2 tipos: Tipo da entidade e tipo do valor na coluna
    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button button;

    private ObservableList<Department> obsList;

    @FXML
    private void onBtnAction(ActionEvent event) { // Semelhante a passagem de evento no js
        Stage parentStage = Utils.currentStage(event);
        createDialogForm(new Department(null, null), "/gui/Department/DepartmentForm.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeNodes();
    }

    // Ao inves de injetar na inicializacao, fazemos isso numa funcao
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    // Precisamos inicializar nossas colunas no ato de render do component
    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Alongando a tabela height. Apos capturar as dimensoes da janelas, setamos pra tabela atualizar com elas
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());

        setDepartmentService(new DepartmentService());
        updateTableView();

    }

    // Acessa o service e carrega os departamento para a ObservableList
    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Carregue os serviços");
        }

        List<Department> list = service.findAll(); //Recebe uma list

        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
    }

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            // Injeta o obj no controlador da tela de formulario
            DepartmentFormController controller = loader.getController();
            controller.setDepartment(obj); // Nesse caso nao alteramos nada porque é um novo elemento
            controller.setDepartmentService(new DepartmentService());       // Lembre-se de injetar o servicó
            controller.updateFormData();

            // Vamos instanciar um novo state acima do atual
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department Data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false); // permite que redimensione a janela ou nao
            dialogStage.initOwner(parentStage); // Podemos informar que é o pai dessa cenário
            dialogStage.initModality(Modality.WINDOW_MODAL); // Janela e formato de modal acima da anterior
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("Io Exception", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
