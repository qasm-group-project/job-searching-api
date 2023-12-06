package uk.ac.le.qasm.job.searching.api.adapter;

import org.springframework.data.domain.Page;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.SearchJobRequest;

import java.util.Set;
import java.util.UUID;

public interface JobSearchService {
    Set<JobPost> searchAll();

    JobPost findById(UUID jobId);
    Page<JobPost> searchBy(SearchJobRequest searchJobRequest);
    Page<JobPost> searchByNoTitle(SearchJobRequest searchJobRequest);
}
