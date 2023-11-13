package uk.ac.le.qasm.job.searching.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;

import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {

}
