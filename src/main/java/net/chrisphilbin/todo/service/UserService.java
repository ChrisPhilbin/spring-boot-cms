package net.chrisphilbin.todo.service;


import net.chrisphilbin.todo.entity.User;

public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);
    void createPasswordResetTokenForUser(User user, String token);
    Boolean validatePasswordResetToken(String token);
}