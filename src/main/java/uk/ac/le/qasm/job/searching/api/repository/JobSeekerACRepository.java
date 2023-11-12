package uk.ac.le.qasm.job.searching.api.repository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.CrudRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;

import java.util.UUID;

@Mapper
public interface JobSeekerACRepository extends CrudRepository<JobSeekerAccount, UUID> {
}
