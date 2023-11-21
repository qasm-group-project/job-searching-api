package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.Provider;

import java.util.Optional;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    Optional<Provider> findByUsername(String username);
}
