package net.chrisphilbin.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.chrisphilbin.cms.email.EmailDetails;
import net.chrisphilbin.cms.entity.User;
import net.chrisphilbin.cms.exception.EmailNotSentException;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    
    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMessageBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "ok";
        } catch (Exception e) {
            throw new EmailNotSentException();
        }
    }

    @Override
    public EmailDetails generateResetPasswordEmail(String token, User user) {
        final String BASEURL = "http://localhost:8080/";
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("Reset your password");
        emailDetails.setMessageBody("Here is the link to reset your password. If this was not initiated by you please ignore this email. \r\n" + BASEURL + "user/changePassword?token=" + token);
        return emailDetails;
    }
    
}
