package app.repository.user;

import app.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User user "
            + "left join fetch user.roles "
            + "WHERE user.email = :email")
    Optional<User> findByEmail(String email);

    @Query("FROM User user "
            + "left join fetch user.roles "
            + "WHERE user.id = :id")
    Optional<User> findById(Long id);
}
