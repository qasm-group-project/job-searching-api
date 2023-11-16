package uk.ac.le.qasm.job.searching.api.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.mapper.JobSearchMapper;
import uk.ac.le.qasm.job.searching.api.result.SearchAllJob;
import uk.ac.le.qasm.job.searching.api.service.JobSeachService;

import java.util.List;
@Service
public class JobSeachServiceImpl implements JobSeachService {

    private final JobSearchMapper jobSearchMapper;

    public JobSeachServiceImpl(JobSearchMapper jobSearchMapper) {
        this.jobSearchMapper = jobSearchMapper;
    }

    @Override
    public ResponseEntity<?> searchAll() {
//        return ResponseEntity.status(HttpStatus.CREATED).body(jobSearchMapper.selectList(null));

        return ResponseEntity.ok().body(jobSearchMapper.selectJoinList(SearchAllJob.class,new MPJLambdaWrapper<JobPost>()
                .selectAll(JobPost.class)
                .selectAs(Provider::getCompany_name,SearchAllJob::getCompany_name)
                .selectAs(Provider::getCompany_contact_number,SearchAllJob::getCompany_contact_number)
                .selectAs(Provider::getCompany_location,SearchAllJob::getCompany_location)
                .leftJoin(Provider.class,Provider::getId,JobPost::getProvider_uuid)
                        .eq(JobPost::getIsVisible,true)
        )
        );


//        List<SearchAllJob> allJobs = jobSearchMapper.selectJoinList(SearchAllJob.class,
//                new MPJLambdaWrapper<JobPost>()
//                        .select
//                        .selectAs(JobPost::getId,)
//
//        );
    }
}
