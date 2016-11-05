package com.bbytes.plutus.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
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
	private DateTime creationDate;

	@LastModifiedDate
	private DateTime lastModified;

	@CreatedBy
	private String createdBy;

	@LastModifiedBy
	private String updatedBy;

}
