package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.SearchJobRequest;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.exception.JobPostNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobPostPersistence;
import uk.ac.le.qasm.job.searching.api.persistence.JobSearchPersistence;

import java.util.Set;
import java.util.UUID;

@Service
public class JobSearchService implements uk.ac.le.qasm.job.searching.api.adapter.JobSearchService {

    private final JobPostPersistence jobPostPersistence;
    private final JobSearchPersistence jobSearchPersistence;

    public JobSearchService(JobPostPersistence jobPostPersistence, JobSearchPersistence jobSearchPersistence) {
        this.jobPostPersistence = jobPostPersistence;
        this.jobSearchPersistence = jobSearchPersistence;
    }

    @Override
    public Set<JobPost> searchAll() {
        return jobPostPersistence.findAllAvailable();
    }

    @Override
    public JobPost findById(UUID jobId) {
        return jobPostPersistence.findAvailableById(jobId).orElseThrow(JobPostNotFoundException::new);
    }

    @Override
    public Page<JobPost> searchBy(SearchJobRequest searchJobRequest) {
        int page = searchJobRequest.getPage();
        int size = searchJobRequest.getSize();
        if (searchJobRequest.getJob_type().isEmpty() && !searchJobRequest.getCategory().isEmpty()){
            return jobSearchPersistence.findAllByCategoryWithTitle(searchJobRequest.getTitle(),searchJobRequest.getCategory(), PageRequest.of(page, size));
        }else if (!searchJobRequest.getJob_type().isEmpty() && searchJobRequest.getCategory().isEmpty()){
            return jobSearchPersistence.findAllByJobTypeWithTitle(searchJobRequest.getTitle(), JobType.valueOf(searchJobRequest.getJob_type()), PageRequest.of(page, size));
        }else if (!searchJobRequest.getJob_type().isEmpty()){
            return jobSearchPersistence.findAllByAllWithTitle(searchJobRequest.getTitle(), JobType.valueOf(searchJobRequest.getJob_type()),searchJobRequest.getCategory(),PageRequest.of(page, size));
        }else{
            return jobSearchPersistence.findAllWithTitle(searchJobRequest.getTitle(), PageRequest.of(page, size));
        }
    }

    @Override
    public Page<JobPost> searchByNoTitle(SearchJobRequest searchJobRequest){

        int page = searchJobRequest.getPage();
        int size = searchJobRequest.getSize();


        if (searchJobRequest.getJob_type().isEmpty() && !searchJobRequest.getCategory().isEmpty()){

            return jobSearchPersistence.findAllByCategoryNoTitle(searchJobRequest.getCategory(),PageRequest.of(page, size));

        }else if (!searchJobRequest.getJob_type().isEmpty() && searchJobRequest.getCategory().isEmpty()){
            return jobSearchPersistence.findAllByJobTypeNoTitle(JobType.valueOf(searchJobRequest.getJob_type()), PageRequest.of(page, size));
        }else if (!searchJobRequest.getJob_type().isEmpty()){
            return jobSearchPersistence.findAllByAllNoTitle(JobType.valueOf(searchJobRequest.getJob_type()),searchJobRequest.getCategory(),PageRequest.of(page, size));
        }else{
            return jobSearchPersistence.findAllByNoTitle(PageRequest.of(page, size));
        }
    }
}
