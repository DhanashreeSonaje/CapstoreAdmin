package com.capstore.app.signup_login;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capstore.app.models.CustomerDetails;
import com.capstore.app.models.MerchantDetails;
import com.capstore.app.models.User;

import lombok.Data;

@Entity
@Data
@Table(name="confirmation_token")
public class ConfirmationToken {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(name="user_id")
    private int uid;
    
    public ConfirmationToken(int uid) {
        this.uid = uid;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
    
	public ConfirmationToken() {
		super();
		// TODO Auto-generated constructor stub
	}

}