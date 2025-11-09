package com.solarcom.sites_management.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solarcom.sites_management.domain.User;
import com.solarcom.sites_management.repo.UserRepository;
import com.solarcom.sites_management.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	 @PostMapping("/add")
	    public ResponseEntity<String> addUser(@RequestBody User user) {
	        try {
				if (userService.loginExists(user.getLogin())) {
				    return ResponseEntity.badRequest().body("Login already exists");
				}
				 userService.saveUser(user);
			        return ResponseEntity.ok("User added successfully");
			} catch (Exception e) {
				// TODO Auto-generated catch block
			
				e.printStackTrace();
				  return ResponseEntity.badRequest().body("internal Error");
			}

	       
	    }

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
