package gui.Department;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {
    @FXML
    private TableView<Department> tableViewDepartment;

    // Declarar 2 tipos: Tipo da entidade e tipo do valor na coluna
    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button button;

    @FXML
    private void onBtnAction() {
        System.out.println("Clicado");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeNodes();
    }

    // Precisamos inicializar nossas colunas no ato de render do component
    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Alongando a tabela height. Apos capturar as dimensoes da janelas, setamos pra tabela atualizar com elas
        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());

    }
}
