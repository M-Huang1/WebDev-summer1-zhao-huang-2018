package com.example.webdevsummer1zhaohuang2018.services;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdevsummer1zhaohuang2018.models.User;
import com.example.webdevsummer1zhaohuang2018.repositories.UserRepository;


@RestController
public class UserService {
	
	
	@Autowired
	UserRepository repository;
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/api/register")
	@ResponseBody
	public String registerUser(@RequestBody User user) { 
		repository.save(user);
		return "User Registered";
		
	}
	
	@PostMapping("/api/user")
	@ResponseBody
	public String createUser(@RequestBody User user) { 

		repository.save(user);
		return "User Created";
		
	}
	
	@PostMapping("/api/login")
	public List<User> login(@RequestBody User user) {
		return (List<User>) repository.findUserByCredentials(user.getUsername(), user.getPassword());
	}
	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>) repository.findAll();
	}
	
	@PutMapping("/api/user/{userId}")
	@ResponseBody
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setUsername(newUser.getUsername());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setPassword(newUser.getPassword());
			user.setRole(newUser.getRole());
			user.setEmail(newUser.getEmail());
			user.setDob(newUser.getDob());
			repository.save(user);
			return user;
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int userId, HttpServletResponse response) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			System.out.println("Found It!!");
			return data.get();}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	
	@GetMapping("/api/user/username/{username}")
	public User findUserByName(@PathVariable("username") String username, HttpServletResponse response) {
		Optional<User> data = repository.findUserByUsername(username);
		if(data.isPresent()) {
			return data.get();}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	
	@GetMapping("/api/user/email/{email}")
	public User findUserByEmail(@PathVariable("email") String email, HttpServletResponse response) {
		Optional<User> data = repository.findUserByEmail(email);
		if(data.isPresent()) {
			return data.get();}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
}