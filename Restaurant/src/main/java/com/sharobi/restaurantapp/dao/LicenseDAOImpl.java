package com.sharobi.restaurantapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.sharobi.license.struts.key.KeyBean;
import com.sharobi.license.struts.key.KeyDetails;
import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.StoreMaster;
import com.sharobi.restaurantapp.model.util.PersistenceListener;

@Component("licenseDAO")
public class LicenseDAOImpl implements LicenseDAO {

  private EntityManagerFactory entityManagerFactory = PersistenceListener.getEntityManager();

	@Override
	public KeyBean addStoreLicenseInformation(String licenseKey)
			throws DAOException {

		EntityManager em = null;
		KeyBean keyBean = null;
		KeyBean keyBean1 = new KeyBean();
		keyBean1.setStatus("N");
		String key = "";
		String keyStoreId = "";
		int storeid = 0;

		try {
			String[] parts = licenseKey.split("\"");
			key = parts[3];
			System.out.println("part1..." + key);
			KeyDetails keyDetails = new KeyDetails();
			keyBean = keyDetails.getKeyDetails(key);
			keyStoreId = keyBean.getStore_id();
			storeid = Integer.parseInt(keyStoreId);
			keyBean.setStore_id(Integer.toString(storeid));
		} catch (Exception ex) {
			System.out.println("Error in license Key");
			ex.printStackTrace();
			return keyBean1;
		}

		try {
			keyBean.setLicense_key(key);
			
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();

			// check if key already exists for store, delete it
			try {

				Query query = em
						.createQuery("DELETE FROM KeyBean k where k.store_id=:store_id");
				query.setParameter("store_id", Integer.toString(storeid));
				query.executeUpdate();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			{
				em.persist(keyBean);
				em.getTransaction().commit();
				String noOfUsers = keyBean.getNo_of_users();
				String frmDate = keyBean.getFrom_date();
				String toDate = keyBean.getTo_date();
				// remove unnecessary data from key bean
				keyBean1.setNo_of_users(noOfUsers);
				keyBean1.setFrom_date(frmDate);
				keyBean1.setTo_date(toDate);
				keyBean1.setStatus("Y");
				System.out.print("License info saved successfully...");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			em.close();
		}
		return keyBean1;

	}

	@Override
	public KeyBean aboutProduct(String storeId) throws DAOException {
		EntityManager em = null;
		KeyBean keyBean = null;
		KeyBean key=null;

		try {
			StoreAddressDAO addressDAO = new StoreAddressDAOImpl();
			StoreMaster store = addressDAO.getStoreByStoreId(Integer
					.parseInt(storeId));
			

			em = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			Query qry = em
					.createQuery("SELECT k FROM KeyBean k WHERE k.store_id=:store_id order by k.id desc");
			qry.setMaxResults(1);
			qry.setParameter("store_id", storeId);
			keyBean = (KeyBean) qry.getSingleResult();
			
			//get keybean from license
			KeyDetails keyDetails = new KeyDetails();
			key = keyDetails.getKeyDetails(keyBean.getLicense_key());
			key.setStoreName(store.getStoreName());
		
			System.out.print("License info got successfully...");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("Check data to be inserted", e);

		} finally {
			em.close();
		}
		return key;

	}

}
