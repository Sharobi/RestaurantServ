package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.Customer;
import com.sharobi.restaurantapp.model.StoreMaster;
import com.sharobi.restaurantapp.model.User;
import com.sharobi.restaurantapp.model.UserTransaction;

public interface UserDAO {

	public List<User> getAllUsers() throws DAOException;

	public List<Customer> getAllTabletUsers() throws DAOException;

	public User getUserById(String userId) throws DAOException;

	public Customer getTabletUserById(String tabletUserId) throws DAOException;

	public void createUser(User user) throws DAOException;

	public void update(User user) throws DAOException;

	public void updateTabletUser(Customer tabletUser) throws DAOException;

	public void delete(User user) throws DAOException;

	public User login(User user) throws DAOException;

	public List<StoreMaster> getStoresByUser(int id) throws DAOException;

	public Customer getCustomerByContactNo(String conctNo) throws DAOException;

	public void createUserST(User user, UserTransaction transaction)
			throws DAOException;

	public void createTabletUserST(Customer user) throws DAOException;

	public List<StoreMaster> getStoreByRestaurantId(int id) throws DAOException;
	
	public User loginToCheckAdmin(User user) throws DAOException;

}
