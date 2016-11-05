package com.bbytes.plutus.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.service.PlutusException;
import com.bbytes.plutus.service.ProductService;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.PRODUCT_URL)
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	private PlutusRestResponse getAll() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, productService.findAll());
		return status;
	}

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	private PlutusRestResponse getAllProductNames() throws PlutusException {
		List<String> productNameList = new ArrayList<>();
		for (Product product : productService.findAll()) {
			productNameList.add(product.getName());
		}
		PlutusRestResponse status = new PlutusRestResponse(true, productNameList);
		return status;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private PlutusRestResponse create(@RequestBody Product product) throws PlutusException {

		if (product == null)
			throw new PlutusException("Product request is empty or null");

		if (productService.exists(product.getId())) {
			throw new PlutusException("Product with id exist in DB");
		}

		product = productService.save(product);

		PlutusRestResponse status = new PlutusRestResponse("Product save success", true);
		return status;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	private PlutusRestResponse update(@RequestBody Product product) throws PlutusException {

		if (product == null)
			throw new PlutusException("Product request is empty or null");

		if (!productService.exists(product.getId())) {
			throw new PlutusException("Product cannot be new for update");
		}
		product = productService.save(product);

		PlutusRestResponse status = new PlutusRestResponse("Product update success", true);
		return status;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	private PlutusRestResponse delete(@PathVariable String id) throws PlutusException {
		if (id == null)
			throw new PlutusException("Product id is empty or null");

		productService.delete(id);

		PlutusRestResponse status = new PlutusRestResponse("Product deleted", true);
		return status;
	}

	@ExceptionHandler(PlutusException.class)
	public ResponseEntity<PlutusRestResponse> exception(HttpServletRequest req, PlutusException e) {
		PlutusRestResponse status = new PlutusRestResponse(e.getMessage(), false);
		return new ResponseEntity<PlutusRestResponse>(status, HttpStatus.OK);
	}

}