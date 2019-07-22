package gui.Department;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

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
    private void onBtnAction() {
        System.out.println("Clicado");
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
}
