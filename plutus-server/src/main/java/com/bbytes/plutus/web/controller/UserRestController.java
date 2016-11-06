package com.bbytes.plutus.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.service.PlutusException;
import com.bbytes.plutus.service.UserService;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.USER_URL)
public class UserRestController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	private PlutusRestResponse getAll() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, userService.findAll());
		return status;
	}

	

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private PlutusRestResponse create(@RequestBody PlutusUser user) throws PlutusException {

		if (user == null)
			throw new PlutusException("User request is empty or null");

		if (userService.exists(user.getEmail())) {
			throw new PlutusException("User with id exist in DB");
		}

		user = userService.create(user);

		PlutusRestResponse status = new PlutusRestResponse("User save success", true);
		return status;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	private PlutusRestResponse update(@RequestBody PlutusUser user) throws PlutusException {

		if (user == null)
			throw new PlutusException("User request is empty or null");

		if (!userService.exists(user.getEmail())) {
			throw new PlutusException("User cannot be new for update");
		}
		user = userService.update(user);

		PlutusRestResponse status = new PlutusRestResponse("Product update success", true);
		return status;
	}
	
	@RequestMapping(value = "/updatePassword/{email}", method = RequestMethod.POST)
	private PlutusRestResponse updatePassword(@PathVariable String email, @RequestParam("password") String newPassword ) throws PlutusException {

		if (!userService.exists(email)) {
			throw new PlutusException("User does not exist");
		}
		
		userService.updatePassword(email, newPassword);

		PlutusRestResponse status = new PlutusRestResponse("Product update success", true);
		return status;
	}

	@RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
	private PlutusRestResponse delete(@PathVariable String email) throws PlutusException {
		if (email == null)
			throw new PlutusException("User email is empty or null");

		userService.delete(email);

		PlutusRestResponse status = new PlutusRestResponse("User deleted", true);
		return status;
	}

	@ExceptionHandler(PlutusException.class)
	public ResponseEntity<PlutusRestResponse> exception(HttpServletRequest req, PlutusException e) {
		PlutusRestResponse status = new PlutusRestResponse(e.getMessage(), false);
		return new ResponseEntity<PlutusRestResponse>(status, HttpStatus.OK);
	}

}