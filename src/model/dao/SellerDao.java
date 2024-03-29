package model.dao;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer client);
	List<Seller> findAll();
	List<Seller> findByDepartment(Integer id);
}
