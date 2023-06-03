package net.chrisphilbin.cms.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.chrisphilbin.cms.entity.PasswordResetToken;
import net.chrisphilbin.cms.repository.PasswordTokenRepository;

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
