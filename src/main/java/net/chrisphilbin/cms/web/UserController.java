package net.chrisphilbin.cms.web;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.chrisphilbin.cms.entity.User;
import net.chrisphilbin.cms.service.EmailService;
import net.chrisphilbin.cms.service.TokenService;
import net.chrisphilbin.cms.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    UserService userService;
	TokenService tokenService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("/{id}")
	public ResponseEntity<String> findById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUser(id).getUsername(), HttpStatus.OK);
	}

    @PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		userService.saveUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<HttpStatus> resetPassword(@RequestParam String userName) {
		User user = userService.getUser(userName);
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		emailService.sendSimpleMail(emailService.generateResetPasswordEmail(token, user));
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	//intermediate step to render reset form to user - if the token is valid, display the form to change passwords - if not, display error message
	@GetMapping("/changePassword")
	public ResponseEntity<HttpStatus> showChangePasswordPage(@RequestParam String token) {
		userService.validatePasswordResetToken(token);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/savePassword")
	public ResponseEntity<HttpStatus> saveNewPassword(@RequestParam String token, @RequestBody String password) {
		try {
			User user = userService.getUserIdByToken(token);
			if (user == null || password == null || !userService.validatePasswordResetToken(token)) { 
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
			user.setPassword(password);
			userService.saveUser(user);
			tokenService.deleteToken(token);
			return new ResponseEntity<>(null, HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
