package net.chrisphilbin.todo.service;

import net.chrisphilbin.todo.email.EmailDetails;
import net.chrisphilbin.todo.entity.User;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    EmailDetails generateResetPasswordEmail(String token, User user);
}
