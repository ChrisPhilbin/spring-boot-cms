package net.chrisphilbin.todo.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.chrisphilbin.todo.entity.PasswordResetToken;
import net.chrisphilbin.todo.repository.PasswordTokenRepository;

@AllArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private PasswordTokenRepository passwordTokenRepository;

    @Override
    public void deleteToken(String token) {
        PasswordResetToken foundToken = passwordTokenRepository.findByToken(token);
        passwordTokenRepository.delete(foundToken);
    }
    
}
