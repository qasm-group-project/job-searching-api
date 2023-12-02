package uk.ac.le.qasm.job.searching.api.service;

import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobApplication;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
import uk.ac.le.qasm.job.searching.api.exception.UserNotFoundException;
import uk.ac.le.qasm.job.searching.api.persistence.JobApplicationPersistence;
import uk.ac.le.qasm.job.searching.api.persistence.JobSeekerPersistence;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobSeekerService implements uk.ac.le.qasm.job.searching.api.adapter.JobSeekerService {

    private final JobSeekerPersistence jobSeekerPersistence;
    private final JobApplicationPersistence jobApplicationPersistence;
    private final CsvFileWriterService csvFileWriterService;

    public JobSeekerService(JobSeekerPersistence jobSeekerPersistence,
                            JobApplicationPersistence jobApplicationPersistence,
                            CsvFileWriterService csvFileWriterService) {
        this.jobSeekerPersistence = jobSeekerPersistence;
        this.jobApplicationPersistence = jobApplicationPersistence;
        this.csvFileWriterService = csvFileWriterService;
    }

    @Override
    public JobSeeker findByUsername(String username) {
        return jobSeekerPersistence.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public JobSeeker findById(UUID id) {
        return jobSeekerPersistence.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public JobSeeker update(JobSeeker jobSeeker) {
        return jobSeekerPersistence.update(jobSeeker);
    }

    public byte[] getAllJobApplicationsBySeekerInFile(JobSeeker jobSeeker) {
        Set<JobApplication> applications = jobApplicationPersistence.findAllByApplicant(jobSeeker);

        return csvFileWriterService.generate(applications.stream()
                                                         .map(JobApplication::getJobPost)
                                                         .sorted(Comparator.comparing(JobPost::getTitle))
                                                         .collect(Collectors.toCollection(LinkedHashSet::new)));
    }


}
