package net.chrisphilbin.cms.service;

import net.chrisphilbin.cms.email.EmailDetails;
import net.chrisphilbin.cms.entity.User;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    EmailDetails generateResetPasswordEmail(String token, User user);
}
