package com.capstore.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.capstore.app.models.User;
import com.capstore.app.signup_login.ConfirmationToken;

import lombok.Data;

@Data
@Repository
@EnableTransactionManagement
@Transactional
public class ConfirmationTokenRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@PersistenceContext
	EntityManager entityManager;
	
	public ConfirmationToken findByConfirmationToken(String confirmationToken)
	{	
		ConfirmationToken confToken=null;
		try {
		TypedQuery<ConfirmationToken> q2 = entityManager
				.createQuery("SELECT ct from ConfirmationToken ct where confirmationToken='"+confirmationToken+"'",ConfirmationToken.class);            
		confToken = (ConfirmationToken)q2.getSingleResult();
		} catch(NoResultException nre) { }
		
		return confToken;
	}
	
	public void save(ConfirmationToken confirmationToken) {
		entityManager.persist(confirmationToken);
		entityManager.flush();
	}
	
}