package com.bbytes.plutus.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Document
public class BaseEntity {

	@Id
	private String id;
	
	private String name;
	
	@CreatedDate
	private Date creationDate;
	
	@LastModifiedDate
	private Date lastModified;

}
