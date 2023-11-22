package uk.ac.le.qasm.job.searching.api.adapter;

import uk.ac.le.qasm.job.searching.api.entity.JobPost;

import java.util.Set;

public interface JobSearchService {
    Set<JobPost> searchAll();
}
