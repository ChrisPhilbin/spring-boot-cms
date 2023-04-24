package net.chrisphilbin.todo.repository;
import org.springframework.data.repository.CrudRepository;
import net.chrisphilbin.todo.entity.PasswordResetToken;;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
