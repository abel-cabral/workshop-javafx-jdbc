package model.services;

import model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    public List<Department> findAll() {
        // MOCK
        List<Department> list = new ArrayList<Department>();
        list.add(new Department(25, "Cumputers"));
        list.add(new Department(10, "Books"));
        list.add(new Department(58, "Eletronics"));
        return list;
    }
}
