package net.chrisphilbin.todo.service;

import net.chrisphilbin.todo.email.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
}
