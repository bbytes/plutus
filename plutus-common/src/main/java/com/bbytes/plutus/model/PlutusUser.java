package com.bbytes.plutus.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class PlutusUser {

	@Id
	private String email;

	private String name;

	private String status;

	private String password;

	private boolean accountInitialise;

	private String timeZone;

	private String timePreference;

	private boolean emailNotificationState = true;

	// embedded
	private String userRole = "Normal";

	@CreatedDate
	private Date creationDate;

	@LastModifiedDate
	private Date lastModified;

	public PlutusUser(String name, String email) {
		this.name = name;
		this.email = email;
	}

}
