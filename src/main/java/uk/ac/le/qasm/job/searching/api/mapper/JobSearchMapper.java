package uk.ac.le.qasm.job.searching.api.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
@Mapper
public interface JobSearchMapper extends MPJBaseMapper<JobPost> {
}
