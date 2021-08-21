package com.sharobi.restaurantapp.dao;

import com.sharobi.restaurantapp.dao.exception.DAOException;

public interface SocialMediaDAO {

	public void addSocialMedia (String url)throws DAOException;
	
	public void updateSocialMedia(String url) throws DAOException;
	
	public void deleteSocialMedia(String url) throws DAOException;
	
	public void getSocialMedia() throws DAOException;
}
