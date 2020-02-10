package com.virtualpairprogrammers.roombooking.rest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.roombooking.data.UserRepository;
import com.virtualpairprogrammers.roombooking.model.AngularUser;
import com.virtualpairprogrammers.roombooking.model.entities.User;

@RestController
@RequestMapping("/api/users")
public class RestUsersController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping()
	public List<AngularUser> getAllUsers() throws InterruptedException{
		Thread.sleep(2000);
		System.out.println("Get all users called..");
		return userRepository.findAll().parallelStream().map( user -> new AngularUser(user)).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public AngularUser getUser(@PathVariable("id") Long id) {
		System.out.println("Got a request for user " + id);
		return new AngularUser(userRepository.findById(id).get());
	}
	
	@PutMapping()
	public AngularUser updateUser(@RequestBody AngularUser updatedUser) throws InterruptedException {
		Thread.sleep(1000);
//		throw new RuntimeException("something went wrong");
		User originalUser = userRepository.findById(updatedUser.getId()).get();
		originalUser.setName(updatedUser.getName());
		return new AngularUser(userRepository.save(originalUser));
	}
	
	@PostMapping()
	public AngularUser newUser(@RequestBody AngularUser user) {
//		if(user.getId() == null) {
//			List<AngularUser> allUsers = getAllUsers();
//			Long id = allUsers.parallelStream().max(Comparator.comparingLong(AngularUser::getId)).get().getId();
//			user.setId(id++);
//		}
		
		User newUser =  new User();
		newUser.setName(user.getName());
		newUser.setPassword("secret");
		
		return new AngularUser(userRepository.save(newUser));
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping("/resetPassword/{id}")
	public void resetPassword(@PathVariable("id") Long id) {
		User user = userRepository.findById(id).get();
		user.setPassword("secret");
		userRepository.save(user);
	}
}
