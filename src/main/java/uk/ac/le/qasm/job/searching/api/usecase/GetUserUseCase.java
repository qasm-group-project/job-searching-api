package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.User;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.UserPersistence;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetUserUseCase {

    private final UserPersistence userPersistence;

    public GetUserUseCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User getById(UUID id) {
        Optional<User> user = userPersistence.getById(id);

        return user.orElseThrow(UserNotFoundException::new);
    }
}
