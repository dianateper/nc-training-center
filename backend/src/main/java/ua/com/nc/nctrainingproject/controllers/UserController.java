package ua.com.nc.nctrainingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.nc.nctrainingproject.models.User;
import ua.com.nc.nctrainingproject.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	ResponseEntity<User> update(@RequestParam(name = "login") String login,
							 @RequestParam(name = "newPassword") String newPassword,
							 @RequestParam(name = "newEmail") String newEmail) {
		User newData = new User(login, newPassword, newEmail);
		User response = userService.updateByName(newData);
		return Optional.ofNullable(response).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<User> get(@PathVariable(value = "id") int id) {
		User response = userService.getById(id);
		return Optional.ofNullable(response).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create/admin")
	ResponseEntity<User> createAdmin(@RequestBody User admin) {
		User response = userService.createAdmin(admin);
		return Optional.ofNullable(response).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/activate")
	public ResponseEntity<User> activate(@RequestParam(name = "email") String email,
									  @RequestParam(name = "code") String code) {
		User response = userService.activateAccount(email, code);
		return Optional.ofNullable(response).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/deactivate")
	public ResponseEntity<?> deactivateAccount(@PathVariable(value = "id") int id) {
		return userService.deactivateAccount(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{searchName}")
	public ResponseEntity<List<User>> searchUsers(@PathVariable(value = "searchName") String searchName) {
		List<User> response = userService.searchUsersByUsername(searchName);
		return response.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : ResponseEntity.ok(response);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allAdmins")
	public List<User> getAdmins() {
		return userService.getAllAdmins();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allModer")
	public List<User> getModerators() {
		return userService.getAllModerators();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allActivatedModer")
	public List<User> getActivatedModerators() {
		return userService.getActivatedModerators();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allActivatedAdmin")
	public List<User> getActivatedAdmins() {
		return userService.getActivatedAdmins();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/all")
	public ResponseEntity<?> getAllUsers() {
		List<User> response = userService.getAllUsers();
		return response.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : ResponseEntity.ok(response);
	}
}
