package uk.ac.le.qasm.job.searching.api.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;
@Mapper
public interface JobSeekerMapper extends BaseMapper<JobSeekerAccount>{
}
