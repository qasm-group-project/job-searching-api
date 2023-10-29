package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
