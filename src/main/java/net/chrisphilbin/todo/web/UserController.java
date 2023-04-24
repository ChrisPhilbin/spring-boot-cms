package net.chrisphilbin.todo.web;

import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.Http;

import net.chrisphilbin.todo.entity.User;
import net.chrisphilbin.todo.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    UserService userService;
	// MailSender mailSender;

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
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		// mailSender.send();

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/changePassword")
	public ResponseEntity<HttpStatus> showChangePasswordPage(@RequestParam String token) {
		Boolean isTokenValid = userService.validatePasswordResetToken(token);
		if (isTokenValid == true) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
	}

	// ======= NON-API =======

	// final String EMAIL_BODY = "Here is the link to reset your password. If this was not initiated by you please ignore this email.";
	// final String BASE_URL = "https://does-rover-live.uc.r.appspot.com/";

	// private SimpleMailMessage constructResetTokenEmail(final String contextPath, final String token, final User user) {
	// 	String url = BASE_URL + "/user/changePassword?token=" + token;
	// 	return constructEmail("Reset Password", EMAIL_BODY + " \r\n" + url, user);
	// }

	// private SimpleMailMessage constructEmail(String subject, String body, User user) {
    //     final SimpleMailMessage email = new SimpleMailMessage();
    //     email.setSubject(subject);
    //     email.setText(body);
    //     // email.setTo(user.getEmail());
    //     // email.setFrom(env.getProperty("support.email"));
    //     return email;
    // }

}
