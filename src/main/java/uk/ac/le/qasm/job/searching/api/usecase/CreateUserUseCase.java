package uk.ac.le.qasm.job.searching.api.usecase;

import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.User;
import uk.ac.le.qasm.job.searching.api.persistence.UserPersistence;

@Component
public class CreateUserUseCase {

    private final UserPersistence userPersistence;

    public CreateUserUseCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User create(User user) {
        return userPersistence.create(user);
    }

}
