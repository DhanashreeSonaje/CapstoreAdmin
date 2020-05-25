package com.capstore.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.capstore.app.models.CustomerDetails;
import com.capstore.app.models.MerchantDetails;

import lombok.Data;

@Data
@EnableTransactionManagement
@Repository
@Transactional
public class UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@PersistenceContext
	EntityManager entityManager;
	
	public CustomerDetails findCustomerById(int id) {
		CustomerDetails cd=entityManager.find(CustomerDetails.class, id);
		return cd;
	}
	
	public MerchantDetails findMerchantById(int id) {
		MerchantDetails md=entityManager.find(MerchantDetails.class, id);
		return md;
	}
	
	public CustomerDetails findCustomerByEmailIgnoreCase(String email)
	{	
		CustomerDetails cd=null;
		try {
		TypedQuery<CustomerDetails> q2 = entityManager
				.createQuery("SELECT cd from CustomerDetails cd where email='"+email+"'",CustomerDetails.class);
		cd = (CustomerDetails)q2.getSingleResult();
		}
		catch(NoResultException nre) { }
		
		return cd;
	}
	
	public MerchantDetails findMerchantByEmailIgnoreCase(String email)
	{
		MerchantDetails md=null;
		try {
		TypedQuery<MerchantDetails> q2 = entityManager
				.createQuery("SELECT md from MerchantDetails md where email='"+email+"'",MerchantDetails.class);
		md = (MerchantDetails)q2.getSingleResult();
		}
		catch(NoResultException nre) { }
		
		return md;
	}
	
	public void saveCustomer(CustomerDetails cd) {
		entityManager.persist(cd);
		entityManager.flush();
	}
	
	public void saveMerchant(MerchantDetails md) {
		entityManager.persist(md);
		entityManager.flush();
	}
	
}