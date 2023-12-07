package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.entity.ProviderNews;

import java.util.Optional;
import java.util.UUID;

public interface ProviderNewsRepository extends CrudRepository<ProviderNews, UUID> {

    Optional<ProviderNews> findByIdAndProvider(UUID newsId, Provider provider);
}
