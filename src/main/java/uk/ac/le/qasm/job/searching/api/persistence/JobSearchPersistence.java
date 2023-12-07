package uk.ac.le.qasm.job.searching.api.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.repository.SeekerSearchJobRepository;

@Component
public class JobSearchPersistence {
    private final SeekerSearchJobRepository seekerSearchJobRepository;

    public JobSearchPersistence(SeekerSearchJobRepository seekerSearchJobRepository) {
        this.seekerSearchJobRepository = seekerSearchJobRepository;
    }

    public Page<JobPost> findAllByNoTitle(Pageable pageable){
        return seekerSearchJobRepository.findAllByIsVisibleAndStatus(true, JobStatus.PENDING,pageable);
    }

    public Page<JobPost> findAllByCategoryNoTitle(String category, Pageable pageable) {
        return seekerSearchJobRepository.findAllByCategoryAndIsVisibleAndStatus(category,true,JobStatus.PENDING,pageable);
    }

    public Page<JobPost> findAllByCategoryWithTitle(String title, String category, PageRequest of) {
        return seekerSearchJobRepository.findAllByTitleAndCategoryAndIsVisibleAndStatus(title,category,true,JobStatus.PENDING,of);
    }

    public Page<JobPost> findAllByJobTypeWithTitle(String title, JobType jobType, Pageable pageable){
        return seekerSearchJobRepository.findAllByTitleAndJobTypeAndIsVisibleAndStatus(title,jobType,true,JobStatus.PENDING,pageable);
    }

    public Page<JobPost> findAllWithTitle(String title,Pageable pageable){
        return seekerSearchJobRepository.findAllByTitleAndIsVisibleAndStatus(title,true,JobStatus.PENDING,pageable);
    }
    public Page<JobPost> findAllByJobTypeWithTitle(String title,String category,JobType jobType, Pageable pageable){
        return seekerSearchJobRepository.findAllByTitleAndJobTypeAndCategoryAndIsVisibleAndStatus(title,jobType,category,true,JobStatus.PENDING,pageable);
    }

    public Page<JobPost> findAllByJobTypeNoTitle(JobType jobType, PageRequest of) {
        return seekerSearchJobRepository.findAllByJobTypeAndIsVisibleAndStatus(jobType,true,JobStatus.PENDING,of);
    }
    public Page<JobPost> findAllByAllWithTitle(String title, JobType jobType, String category,Pageable pageable){
        return seekerSearchJobRepository.findAllByTitleAndJobTypeAndCategoryAndIsVisibleAndStatus(title,jobType,category,true,JobStatus.PENDING,pageable);
    }

    public Page<JobPost> findAllByAllNoTitle(JobType jobType, String category, PageRequest of) {
        return seekerSearchJobRepository.findAllByCategoryAndJobTypeAndIsVisibleAndStatus(category,jobType,true, JobStatus.PENDING,of);
    }
}
