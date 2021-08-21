package com.sharobi.restaurantapp.dao;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.RewardPoint;

public interface RewardsDAO {

	public void createReward () throws DAOException;
	
	public void updateReward (RewardPoint reward) throws DAOException;
	
	public void deleteReward (RewardPoint reward) throws DAOException;
}
