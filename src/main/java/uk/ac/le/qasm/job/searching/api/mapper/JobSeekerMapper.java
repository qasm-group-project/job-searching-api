package uk.ac.le.qasm.job.searching.api.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.le.qasm.job.searching.api.entity.JobSeeker;
@Mapper
public interface JobSeekerMapper extends BaseMapper<JobSeeker>{
}
