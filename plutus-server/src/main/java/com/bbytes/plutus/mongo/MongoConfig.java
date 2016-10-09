package com.bbytes.plutus.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableMongoRepositories(basePackages = "com.bbytes.plutus.repo")
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongodb.db");
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("mongodb.host"), Integer.parseInt(env.getProperty("mongodb.port")));
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.bbytes.plutus.model";
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public CascadeSaveMongoEventListener cascadingMongoEventListener() {
		return new CascadeSaveMongoEventListener();
	}
}
