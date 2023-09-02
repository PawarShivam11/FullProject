package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.exception.UserNotFoundException;
import com.demo.model.User;
import com.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	//GET ALL USERS IN LIST
	@GetMapping()
	public List<User> getall(){
		return repository.findAll();
	}
	
	// GET USER BY ID
	@GetMapping("/{id}")
	public User getuserbyid(@PathVariable(value = "id")long id) {
		return this.repository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found By This Id :"+id));
	}

	//CREATE NEW USER 
	@PostMapping()
	public User createnewuser(@RequestBody User user) {
		return repository.save(user);
	}
	
	// UPDATE USER DATA 
	@PutMapping("/{id}")
	public User updateuser(@RequestBody User user,@PathVariable(value = "id") long id) {
	User existing = this.repository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found By This Id :"+id));
		existing.setFirstname(user.getFirstname());
		existing.setLastname(user.getLastname());
		existing.setEmail(user.getEmail());
		return this.repository.save(existing);
	}
	
	// DELETE USER DATA 
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteuser(@PathVariable(value = "id")long id){
		User existing = this.repository.findById(id).orElseThrow(()-> new UserNotFoundException("User Not Found By This Id :"+id));
		 this.repository.delete(existing);
		 return ResponseEntity.ok().build();
	}
	
}
