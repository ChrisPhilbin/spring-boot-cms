package net.chrisphilbin.cms.repository;
import org.springframework.data.repository.CrudRepository;
import net.chrisphilbin.cms.entity.PasswordResetToken;;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    Long findUserIdByToken(String token);
}
