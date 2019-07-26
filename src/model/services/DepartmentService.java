package model.services;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class DepartmentService {
    private DepartmentDao dao = DaoFactory.createDepartmentDao(); // Cria uma instancia de DepartmentDao

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj) {
        if (obj.getId() == null) {  // Precisamos verficiar se é uma insercao ou update
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }
}
