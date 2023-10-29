package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.User;
import uk.ac.le.qasm.job.searching.api.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserPersistence {

    private final UserRepository userRepository;

    public UserPersistence(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }
}
