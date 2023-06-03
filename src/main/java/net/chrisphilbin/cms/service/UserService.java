package net.chrisphilbin.cms.service;


import net.chrisphilbin.cms.entity.User;

public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);
    void createPasswordResetTokenForUser(User user, String token);
    Boolean validatePasswordResetToken(String token);
    User getUserIdByToken(String token);
}