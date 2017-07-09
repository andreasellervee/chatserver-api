package chatserver.user.repository;

import chatserver.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByName(String name);

    Optional<User> findByName(String name);

}
