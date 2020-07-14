package io.kryptoblocks.msa.user.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.kryptoblocks.msa.user.repository.model.CustomerBankAccount;
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;
import io.kryptoblocks.msa.user.repository.model.CustomerDriverLicense;
import io.kryptoblocks.msa.user.repository.model.CustomerVehicle;
import io.kryptoblocks.msa.user.repository.model.PasswordResetToken;
import io.kryptoblocks.msa.user.repository.model.User;
import io.kryptoblocks.msa.user.repository.model.UserLogin;
import io.kryptoblocks.msa.user.repository.model.UserToken;

public class UserServiceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceRepository.class);

	@Autowired
	@Qualifier("usrEntityManagerFactory")
	private EntityManagerFactory usrEntityManagerFactory;
	
	

	
	public void saveCC(CustomerCreditCard ucc) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(ucc);
		em.getTransaction().commit(); // Will write
		em.close();

	}

	public List<CustomerCreditCard> getUserCCards(String email) {
		List<CustomerCreditCard> userCCList = new ArrayList<CustomerCreditCard>();
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT userCC FROM UserCreditCard userCC WHERE userCC.email=:email",
					CustomerCreditCard.class);
			q.setParameter("email", email);
			q.executeUpdate();
			for(Object result : q.getResultList()) {
				CustomerCreditCard curCC = (CustomerCreditCard)result;	
				userCCList.add(curCC);
			} 
			//userCC = em.find(UserCreditCard.class, email);
		} catch (NoResultException e) {
			//TODO
			LOGGER.debug("Credit Card details doesnt exist for {}", email);
		}
		return userCCList;
	}

	public void saveOrUpdateDL(CustomerDriverLicense driverLicense) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		em.getTransaction().begin();
		em.persist(driverLicense);
		em.getTransaction().commit(); // Will write
		em.close();

	}

	public List<CustomerDriverLicense> getDL(String email) {
		List<CustomerDriverLicense> returnValue = new ArrayList<CustomerDriverLicense>();
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT customerDL FROM CustomerDriverLicense customerDL WHERE customerDL.email=:email",
					CustomerDriverLicense.class);
			q.setParameter("email", email);
			q.executeUpdate();
			for(Object result : q.getResultList()) {
				CustomerDriverLicense curDL = (CustomerDriverLicense)result;	
				returnValue.add(curDL);
			} 			
			 
		} catch (NoResultException e) {
			LOGGER.debug("Driving License details doesnt exist for {}", email);
		}
		return returnValue;
	}

	public void saveOrUpdateVehicle(CustomerVehicle userVehicle) {
		
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		em.getTransaction().begin();
		em.persist(userVehicle);
		em.getTransaction().commit(); // Will write
		em.close();
		
	}

	public List<CustomerVehicle> getVehicles(String email) {
		List<CustomerVehicle> returnValue = new ArrayList<CustomerVehicle>();
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT customerVehicle FROM CustomerVehicle customerVehicle WHERE customerVehicle.email=:email",
					CustomerDriverLicense.class);
			q.setParameter("email", email);
			q.executeUpdate();
			for(Object result : q.getResultList()) {
				CustomerVehicle curVehicle = (CustomerVehicle)result;	
				returnValue.add(curVehicle);
			} 			
			 
		} catch (NoResultException e) {
			LOGGER.debug("Customer vehicle details doesnt exist for {}", email);
		}
		return returnValue;
	}

	public void saveBankAccount(CustomerBankAccount bankAccount) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		em.getTransaction().begin();
		em.persist(bankAccount);
		em.getTransaction().commit(); // Will write
		em.close();
		
	}

	public List<CustomerBankAccount> getBankAccounts(String email) {
		List<CustomerBankAccount> returnValue = new ArrayList<CustomerBankAccount>();
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT customerBankAccount FROM CustomerBankAccount customerBankAccount WHERE customerBankAccount.email=:email",
					CustomerBankAccount.class);
			q.setParameter("email", email);
			q.executeUpdate();
			for(Object result : q.getResultList()) {
				CustomerBankAccount curBKAccount = (CustomerBankAccount)result;	
				returnValue.add(curBKAccount);
			} 			
			 
		} catch (NoResultException e) {
			LOGGER.debug("Bank account details doesnt exist for {}", email);
		}
		return returnValue;
	}
	
	 
	public User saveUser(User  user) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); 
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit(); // Will write
		em.close();
		return user;
	}
	 
	public void saveUserLogin(UserLogin userLogin) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); 
		em.getTransaction().begin();
		em.persist(userLogin);
		em.getTransaction().commit(); // Will write
		em.close();

	}

	public User  findUserByEmail(String email) {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT user FROM User user WHERE user.email=:email", User.class);
			q.setParameter("email", email);
			return (User)q.getSingleResult();
	}
	
	 
	public void saveResetToken(PasswordResetToken resetToken) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); 
		em.getTransaction().begin();
		em.persist(resetToken);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	public PasswordResetToken findResetToken(String email){
		PasswordResetToken rtoken = null;
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT token FROM PasswordResetToken token WHERE token.email=:email", PasswordResetToken.class);
			q.setParameter("email", email);
			rtoken = (PasswordResetToken) q.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.debug("Token doesnt exist {}", email);
		}
		return rtoken;
	}
	 
	public void saveUserToken(UserToken verToken) {
		EntityManager em = usrEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); 
		em.getTransaction().begin();
		em.persist(verToken);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	public UserToken findUserToken(String email, String type, String createdTime){
		UserToken vtoken = null;
		try {
			EntityManager em = usrEntityManagerFactory.createEntityManager();
			Query q = em.createQuery("SELECT token FROM UserToken token WHERE token.email=:email AND token.type=:type AND token.createdTime=:createdTime ", UserToken.class);
			q.setParameter("email", email);
			q.setParameter("type", type);
			q.setParameter("createdTime", createdTime);
			
			vtoken = (UserToken) q.getSingleResult();
		} catch (NoResultException e) {
			LOGGER.debug("Token doesnt exist {}", email);
		}
		return vtoken;
	}

}
