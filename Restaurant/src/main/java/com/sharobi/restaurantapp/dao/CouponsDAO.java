package com.sharobi.restaurantapp.dao;

import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.Coupon;

public interface CouponsDAO {

	public void createCoupon (String coupon)throws DAOException;
	public void updateCoupon (Coupon coupon) throws DAOException;
	public void deleteCoupon (Coupon coupon) throws DAOException;
	public List<Coupon> getCoupons() throws DAOException;
	public void sendCoupon (Coupon coupon) throws DAOException;
}
