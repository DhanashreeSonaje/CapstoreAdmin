package com.capstore.app.models;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "common_feedback")
public class CommonFeedback {

	@Id
	@Column(name = "feedback_id")
	private int feedbackId;  //(Primary Key)
	@Column(name = "feedback_subject")
    private String feedbackSubject;
	@Column(name = "feedback_message")
    private String feedbackMessage;
	@Column(name = "user_id")
	private int userId;
	
    private int feedbackForUserId;  //(Foreign Key)
	
}