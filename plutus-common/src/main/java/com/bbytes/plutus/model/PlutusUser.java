package com.bbytes.plutus.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
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

//	@JsonIgnore
	private String password;

	// embedded
	private String userRole = "Normal";

	@CreatedDate
	private Date creationDate;

	@LastModifiedDate
	private Date lastModified;

	@CreatedBy
	private String createdBy;

	public PlutusUser() {
	}

	public PlutusUser(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public PlutusUser(String name, String email, String role) {
		this.name = name;
		this.email = email;
		this.userRole = role;
	}

}
