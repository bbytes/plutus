package com.bbytes.plutus.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.Product;

@Service
public class InitDBDataService {

	@Autowired
	private ProductService productService;
	
	@PostConstruct
	private void initDB(){
		
		Product statusnap = new Product();
		statusnap.setId(ProductName.Statusnap.toString());
		statusnap.setName(ProductName.Statusnap.toString());
		statusnap.setDesc("Capture status online everyday");
		statusnap.addProductTeamEmails("statusnap@beyondbytes.co.in");
		
		Product recruiz = new Product();
		recruiz.setId(ProductName.Recruiz.toString());
		recruiz.setName(ProductName.Recruiz.toString());
		recruiz.setDesc("Recruitment made easy");
		recruiz.addProductTeamEmails("recruiz@beyondbytes.co.in");
		
		productService.save(statusnap);
		productService.save(recruiz);
	}
}
